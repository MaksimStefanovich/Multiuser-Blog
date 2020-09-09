package com.stefanovich.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "is_active", columnDefinition = "TINYINT", nullable = false)
    private int isActive;
    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", columnDefinition = "ModerationStatus default 'NEW'", nullable = false)
    private ModerationStatus moderationStatus = ModerationStatus.NEW;
    @Column(name = "moderator_id")
    private int moderatorId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(columnDefinition = "DATETIME not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "text", nullable = false)
    private String text;
    @Column(name = "view_count")
    private int viewCount;

    public Integer getId() {
        return id;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public int getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(int moderatorId) {
        this.moderatorId = moderatorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
