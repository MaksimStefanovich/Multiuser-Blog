package com.stefanovich.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CalendarDto {

    private List<Integer> years;
    private Map<LocalDate, Integer> posts;
}
