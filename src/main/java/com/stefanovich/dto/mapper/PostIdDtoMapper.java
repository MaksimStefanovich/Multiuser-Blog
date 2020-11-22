package com.stefanovich.dto.mapper;

import com.stefanovich.dto.PostIdDto;
import com.stefanovich.model.Posts;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class PostIdDtoMapper {
    private final ModelMapper modelMapper;
    private final PostsCommentsDtoMapper p;


    public PostIdDto convertToPostDtoId(Posts posts){

        PostIdDto postIdDto = modelMapper
                .map(posts, PostIdDto.class);
        postIdDto.setTimestamp(Timestamp.valueOf(posts.getTime()).getTime());
        postIdDto.setComments(p.convertToDtoListPostComments(posts.getPostComments()));
        return postIdDto;
    }

}
