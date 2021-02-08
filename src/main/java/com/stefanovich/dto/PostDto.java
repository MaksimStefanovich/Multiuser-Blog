package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Schema(name = "PostDto", description = "Dto for Entity Post")
public class PostDto {
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "post time")
    private Long timestamp;

    @NotNull
    @Schema(description = "post user")
    private UserDto user;

    @NotNull
    @Schema(description = "post title")
    private String title;

    @Schema(description = "post announce")
    private String announce;

    @Schema(description = "post like")
    private Integer likeCount;

    @Schema(description = "post disLike")
    private Integer dislikeCount;

    @Schema(description = "post comment count")
    private Integer commentCount;

    @Schema(description = "post view count")
    private Integer viewCount;

    @Data
    @NoArgsConstructor
    @Schema(name = "UserDto", description = "UserDto for Post")
    protected static class UserDto {
        @Schema(description = "id")
        Integer id;
        @Schema(description = "name")
        String name;
    }
}
