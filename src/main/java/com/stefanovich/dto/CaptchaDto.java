package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "CaptchaDto", description = "Dto for Captcha")
public class CaptchaDto {
    @Schema(name = "secret")
    String secret;
    @Schema(name = "image")
    String image;
}
