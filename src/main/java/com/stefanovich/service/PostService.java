package com.stefanovich.service;

import com.stefanovich.dto.*;
import com.stefanovich.dto.mapper.PostDtoMapper;
import com.stefanovich.dto.mapper.PostIdDtoMapper;
import com.stefanovich.exception.BadRequestException;
import com.stefanovich.model.*;
import com.stefanovich.repository.PostCommentsRepository;
import com.stefanovich.repository.PostVotesRepository;
import com.stefanovich.repository.PostsRepository;
import com.stefanovich.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostsRepository postsRepository;
    private final PostDtoMapper postDtoMapper;
    private final PostIdDtoMapper postIdDtoMapper;
    private final TagsRepository tagsRepository;
    private final PostVotesRepository postVotesRepository;
    private final AuthService authService;
    private final PostCommentsRepository postCommentsRepository;

    @Value("${upload.file.folder}")
    private String uploadFileFolder;

    @Value("${upload.file.extension}")
    private List<String> extension;

    public long getCount() {
        return postsRepository.count();
    }

    public ListPostDto getAllPostsDto(Integer offset, Integer limit, String mode) {
        Page<Posts> posts = postsRepository.findAllByIsActiveTrueAndModerationStatusAndTimeIsBefore(
                ModerationStatus.ACCEPTED, LocalDateTime.now(), PageRequest.of(offset / limit, limit));

        long totalElements = posts.getTotalElements();

        List<PostDto> listPostDto = getListPostDto(posts.toList());

        Comparator<PostDto> comparator;
        switch (mode) {
            case "recent":
                comparator = (p1, p2) -> Long.compare(p2.getTimestamp(), p1.getTimestamp());
                break;
            case "popular":
                comparator = (p1, p2) -> Long.compare(p2.getCommentCount(), p1.getCommentCount());
                break;
            case "best":
                comparator = (p1, p2) -> Long.compare(p2.getLikeCount(), p1.getLikeCount());
                break;
            case "early":
                comparator = Comparator.comparing(PostDto::getTimestamp);
                break;
            default:
                throw new IllegalArgumentException("unknown mode");
        }

        listPostDto = listPostDto.stream().sorted(comparator)
                .collect(Collectors.toList());

        return ListPostDto.builder()
                .count(totalElements)
                .posts(listPostDto)
                .build();
    }

    public List<PostDto> getListPostDto(List<Posts> posts) {
        List<PostDto> postDtoList = new ArrayList<>();

        posts.forEach(p -> {
            List<PostVotes> postVotes = postVotesRepository.findByAllPost(p.getId());
            AtomicInteger likeCount = new AtomicInteger();
            AtomicInteger disLikeCount = new AtomicInteger();

            AtomicInteger commentCount = new AtomicInteger();
            postVotes.forEach(v -> {
                if (v.getValue() == 1) likeCount.getAndIncrement();
                if (v.getValue() == -1) disLikeCount.getAndIncrement();
            });

            List<PostComments> postComments = postCommentsRepository.findByAllPost(p.getId());
            commentCount.set(postComments.size());

            PostDto postDto = postDtoMapper.convertToPostsDTO(p);
            String text = Jsoup.parse(p.getText().substring(0, 4)).text();
            postDto.setAnnounce(text);
            postDto.setLikeCount(likeCount.get());
            postDto.setDislikeCount(disLikeCount.get());
            postDto.setCommentCount(commentCount.get());
            postDtoList.add(postDto);

        });

        return postDtoList;
    }

    public ListPostDto getPostsDtoSearch(Integer offset, Integer limit, String query) {
        Page<Posts> posts = postsRepository.findAllByQuery(query,
                PageRequest.of(offset / limit, limit));

        long totalElements = posts.getTotalElements();
        List<PostDto> listPostDto = getListPostDto(posts.toList());

        return ListPostDto.builder()
                .count(totalElements)
                .posts(listPostDto)
                .build();
    }

    public ListPostDto getPostsDtoByDate(Integer offset, Integer limit, String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        LocalDateTime localDateTime1 = localDateTime.plusDays(1);

        Page<Posts> posts = postsRepository.findAllByDate(PageRequest.of(offset / limit, limit), localDateTime, localDateTime1);

        long totalElements = posts.getTotalElements();
        List<PostDto> listPostDto = getListPostDto(posts.toList());

        return ListPostDto.builder()
                .count(totalElements)
                .posts(listPostDto)
                .build();
    }

    public ListPostDto getByTagsDto(Integer offset, Integer limit, String name) {
        Page<Posts> posts = postsRepository.findByTag(name, PageRequest.of(offset / limit, limit));

        long totalElements = posts.getTotalElements();
        List<PostDto> listPostDto = getListPostDto(posts.toList());

        return ListPostDto.builder()
                .count(totalElements)
                .posts(listPostDto)
                .build();
    }

    public ListPostDto getByModeration(Integer offset, Integer limit, String status) {
        Page<Posts> posts;
        Users users = authService.getCurrentUser();
        ModerationStatus moderationStatus;

        switch (status) {
            case "new":
                moderationStatus = ModerationStatus.NEW;
                break;
            case "declined":
                moderationStatus = ModerationStatus.DECLINED;
                break;
            case "accepted":
                moderationStatus = ModerationStatus.ACCEPTED;
                break;
            default:
                throw new IllegalArgumentException("unknown status");
        }
        posts = postsRepository.findMyModeration(PageRequest.of(offset / limit, limit), moderationStatus, users);
        return getListPostDto(posts);
    }

    private ListPostDto getListPostDto(Page<Posts> posts) {
        long totalElements = 0;

        List<PostDto> listPostDto = new ArrayList<>();
        if (posts != null) {
            totalElements = posts.getTotalElements();
            listPostDto = getListPostDto(posts.toList());
        }
        return ListPostDto.builder()
                .count(totalElements)
                .posts(listPostDto)
                .build();
    }

    public ListPostDto getByMyPosts(Integer offset, Integer limit, String status) {
        Users currentUser = authService.getCurrentUser();
        ModerationStatus moderationStatus = null;
        Page<Posts> posts;

        switch (status) {
            case "inactive":
                break;
            case "pending":
                moderationStatus = ModerationStatus.NEW;
                break;
            case "declined":
                moderationStatus = ModerationStatus.DECLINED;
                break;
            case "published":
                moderationStatus = ModerationStatus.ACCEPTED;
                break;
            default:
                throw new IllegalArgumentException("unknown status");
        }

        posts = postsRepository
                .findByAllMyPosts(PageRequest.of(offset / limit, limit), currentUser, moderationStatus);
        return getListPostDto(posts);
    }

    public PostIdDto getByPostId(Integer id) {
        Posts post = postsRepository.findById(id).orElseThrow(() -> new com.stefanovich.exception.EntityNotFoundException("документ с id = " + id + " не найден"));
        Users currentUser = authService.getCurrentUser();


        if (currentUser != null) {

            if (!post.getUser().getId().equals(currentUser.getId()) && !currentUser.isModerator()) {
                post.setViewCount(post.getViewCount() + 1);
                postsRepository.save(post);
            }
        } else {
            post.setViewCount(post.getViewCount() + 1);
            postsRepository.save(post);
        }

        AtomicInteger likeCount = new AtomicInteger();
        AtomicInteger disLikeCount = new AtomicInteger();

        List<PostVotes> postVotes = postVotesRepository.findByAllPost(id);

        postVotes.forEach(p -> {
            if (p.getValue() == 1) likeCount.getAndIncrement();
            if (p.getValue() == -1) disLikeCount.getAndIncrement();
        });

        List<String> tagName = new ArrayList<>();
        for (Tags tag : post.getTags()) tagName.add(tag.getName());

        PostIdDto postIdDto = postIdDtoMapper.convertToPostDtoId(post);
        postIdDto.setTags(tagName);
        postIdDto.setLikeCount(likeCount.get());
        postIdDto.setDislikeCount(disLikeCount.get());

        return postIdDto;
    }

    public void savePost(Posts post, PostCreateDto postCreateDto) {
        post.setIsActive(postCreateDto.getActive());
        post.setTitle(postCreateDto.getTitle());
        post.setText(postCreateDto.getText());

        if (postCreateDto.getTimestamp() != null) {
            Timestamp timestamp = new Timestamp(postCreateDto.getTimestamp());
            LocalDateTime time = timestamp.toLocalDateTime();
            if (time.isBefore(LocalDateTime.now())) {
                time = LocalDateTime.now();
            }
            post.setTime(time);
        }

        List<String> postTags = postCreateDto.getTags();
        List<Tags> tags = tagsRepository.findAll();

        post.getTags().clear();

        for (String postTag : postTags) {
            Optional<Tags> tag = tags.stream().filter(t -> t.getName().equals(postTag)).findFirst();
            if (tag.isPresent()) {
                post.getTags().add(tag.get());
            } else {
                Tags tagNew = new Tags();
                tagNew.setName(postTag);
                tagsRepository.save(tagNew);
                post.getTags().add(tagNew);
            }
        }
        post.setUser(authService.getCurrentUser());
        if (post.getViewCount() == null) {
            post.setViewCount(1);
        } else post.setViewCount(post.getViewCount() + 1);
        postsRepository.save(post);
    }

    public void savePostDto(PostCreateDto postCreateDto) {
        Posts post = new Posts();
        savePost(post, postCreateDto);
    }

    public String savePicture(MultipartFile image) throws IOException {
        if (!extension.contains(FilenameUtils.getExtension(image.getOriginalFilename()))) {
            throw new BadRequestException("Неверное расширение файла. Допустимы: jpg, png");
        }

        File file = createFile();
        image.transferTo(file);
        return file.getAbsolutePath();
    }

    public File createFile() throws IOException {
        UUID uuid = UUID.randomUUID();
        String[] s = uuid.toString().split("-");

        File file = new File(uploadFileFolder + "/" + s[1] + "/" + s[2] + "/" + s[3] + "/" + s[0]);
        Files.createDirectories(Paths.get(file.getParent()));
        file.createNewFile();
        return file;
    }

    public void update(Integer id, PostCreateDto postCreateDto) {
        Posts post = postsRepository.findById(id).orElseThrow(() -> new com.stefanovich.exception.EntityNotFoundException("документ с id = " + id + " не найден"));
        savePost(post, postCreateDto);
    }

    public Boolean moderationP(Integer id, ModerationDto.Decision decision) {
        Posts post = postsRepository.findById(id).orElseThrow(() -> new com.stefanovich.exception.EntityNotFoundException("документ с id = " + id + " не найден"));
        ModerationStatus moderationStatus1 = post.getModerationStatus();

        ModerationStatus moderationStatus = decision.equals(ModerationDto.Decision.ACCEPTED) ? ModerationStatus.ACCEPTED : ModerationStatus.DECLINED;

        if (moderationStatus1 == moderationStatus) {
            return true;
        } else {
            post.setModerationStatus(moderationStatus);
            ModerationStatus moderationStatus2 = post.getModerationStatus();
            postsRepository.save(post);
            return moderationStatus1 != moderationStatus2;
        }
    }

    public CalendarDto getYears(String yearSt) {
        Integer year = yearSt == null ? LocalDate.now().getYear() : Integer.parseInt(yearSt);
        List<LocalDateTime> times = postsRepository.getYear(year);
        Map<LocalDate, Integer> posts = new HashMap<>();
        times.stream()
                .map(LocalDateTime::toLocalDate)
                .forEach(t -> posts.merge(t, 1, Integer::sum));

        List<Integer> date = postsRepository.getYear();
        return CalendarDto.builder()
                .posts(posts)
                .years(date)
                .build();
    }

    public Boolean saveLike(LikeDto likeDto) {
        return saveLikeOrDislike(1, likeDto.getPostId());
    }

    public Boolean saveDisLike(LikeDto likeDto) {
        return saveLikeOrDislike(-1, likeDto.getPostId());
    }

    public Boolean saveLikeOrDislike(int i, Integer postId) {
        Users currentUser = authService.getCurrentUser();
        Posts post = postsRepository.findById(postId).orElseThrow(() ->
                new EntityNotFoundException("post with id = " + postId + " not found"));

        Optional<PostVotes> postVotes = postVotesRepository.findByPostIdAndUserId(currentUser, post);
        if (postVotes.isPresent()) {
            if (postVotes.get().getValue() == i) return false;
            else postVotes.get().setValue(i);
            postVotesRepository.save(postVotes.get());
        } else {

            PostVotes postVotesNew = new PostVotes();
            postVotesNew.setUser(currentUser);
            postVotesNew.setTime(LocalDateTime.now());
            postVotesNew.setMessages(post);
            postVotesNew.setValue(i);
            postVotesRepository.save(postVotesNew);
        }
        return true;
    }
}
