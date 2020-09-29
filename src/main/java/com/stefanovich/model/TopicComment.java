package com.stefanovich.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class TopicComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private TopicComment parent;

    @ManyToOne
    @NotNull
    private Topic topic;

    @ManyToOne
    @NotNull
    private Users user;

    @Column(columnDefinition = "DATETIME not null")
    private LocalDateTime time;
    @Column (nullable = false)
    @Lob
    private String text;

    public Integer getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TopicComment getParent() {
        return parent;
    }

    public void setParent(TopicComment parent) {
        this.parent = parent;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
