package com.stefanovich.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InitDto {
    String title;
    String subtitle;
    String phone;
    String email;
    String copyright;
    String copyrightFrom;
}
