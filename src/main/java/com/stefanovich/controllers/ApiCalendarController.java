package com.stefanovich.controllers;

import com.stefanovich.dto.CalendarDto;
import com.stefanovich.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "calendar API", description = "Метод выводит количества публикаций на каждую дату переданного" +
            " в параметре year года или текущего года, если параметр year не задан. " +
            "В параметре years всегда возвращается список всех годов, за которые была хотя бы одна публикация, " +
            "в порядке возврастания.")
    @GetMapping
    public CalendarDto getCalendar(@RequestParam(required = false) String year) {
        return postService.getYears(year);
    }
}
