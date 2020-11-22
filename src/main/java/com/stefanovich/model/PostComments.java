package com.stefanovich.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_comments")
@Data
@NoArgsConstructor
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private PostComments parent;

    @ManyToOne
    @NotNull
    private Posts messages;

    @ManyToOne
    @NotNull
    private Users user;

    @Column(columnDefinition = "DATETIME")
    @NotNull
    private LocalDateTime time;

    @NotNull
    @Lob
    private String text;



}
