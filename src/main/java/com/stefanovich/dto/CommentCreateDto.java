package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CommentCreateDto {

    @JsonProperty("parent_id")
    private Integer parentId;

    @NotNull(message = "поста нет")
    @JsonProperty("post_id")
    private Integer postId;

    @NotNull(message = "Текст комментария не задан или слишком короткий")
    @Size(min = 5)
    private String text;

}
