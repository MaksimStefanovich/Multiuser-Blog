package com.stefanovich.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Schema(name = "ChangePasswordDto", description = "Dto for Change password")
public class ChangePasswordDto {
    @NotBlank(message = "Ссылка для восстановления пароля устарела. <a href= " +
            "\"/auth/restor\">Запросить ссылку снова</a>")
    @Schema(name = "code")
    String code;

    @NotBlank(message = "Пароль короче 6-ти символов")
    @Size(min = 6, message = "Пароль короче 6-ти символов")
    @Schema(name = "password")
    String password;

    @NotBlank(message = "Код с картинки введён неверно")
    @Schema(name = "captcha")
    String captcha;

    @JsonProperty("captcha_secret")
    @Schema(name = "captcha secret")
    String captchaSecret;
}
