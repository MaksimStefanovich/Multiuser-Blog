package com.stefanovich.controllers;

import com.stefanovich.dto.ListTagDto;
import com.stefanovich.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tag")
@RequiredArgsConstructor
public class ApiTagController {
    private final TagService tagService;

    @GetMapping
    public ListTagDto getTags(@RequestParam(defaultValue = "", required = false) String query) {

        return ListTagDto.builder()
                .tags(tagService.getTagsDtoByQuery(query))
                .build();
    }
}
