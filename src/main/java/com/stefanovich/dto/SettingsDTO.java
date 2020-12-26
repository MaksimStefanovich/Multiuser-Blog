package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SettingsDTO {
    @JsonProperty("MULTIUSER_MODE")
    private Boolean multiuserMode;

    @JsonProperty("POST_PREMODERATION")
    private Boolean postPremoderation;

    @JsonProperty("STATISTICS_IS_PUBLIC")
    private Boolean statisticsIsPublic;
}

