package com.stefanovich.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_comments")
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "parent_id")
    private int parentId;
    @Column(name = "post_id", nullable = false)
    private int postId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(columnDefinition = "DATETIME not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Column(columnDefinition = "text", nullable = false)
    private String text;

    public Integer getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
