package com.stefanovich.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class PostDto {

    private Integer id;

    private Long timestamp;

    @NotNull
    private UserDto user;

    @NotNull
    private String title;

    private String announce;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer commentCount;

    private Integer viewCount;




//    private List<PostCommentsDto> comments;




    @Data
    @NoArgsConstructor
    protected static class UserDto {
        Integer id;
        String name;

    }
}
