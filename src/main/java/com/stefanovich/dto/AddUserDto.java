package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AddUserDto {

    @JsonProperty("e_mail")
    @NotBlank(message = "Этот e-mail уже зарегистрирован")
    private String email;

    @NotBlank(message = "Пароль не установлен")
    @Size(min = 6, message = "Пароль короче 6-ти символов")
    private String password;

    @NotBlank(message = "Имя указано неверно")
    @Size(min = 2, message = "Имя указано неверно")
    private String name;

    @NotBlank(message =  "Код с картинки введён неверно")
    private String captcha;

    @JsonProperty("captcha_secret")
    private String captchaSecret;


}
