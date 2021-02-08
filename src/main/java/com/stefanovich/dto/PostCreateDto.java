package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "PostCreateDto", description = "Dto for create post")
public class PostCreateDto {
    @Schema(name = "timestamp")
    private Long timestamp;

    @Schema(name = "active")
    private Boolean active;

    @NotBlank(message = "Заголовок не установлен")
    @Size(min = 3, message = "Загаловок слишком короткий")
    @Schema(name = "title")
    private String title;

    @Schema(name = "tags")
    private List<String> tags;

    @NotBlank(message = "Текст публикции не установлен")
    @Size(min = 50, message = "Текст публикции слишком короткий")
    @Schema(name = "text")
    private String text;
}
