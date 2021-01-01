package com.stefanovich.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProfileMyDto {

    private String name;

    private String email;

    private String password;

    private Integer removePhoto = 0;


}
