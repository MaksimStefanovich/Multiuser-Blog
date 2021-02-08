package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@Schema(name = "CalendarDto", description = "Dto for Calendar")
public class CalendarDto {
    @Schema(name = "years")
    private final List<Integer> years;
    @Schema(name = "posts")
    private final Map<LocalDate, Integer> posts;
}
