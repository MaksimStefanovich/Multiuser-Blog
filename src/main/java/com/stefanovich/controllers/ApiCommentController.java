package com.stefanovich.controllers;

import com.stefanovich.dto.CommentCreateDto;
import com.stefanovich.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class ApiCommentController {
    private final CommentService commentService;

    @PostMapping()
    public Map<String, Integer> saveComment(@RequestBody @Valid CommentCreateDto commentCreateDto) {

        return commentService.saveCommentDto(commentCreateDto);
    }
}
