package com.stefanovich.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileMyDto {
    private String name;

    private String email;

    private String password;

    private Integer removePhoto = 0;
}
