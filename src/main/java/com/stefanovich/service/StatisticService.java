package com.stefanovich.service;

import com.stefanovich.dto.StatisticDto;
import com.stefanovich.model.Posts;
import com.stefanovich.model.Users;
import com.stefanovich.repository.GlobalSettingsRepository;
import com.stefanovich.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final PostsRepository postsRepository;
    private final AuthService authService;
    private final GlobalSettingsRepository globalSettingsRepository;


    public StatisticDto getMyStat() {
        Users user = authService.getCurrentUser();
        List<Posts> post = postsRepository.findAllByUserId(user.getId());
        return getStatisticDto(post);
    }

    public StatisticDto getAllStat() {

        String byCode = globalSettingsRepository.findByCode();


        if (byCode.equals("NO")) new NullPointerException();
        else {
            List<Posts> post = postsRepository.findAll();
            return getStatisticDto(post);
        }
        return null;
    }

    public StatisticDto getStatisticDto(List<Posts> post) {

        List<LocalDateTime> collect = post.stream().map(Posts::getTime).collect(Collectors.toList());

        LocalDateTime time = Collections.min(collect);
        Instant instant = time.atZone(ZoneId.of("Europe/Paris")).toInstant();
//        Long timestamp = Timestamp.from(instant).getTime();
        Long timestampSeconds = instant.getEpochSecond();


                AtomicInteger likeCount = new AtomicInteger();
        AtomicInteger disLikeCount = new AtomicInteger();
        AtomicInteger viewCount = new AtomicInteger();

        post.forEach(posts -> {
            if (posts.getViewCount() != null) {
                viewCount.set(viewCount.get() + posts.getViewCount());
            }
            posts.getPostVotes().forEach(p -> {
                if (p.getValue() == 1) {
                    likeCount.getAndIncrement();
                }
                if (p.getValue() == -1) {
                    disLikeCount.getAndIncrement();
                }
            });
        });

        StatisticDto statisticDto = new StatisticDto();

        statisticDto.setPostsCount(String.valueOf(post.size()));
        statisticDto.setLikesCount(String.valueOf(likeCount.get()));
        statisticDto.setDislikesCount(String.valueOf(disLikeCount.get()));
        statisticDto.setViewsCount(String.valueOf(viewCount.get()));
        statisticDto.setFirstPublication(timestampSeconds);

        return statisticDto;
    }
}
