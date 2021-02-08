package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "LikeDto", description = "Dto for Like")
public class LikeDto {
    @JsonProperty("post_id")
    @Schema(name = "postId")
    Integer postId;
}
