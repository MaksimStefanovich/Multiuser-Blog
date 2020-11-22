package com.stefanovich.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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




    @Data
    @NoArgsConstructor
    //TODO какой тут лучше модификатор доступа, использую еще в PostIdDto
    protected static class UserDto {
        Integer id;
        String name;

    }
}
