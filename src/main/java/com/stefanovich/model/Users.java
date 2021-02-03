package com.stefanovich.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "is_moderator", columnDefinition = "TINYINT")
    @NotNull
    private boolean isModerator;

    @Column(name = "reg_time", columnDefinition = "DATETIME")
    @NotNull
    private LocalDateTime regTime;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String code;

    @Column(columnDefinition = "text")
    private String photo;
}
