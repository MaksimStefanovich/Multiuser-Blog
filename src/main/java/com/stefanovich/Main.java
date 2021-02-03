package com.stefanovich;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stefanovich.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
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
