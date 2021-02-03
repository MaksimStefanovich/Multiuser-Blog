package com.stefanovich.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CalendarDto {
    private final List<Integer> years;
    private final Map<LocalDate, Integer> posts;
}
