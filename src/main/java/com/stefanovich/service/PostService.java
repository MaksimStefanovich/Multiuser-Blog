package com.stefanovich.service;

import com.stefanovich.dto.*;
import com.stefanovich.dto.mapper.PostDtoMapper;
import com.stefanovich.dto.mapper.PostIdDtoMapper;
import com.stefanovich.model.ModerationStatus;
import com.stefanovich.model.Posts;
import com.stefanovich.model.Tags;
import com.stefanovich.repository.PostsRepository;
import com.stefanovich.repository.TagsRepository;
import com.stefanovich.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostsRepository postsRepository;
    private final PostDtoMapper postDtoMapper;
    private final PostIdDtoMapper postIdDtoMapper;
    private final TagsRepository tagsRepository;
    private final UsersRepository usersRepository;

    @Value("${upload.file.folder}")
    private String uploadFileFolder;

    @Value("${upload.file.extension}")
    private List<String> extension;


    public long getCount() {
        return postsRepository.count();
    }


    public List<PostDto> getAllPostsDto(Integer offset, Integer limit) {
        return postDtoMapper.convertToDtoList(postsRepository.findAllByIsActiveTrueAndAndModerationStatusAndTimeIsBefore(
                ModerationStatus.ACCEPTED, LocalDateTime.now(), PageRequest.of(offset, limit)));
    }


    public List<PostDto> getPostsDtoSearch(Integer offset, Integer limit, String query) {

        return postDtoMapper.convertToDtoList(postsRepository.findAllByQuery(query,
                PageRequest.of(offset, limit)));
    }


    public List<PostDto> getPostsDtoByDate(Integer offset, Integer limit, String date) {

        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        LocalDateTime localDateTime1 = localDateTime.plusDays(1);

        return postDtoMapper.convertToDtoList(postsRepository.findAllByDate(PageRequest.of(offset, limit), localDateTime, localDateTime1));


    }

    public List<PostDto> getByTagsDto(Integer offset, Integer limit, String name) {
        return postDtoMapper.convertToDtoList(postsRepository.findByTag(name, PageRequest.of(offset, limit)));
    }

    public List<PostDto> getByModeration(Integer offset, Integer limit, ModerationStatus status) {
        return postDtoMapper.convertToDtoList(postsRepository.findAllModeration(PageRequest.of(offset, limit), status));

    }

    public PostIdDto getByPostId(Integer id) {

        Posts post = postsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        post.setViewCount(post.getViewCount() + 1);
        //TODO вместе с параметром viewCount меняются лайки и дизлайки
        postsRepository.save(post);

        List<String> tagName = new ArrayList<>();
        for (Tags tag : post.getTags()) tagName.add(tag.getName());

        PostIdDto postIdDto = postIdDtoMapper.convertToPostDtoId(post);
        postIdDto.setTags(tagName);
        return postIdDto;

    }

    public void savePost(Posts post, PostCreateDto postCreateDto) {
        post.setIsActive(postCreateDto.getActive());
        post.setTitle(postCreateDto.getTitle());
        post.setText(postCreateDto.getText());

        Timestamp timestamp = new Timestamp(postCreateDto.getTimestamp());
        LocalDateTime time = timestamp.toLocalDateTime();
        if (time.isBefore(LocalDateTime.now())) {
            time = LocalDateTime.now();
        }
        post.setTime(time);

        List<String> postTags = postCreateDto.getTags();
        List<Tags> tags = tagsRepository.findAll();

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
        post.setUser(usersRepository.findById(1).get());
        postsRepository.save(post);

    }


    public void savePostDto(PostCreateDto postCreateDto) {
        Posts post = new Posts();
        savePost(post, postCreateDto);

    }

    public String savePicture(MultipartFile image) throws IOException {

        if (!extension.contains(FilenameUtils.getExtension(image.getOriginalFilename()))) {
            throw new RuntimeException();
        }

        UUID uuid = UUID.randomUUID();
        String[] s = uuid.toString().split("-");

        File file = new File(uploadFileFolder + "/" + s[1] + "/" + s[2] + "/" + s[3] + "/" + s[0]);
        Files.createDirectories(Paths.get(file.getParent()));
        file.createNewFile();

        image.transferTo(file);

        return file.getAbsolutePath();

    }


    public void update(Integer id, PostCreateDto postCreateDto) {
        Posts post = postsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        savePost(post, postCreateDto);
    }

//TODO проверить, получилось длинно и сложно, по-моему

    public Boolean moderationP(Integer id, ModerationDto.Decision decision) {

        Posts post = postsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        ModerationStatus moderationStatus1 = post.getModerationStatus();

        ModerationStatus moderationStatus = decision.equals(ModerationDto.Decision.ACCEPTED) ? ModerationStatus.ACCEPTED : ModerationStatus.DECLINED;


        if (moderationStatus1 == moderationStatus) {
            return true;
        } else {
            post.setModerationStatus(moderationStatus);
            ModerationStatus moderationStatus2 = post.getModerationStatus();
            postsRepository.save(post);

            if (moderationStatus1 == moderationStatus2) {
                return false;
            } else return true;
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
}
