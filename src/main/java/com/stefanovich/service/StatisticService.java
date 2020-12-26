package com.stefanovich.service;

import com.stefanovich.dto.StatisticDto;
import com.stefanovich.dto.PostIdDto;
import com.stefanovich.dto.mapper.PostIdDtoMapper;
import com.stefanovich.model.Posts;
import com.stefanovich.model.Users;
import com.stefanovich.repository.PostsRepository;
import com.stefanovich.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final PostsRepository postsRepository;
    private final PostIdDtoMapper postIdDtoMapper;
    private final UsersRepository usersRepository;
    private final AuthService authService;


    public StatisticDto getMyStat() {


        Users user = authService.getCurrentUser();
        List<Posts> post = postsRepository.findAllByUserId(user.getId());

        return getStatisticDto(post);
    }

    public StatisticDto getAllStat() {
        List<Posts> post = postsRepository.findAll();
        return getStatisticDto(post);
    }

    public StatisticDto getStatisticDto(List<Posts> post) {
        StatisticDto statisticDto = new StatisticDto();

        List<PostIdDto> postIdDto = post.stream().map(postIdDtoMapper::convertToPostDtoId)
                .collect(Collectors.toList());

        Long likeCount = postIdDto.stream().map(PostIdDto::getLikeCount).count();
        Long disLikeCount = postIdDto.stream().map(PostIdDto::getDislikeCount).count();
        Long viewCount = postIdDto.stream().map(PostIdDto::getViewCount).count();
        OptionalLong firstPublication = postIdDto.stream().map(PostIdDto::getTimestamp)
                .mapToLong(Long::longValue).min();


        statisticDto.setPostsCount(String.valueOf(post.size()));
        statisticDto.setLikesCount(String.valueOf(likeCount));
        statisticDto.setDislikesCount(String.valueOf(disLikeCount));
        statisticDto.setViewsCount(String.valueOf(viewCount));
        statisticDto.setFirstPublication(String.valueOf(firstPublication));

        return statisticDto;
    }
}
