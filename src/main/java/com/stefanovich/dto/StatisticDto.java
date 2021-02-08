package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "StatisticDto", description = "Dto for Statistic")
public class StatisticDto {
    @Schema(description = "postsCount")
    private String postsCount;
    @Schema(description = "likesCount")
    private String likesCount;
    @Schema(description = "dislikesCount")
    private String dislikesCount;
    @Schema(description = "viewsCount")
    private String viewsCount;
    @Schema(description = "firstPublication")
    private Long firstPublication;
}
