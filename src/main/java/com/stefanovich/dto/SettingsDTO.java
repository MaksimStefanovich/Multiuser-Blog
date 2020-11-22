package com.stefanovich.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SettingsDTO {
    Boolean multiuserMode;
    Boolean postPremoderation;
    Boolean statisticsIsPublic;
}

