package com.stefanovich.controllers;

import com.stefanovich.dto.GlobalSettingsDTO;
import com.stefanovich.service.GlobalSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/settings")
@RequiredArgsConstructor
public class ApiGlobalSettingsController {
    private final GlobalSettingsService globalSettingsService;

    @GetMapping()
    public Map<String,Boolean> getSettings() {
        return globalSettingsService.getGlobalSettings();
    }

    @PutMapping()
    public void putSettings(@RequestBody GlobalSettingsDTO globalSettingsDTO) {
        globalSettingsService.setGlobalSettings(globalSettingsDTO);
    }
}
