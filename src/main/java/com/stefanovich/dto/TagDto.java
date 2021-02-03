package com.stefanovich.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDto {
    private String name;
    private Double weight;
}
