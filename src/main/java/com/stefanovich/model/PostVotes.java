package com.stefanovich.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_votes")
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
    private Boolean value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Posts getMessages() {
        return messages;
    }

    public void setMessages(Posts messages) {
        this.messages = messages;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
