package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "ModerationDto", description = "Dto for Moderation")
public class ModerationDto {
    @JsonProperty("post_id")
    @Schema(name = "post Id")
    private Integer postId;

    @Schema(name = "decision")
    private Decision decision;

    @Schema(name = "Decision")
    public enum Decision {
        @JsonProperty("accept")
        ACCEPTED,
        @JsonProperty("decline")
        DECLINED
    }
}
