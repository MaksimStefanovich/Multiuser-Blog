package com.stefanovich.controllers;

import com.stefanovich.dto.ModerationDto;
import com.stefanovich.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/moderation")
@RequiredArgsConstructor
public class ApiModerationController {
    private final PostService postService;

    @Tag(name = "moderation API", description = "Метод фиксирует действие модератора по посту: его утверждение или отклонение.")
    @PostMapping
    public Map<String, Boolean> moderationPost(@RequestBody ModerationDto moderationDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", postService.moderationP(moderationDto.getPostId(), moderationDto.getDecision()));
        return map;
    }
}
