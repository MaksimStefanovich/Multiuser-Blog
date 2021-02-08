package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Schema(name = "AddLoginDto", description = "Dto for Auth")
public class AuthLoginDto {
    @Schema(description = "result")
    Boolean result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "user")
    AuthUserDto user;

    @Data
    @NoArgsConstructor
    @Schema(name = "AuthUserDto", description = "Dto for AuthUser")
    public static class AuthUserDto {
        @Schema(name = "id")
        Integer id;
        @Schema(name = "name")
        String name;
        @Schema(name = "photo")
        String photo;
        @Schema(name = "email")
        String email;
        @Schema(name = "moderation")
        Boolean moderation;
        @Schema(name = "moderation Count")
        Integer moderationCount;
        @Schema(name = "settings")
        Boolean settings;
    }
}
