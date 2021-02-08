package com.stefanovich.controllers;

import com.stefanovich.dto.CommentCreateDto;
import com.stefanovich.dto.ListPostDto;
import com.stefanovich.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Метод добавляет комментарий к посту",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @PostMapping()
    public Map<String, Integer> saveComment(@RequestBody @Valid CommentCreateDto commentCreateDto) {
        return commentService.saveCommentDto(commentCreateDto);
    }
}
