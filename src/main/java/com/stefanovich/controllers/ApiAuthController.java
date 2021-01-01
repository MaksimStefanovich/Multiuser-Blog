package com.stefanovich.controllers;

import com.stefanovich.dto.*;
import com.stefanovich.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final AuthService authService;


    @GetMapping("/check")
    public AuthLoginDto getAuthUser() {
        return authService.getCheck();

    }

    @PostMapping("/register")
    public Map<String, Object> addUser(@RequestBody @Valid AddUserDto addUserDto) {
       return authService.addUser(addUserDto);
    }

    @PostMapping("/restore")
    public Map<String, Boolean> restore(@RequestBody EmailDto emailDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", authService.restoreEmail(emailDto.getEmail()));
        return map;
    }

    @PostMapping("/password")
    public Map<String, Object> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {

      return authService.change(changePasswordDto);



    }

    @GetMapping("/captcha")
    public CaptchaDto makeCaptcha() throws IOException {
        return authService.makeCaptchaDto();
    }

    @GetMapping("/logout")
    public Map<String, Boolean> getLogout() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", true);
        return map;
    }

}
