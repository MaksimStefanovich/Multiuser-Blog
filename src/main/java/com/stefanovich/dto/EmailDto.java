package com.stefanovich.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "EmailDto", description = "Dto for Email")
public class EmailDto {
    @Schema(name = "email")
    private String email;
}
