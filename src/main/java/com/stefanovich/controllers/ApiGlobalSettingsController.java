package com.stefanovich.controllers;

import com.stefanovich.dto.GlobalSettingsDTO;
import com.stefanovich.service.GlobalSettingsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/settings")
@RequiredArgsConstructor
public class ApiGlobalSettingsController {
    private final GlobalSettingsService globalSettingsService;

    @Tag(name = "settings API", description = "Метод возвращает глобальные настройки блога из таблицы global_settings.")
    @GetMapping()
    public Map<String,Boolean> getSettings() {
        return globalSettingsService.getGlobalSettings();
    }

    @Tag(name = "settings API", description = "Метод устанавливает .")
    @PutMapping()
    public void putSettings(@RequestBody GlobalSettingsDTO globalSettingsDTO) {
        globalSettingsService.setGlobalSettings(globalSettingsDTO);
    }
}
