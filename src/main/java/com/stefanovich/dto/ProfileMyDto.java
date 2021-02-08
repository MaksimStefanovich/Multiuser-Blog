package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "ProfileMyDto", description = "Dto for my profile")
public class ProfileMyDto {
    @Schema(description = "name")
    private String name;

    @Schema(description = "email")
    private String email;

    @Schema(description = "password")
    private String password;

    @Schema(description = "removePhoto")
    private Integer removePhoto = 0;
}
