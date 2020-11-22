package com.stefanovich.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "captcha_codes")
@Data
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "DATETIME")
    @NotNull
    private LocalDateTime time;

    @Column(columnDefinition = "TINYTEXT")
    @NotNull
    private String code;

    @Column(name = "secret_code", columnDefinition = "TINYTEXT")
    @NotNull
    private String secretCode;


}
