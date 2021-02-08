package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "PostIdDto", description = "Dto for post Id")
public class PostIdDto {
    @Schema(description = "post title")
    private Integer id;

    @Schema(description = "timestamp")
    private Long timestamp;

    @NotNull
    @Schema(description = "isActive")
    private Boolean isActive;

    @NotNull
    @Schema(description = "user")
    private PostDto.UserDto user;

    @NotNull
    @Schema(description = "title")
    private String title;

    @NotNull
    @Schema(description = "text")
    private String text;

    @Schema(description = "likeCount")
    private Integer likeCount;

    @Schema(description = "dislikeCount")
    private Integer dislikeCount;

    @Schema(description = "viewCount")
    private Integer viewCount;

    @Schema(description = "comments")
    private List<PostCommentsDto> comments;

    @Schema(description = "tags")
    private List<String> tags;
}
