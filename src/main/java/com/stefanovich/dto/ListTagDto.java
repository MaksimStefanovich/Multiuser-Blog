package com.stefanovich.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ListTagDto {
    List<TagDto> tags;
}
