package com.stefanovich.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PostCommentsDto {
    private Integer id;

    @NotNull
    private Long timestamp;

    @NotNull
    @Lob
    private String text;

    @NotNull
    private UserDtoComment user;

    @Data
    @NoArgsConstructor
    protected static class UserDtoComment {
        Integer id;
        String name;
        String photo;
    }
}
