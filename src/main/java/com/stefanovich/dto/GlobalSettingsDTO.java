package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Schema(name = "GlobalSettingsDTO", description = "Dto for Settings")
public class GlobalSettingsDTO {
    @JsonProperty("MULTIUSER_MODE")
    @Schema(name = "multiuserMode")
    private Boolean multiuserMode;

    @JsonProperty("POST_PREMODERATION")
    @Schema(name = "postPremoderation")
    private Boolean postPremoderation;

    @JsonProperty("STATISTICS_IS_PUBLIC")
    @Schema(name = "statisticsIsPublic")
    private Boolean statisticsIsPublic;
}

