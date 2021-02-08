package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Schema(name = "AddUserDto", description = "Dto for answer")
public class AddUserDto {
    @JsonProperty("e_mail")
    @NotBlank(message = "Этот e-mail уже зарегистрирован")
    @Schema(description = "email")
    private String email;

    @NotBlank(message = "Пароль не установлен")
    @Size(min = 6, message = "Пароль короче 6-ти символов")
    @Schema(description = "password")
    private String password;

    @NotBlank(message = "Имя указано неверно")
    @Size(min = 2, message = "Имя указано неверно")
    @Schema(description = "name")
    private String name;

    @NotBlank(message = "Код с картинки введён неверно")
    @Schema(description = "captcha")
    private String captcha;

    @JsonProperty("captcha_secret")
    @Schema(description = "captchaSecret")
    private String captchaSecret;
}
