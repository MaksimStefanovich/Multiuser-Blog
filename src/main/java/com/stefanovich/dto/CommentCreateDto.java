package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Schema(name = "ChangePasswordDto", description = "Dto for Change password")
public class CommentCreateDto {
    @JsonProperty("parent_id")
    @Schema(name = "parent Id")
    private Integer parentId;

    @NotNull(message = "поста нет")
    @JsonProperty("post_id")
    @Schema(name = "post Id")
    private Integer postId;

    @NotBlank()
    @Size(min = 6, message = "Текст комментария не задан или слишком короткий")
    @Schema(name = "text")
    private String text;
}
