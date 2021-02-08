package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(name = "ListTagDto", description = "list Tag Dto")
public class ListTagDto {
    @Schema(name = "tags")
    List<TagDto> tags;
}
