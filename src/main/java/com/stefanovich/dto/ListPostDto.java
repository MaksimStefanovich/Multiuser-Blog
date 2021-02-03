package com.stefanovich.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ListPostDto {
    Long count;
    List<PostDto> posts;
}
