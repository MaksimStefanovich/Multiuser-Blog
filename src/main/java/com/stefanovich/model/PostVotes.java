package com.stefanovich.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_votes")
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "user_id",nullable = false)
    private int userId;
    @Column(name = "post_id",nullable = false)
    private int postId;
    @Column(columnDefinition="DATETIME not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Column(columnDefinition = "TINYINT",nullable = false)
    private int value;

    public Integer getId() {
        return id;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
