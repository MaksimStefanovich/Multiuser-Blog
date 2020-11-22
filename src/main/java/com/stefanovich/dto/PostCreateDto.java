package com.stefanovich.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class PostCreateDto {

    private Long timestamp;

    private Boolean active;

    @NotBlank(message = "Заголовок не установлен")
    @Size(min = 3, message = "Загаловок слишком короткий")
    private String title;

    private List<String> tags;

    @NotBlank(message = "Текст публикции не установлен")
    @Size(min = 50, message = "Текст публикции слишком короткий")
    private String text;


}
