package com.stefanovich.controllers;

import com.stefanovich.dto.InitDto;
import org.springframework.web.bind.annotation.GetMapping;
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




}
