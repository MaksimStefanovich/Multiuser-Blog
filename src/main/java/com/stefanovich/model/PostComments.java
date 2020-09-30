package com.stefanovich.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_comments")
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

    public PostComments getParent() {
        return parent;
    }

    public void setParent(PostComments parent) {
        this.parent = parent;
    }

    public Posts getMessages() {
        return messages;
    }

    public void setMessages(Posts messages) {
        this.messages = messages;
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
