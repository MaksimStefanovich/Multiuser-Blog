package com.stefanovich.controllers;

import com.stefanovich.dto.StatisticDto;
import com.stefanovich.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/statistics")
@RequiredArgsConstructor
public class ApiStatisticsController {

    private final StatisticService statisticService;

    @GetMapping("/my")
    public StatisticDto getMyStatistic() {
        return statisticService.getMyStat();
    }

    @GetMapping("/all")
    public StatisticDto getAllStatistic() {
        return statisticService.getAllStat();
    }



}
