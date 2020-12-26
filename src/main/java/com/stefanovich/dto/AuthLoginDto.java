package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
public class AuthLoginDto {

    Boolean result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    AuthUserDto user;

    @Data
    @NoArgsConstructor
    public static class AuthUserDto {
        Integer id;
        String name;
        String photo;
        String email;
        Boolean moderation;
        Integer moderationCount;
        Boolean settings;


    }


}
