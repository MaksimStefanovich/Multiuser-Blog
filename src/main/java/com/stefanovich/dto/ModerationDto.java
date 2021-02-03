package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModerationDto {
    @JsonProperty("post_id")
    private Integer postId;

    private Decision decision;

    public enum Decision {
        @JsonProperty("accept")
        ACCEPTED,
        @JsonProperty("decline")
        DECLINED
    }
}
