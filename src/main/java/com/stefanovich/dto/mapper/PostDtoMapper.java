package com.stefanovich.dto.mapper;

import com.stefanovich.dto.PostDto;
import com.stefanovich.model.Posts;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class PostDtoMapper {
    private final ModelMapper modelMapper;

    public PostDto convertToPostsDTO(Posts posts) {
        PostDto postDto = modelMapper
                .map(posts, PostDto.class);
        Instant instant = posts.getTime().atZone(ZoneId.of("Europe/Paris")).toInstant();
        postDto.setTimestamp(instant.getEpochSecond());
        return postDto;
    }
}
