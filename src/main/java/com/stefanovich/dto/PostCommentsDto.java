package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Schema(name = "PostCommentsDto", description = "Dto for comment")
public class PostCommentsDto {
    @Schema(name = "id")
    private Integer id;

    @NotNull
    @Schema(name = "timestamp")
    private Long timestamp;

    @NotNull
    @Lob
    @Schema(name = "text")
    private String text;

    @NotNull
    @Schema(name = "user")
    private UserDtoComment user;

    @Data
    @NoArgsConstructor
    @Schema(name = "UserDtoComment",  description = "User Comment")
    protected static class UserDtoComment {
        @Schema(name = "id")
        Integer id;
        @Schema(name = "name")
        String name;
        @Schema(name = "photo")
        String photo;
    }
}
