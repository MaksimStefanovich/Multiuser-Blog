package com.stefanovich.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
    @Autowired

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
