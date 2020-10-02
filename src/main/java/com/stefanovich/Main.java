package com.stefanovich;

import com.stefanovich.model.*;
import com.stefanovich.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    UsersRepository usersRepository;
    MessagesRepository messagesRepository;
    PostCommentsRepository postCommentsRepository;
    PostVotesRepository postVotesRepository;
    TagsRepository tagsRepository;

    public Main(UsersRepository usersRepository, MessagesRepository messagesRepository, PostCommentsRepository postCommentsRepository, PostVotesRepository postVotesRepository, TagsRepository tagsRepository) {
        this.usersRepository = usersRepository;
        this.postCommentsRepository = postCommentsRepository;
        this.messagesRepository = messagesRepository;
        this.postVotesRepository = postVotesRepository;
        this.tagsRepository = tagsRepository;
    }

}
