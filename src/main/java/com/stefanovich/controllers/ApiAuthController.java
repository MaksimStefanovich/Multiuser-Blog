package com.stefanovich.controllers;

import com.stefanovich.dto.*;
import com.stefanovich.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class ApiAuthController {
    private final AuthService authService;

    @Operation(summary = "Метод возвращает информацию о текущем авторизованном пользователе, " +
            "если он авторизован. Он должен проверять, сохранён ли идентификатор текущей сессии в списке авторизованных.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AuthLoginDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": false\n" +
                                    "}"
                    )
            }
    )
    @GetMapping("/check")
    public AuthLoginDto getAuthUser() {
        return authService.getCheck();
    }

    @Operation(summary = "Метод создаёт пользователя в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "{\n" +
                                    "\t\"result\": true\n" +
                                    "}"
                    ),
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": false\n" +
                                    "}")
            }
    )
    @PostMapping("/register")
    public Map<String, Object> addUser(@RequestBody @Valid AddUserDto addUserDto) {
        return authService.addUser(addUserDto);
    }

    @Operation(summary = "Метод проверяет наличие в базе пользователя с указанным e-mail. \" +\n" +
            "            \"Если пользователь найден, ему должно отправляться письмо со ссылкой на восстановление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "{\n" +
                                    "\t\"result\": true\n" +
                                    "}"
                    ),
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": false\n" +
                                    "}"
                    )
            }
    )
    @PostMapping("/restore")
    public Map<String, Boolean> restore(@RequestBody EmailDto emailDto) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", authService.restoreEmail(emailDto.getEmail()));
        return map;
    }

    @Operation(summary = "Метод проверяет корректность кода восстановления пароля (параметр code) \" +\n" +
            "            \"и корректность кодов капчи",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "{\n" +
                                    "\t\"result\": true\n" +
                                    "}"
                    ),
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": false\n" +
                                    "}"
                    )
            }
    )
    @PostMapping("/password")
    public Map<String, Object> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        return authService.change(changePasswordDto);
    }

    @Operation(summary = "Метод генерирует коды капчи, - отображаемый и секретный, - сохраняет их в базу данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/captcha")
    public CaptchaDto makeCaptcha() {
        return authService.makeCaptchaDto();
    }

    @Operation(summary = "Метод разлогинивает пользователя: удаляет идентификатор его сессии из списка авторизованных. \" +\n" +
            "            \"Всегда возвращает true, даже если идентификатор текущей сессии не найден в списке авторизованных.",
            responses = {
                    @ApiResponse(
                            description = "{\n" +
                                    "\t\"result\": true\n" +
                                    "}"
                    )
            }
    )
    @GetMapping("/logout")
    public Map<String, Boolean> getLogout() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("result", true);
        return map;
    }
}
