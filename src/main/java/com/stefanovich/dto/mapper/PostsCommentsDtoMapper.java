package com.stefanovich.dto.mapper;

import com.stefanovich.dto.PostCommentsDto;
import com.stefanovich.model.PostComments;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostsCommentsDtoMapper {
    private final ModelMapper modelMapper;

    public PostCommentsDto convertToPostCommentsDto(PostComments comments){

        PostCommentsDto postCommentsDto = modelMapper
                .map(comments, PostCommentsDto.class);
        postCommentsDto.setTimestamp(Timestamp.valueOf(comments.getTime()).getTime());
        return postCommentsDto;

    }

    public List<PostCommentsDto> convertToDtoListPostComments(List<PostComments> postComments) {
        return postComments.stream().map(this::convertToPostCommentsDto).collect(Collectors.toList());

    }

}
