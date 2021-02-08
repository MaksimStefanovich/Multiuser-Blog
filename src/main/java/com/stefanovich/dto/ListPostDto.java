package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(name = "ListPostDto", description = "list Post Dto")
public class ListPostDto {
    @Schema(name = "count")
    Long count;
    @Schema(name = "posts")
    List<PostDto> posts;
}
