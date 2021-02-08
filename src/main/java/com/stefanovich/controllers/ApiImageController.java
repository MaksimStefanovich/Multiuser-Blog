package com.stefanovich.controllers;

import com.stefanovich.dto.PostIdDto;
import com.stefanovich.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
public class ApiImageController {
    private final PostService postService;

    @Operation(summary = "get image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Метод загружает на сервер изображение в папку upload и три случайные подпапки. " +
                                    "Имена подпапок и изображения можно формировать из случайного хэша, разделяя его на части.",
                            content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Bad Request"

                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveImage(@RequestPart MultipartFile image) throws IOException {
        return postService.savePicture(image);
    }
}
