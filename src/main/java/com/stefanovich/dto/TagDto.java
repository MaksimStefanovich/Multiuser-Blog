package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "TagDto", description = "Dto for Tag")
public class TagDto {
    @Schema(description = "name")
    private String name;
    @Schema(description = "weight")
    private Double weight;
}
