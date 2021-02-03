package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ChangePasswordDto {
    @NotBlank(message = "Ссылка для восстановления пароля устарела. <a href= " +
            "\"/auth/restor\">Запросить ссылку снова</a>")
    String code;

    @NotBlank(message = "Пароль короче 6-ти символов")
    @Size(min = 6, message = "Пароль короче 6-ти символов")
    String password;

    @NotBlank(message = "Код с картинки введён неверно")
    String captcha;

    @JsonProperty("captcha_secret")
    String captchaSecret;
}
