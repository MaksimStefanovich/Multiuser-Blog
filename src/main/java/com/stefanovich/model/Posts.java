package com.stefanovich.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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



    public List<Tags> getTags() {
        return tags;
    }


    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public Users getModerator() {
        return moderator;
    }

    public void setModerator(Users moderator) {
        this.moderator = moderator;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
