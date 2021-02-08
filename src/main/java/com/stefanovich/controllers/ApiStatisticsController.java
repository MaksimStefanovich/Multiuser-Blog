package com.stefanovich.controllers;

import com.stefanovich.dto.ListPostDto;
import com.stefanovich.dto.StatisticDto;
import com.stefanovich.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/statistics")
@RequiredArgsConstructor
public class ApiStatisticsController {
    private final StatisticService statisticService;

    @Tag(name = "statistic API", description = "Метод возвращает статистику постов текущего авторизованного пользователя: " +
            "общие количества параметров для всех публикаций, у который он является автором и доступные для чтения.")
    @GetMapping("/my")
    public StatisticDto getMyStatistic() {
        return statisticService.getMyStat();
    }

    @Operation(summary = "Метод выдаёт статистику по всем постам блога. \" +\n" +
            "            \"В случае, если публичный показ статистики блога запрещён (см. соответствующий параметр в global_settings) \" +\n" +
            "            \"и текущий пользователь не модератор, должна выдаваться ошибка 401 (см. ниже Обработка ошибок).",
            responses = {
                    @ApiResponse(
                            content = @Content(schema = @Schema(implementation = ListPostDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )

            }
    ) @GetMapping("/all")
    public StatisticDto getAllStatistic() {
        return statisticService.getAllStat();
    }
}
