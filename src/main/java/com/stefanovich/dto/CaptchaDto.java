package com.stefanovich.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CaptchaDto {
    String secret;
    String image;
}
