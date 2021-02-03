package com.stefanovich.controllers;

import com.stefanovich.dto.ModerationDto;
import com.stefanovich.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/moderation")
@RequiredArgsConstructor
public class ApiModerationController {
    private final PostService postService;

    @PostMapping
    public Map<String, Boolean> moderationPost(@RequestBody ModerationDto moderationDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", postService.moderationP(moderationDto.getPostId(), moderationDto.getDecision()));
        return map;
    }
}
