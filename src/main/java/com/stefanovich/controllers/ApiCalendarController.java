package com.stefanovich.controllers;

import com.stefanovich.dto.CalendarDto;
import com.stefanovich.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/calendar")
@RequiredArgsConstructor
public class ApiCalendarController {
    private final PostService postService;

    @GetMapping
    public CalendarDto getCalendar(@RequestParam(required = false) String year) {
        return postService.getYears(year);
    }
}
