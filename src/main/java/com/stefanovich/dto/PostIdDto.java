package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class PostIdDto {
    private Integer id;

    private Long timestamp;

    @NotNull
    private Boolean isActive;

    @NotNull
    private PostDto.UserDto user;

    @NotNull
    private String title;

    @NotNull
    private String text;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer viewCount;

    private List<PostCommentsDto> comments;

    private List<String> tags;

}
