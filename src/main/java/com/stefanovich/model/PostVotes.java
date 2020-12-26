package com.stefanovich.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_votes")
@Data
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @NotNull
    private Users user;

    @ManyToOne
    @NotNull
    private Posts messages;

    @Column(columnDefinition="DATETIME")
    @NotNull
    private LocalDateTime time;

    @Column(columnDefinition = "TINYINT")
    @NotNull
    private int value = 0;



}
