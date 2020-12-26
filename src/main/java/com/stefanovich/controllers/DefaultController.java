package com.stefanovich.controllers;

import com.stefanovich.dto.InitDto;
import com.stefanovich.dto.SettingsDTO;
import com.stefanovich.model.GlobalSettings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {


    @GetMapping("/api/init")
    public InitDto init() {

        return InitDto.builder()
                .title("DevPub")
                .subtitle("Рассказы разработчиков")
                .phone("+7 903 666-44-55")
                .email("mail@mail.ru")
                .copyright("Дмитрий Сергеев")
                .copyrightFrom("2005")
                .build();
    }

    @GetMapping("/api/settings")
    public SettingsDTO getSettings() {
        return SettingsDTO.builder()
                .multiuserMode(false)
                .postPremoderation(true)
                .statisticsIsPublic(true)
                .build();
    }

    @PutMapping("/api/settings")
    public void putSettings(@RequestBody SettingsDTO settingsDTO){
        GlobalSettings globalSettings = new GlobalSettings();
        globalSettings.setName("settingsDTO.getStatisticsIsPublic()");

    }


}
