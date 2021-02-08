package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(name = "InitDto", description = "Dto for Init")
public class InitDto {
    @Schema(name = "title")
    String title;
    @Schema(name = "subtitle")
    String subtitle;
    @Schema(name = "phone")
    String phone;
    @Schema(name = "email")
    String email;
    @Schema(name = "copyright")
    String copyright;
    @Schema(name = "copyrightFrom")
    String copyrightFrom;
}
