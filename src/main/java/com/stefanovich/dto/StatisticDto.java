package com.stefanovich.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class StatisticDto {
    private String postsCount;
    private String likesCount;
    private String dislikesCount;
    private String viewsCount;


    private Long firstPublication;

}
