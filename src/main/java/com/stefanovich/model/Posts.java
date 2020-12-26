package com.stefanovich.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "is_active", columnDefinition = "TINYINT")
    @NotNull
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status")
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    @ManyToOne
    private Users moderator;

    @ManyToOne
    @NotNull
    private Users user;

    @Column(columnDefinition = "DATETIME")
    @NotNull
    private LocalDateTime time;

    @NotNull
    private String title;

    @Column(columnDefinition = "text")
    @NotNull
    private String text;

    @Column(name = "view_count")
    private Integer viewCount;

    @ManyToMany
    private List<Tags> tags = new ArrayList<>();

    @OneToMany(mappedBy = "messages")
    private List<PostComments> postComments = new ArrayList<>();

    @OneToMany(mappedBy = "messages")
    private List<PostVotes> postVotes = new ArrayList<>();




}
