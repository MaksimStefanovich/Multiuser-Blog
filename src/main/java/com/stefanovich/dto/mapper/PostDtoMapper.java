package com.stefanovich.dto.mapper;

import com.stefanovich.dto.PostDto;
import com.stefanovich.model.Posts;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostDtoMapper {

    private final ModelMapper modelMapper;

    public PostDto convertToPostsDTO(Posts posts) {
        PostDto postDto = modelMapper
                .map(posts, PostDto.class);
        postDto.setTimestamp(Timestamp.valueOf(posts.getTime()).getTime());
        return postDto;
    }

    public List<PostDto> convertToDtoList(List<Posts> posts) {
        return posts.stream().map(this::convertToPostsDTO).collect(Collectors.toList());

    }






}
