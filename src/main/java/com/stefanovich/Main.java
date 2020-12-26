package com.stefanovich;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanovich.model.*;
import com.stefanovich.repository.*;
import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
       ZonedDateTime localDateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        System.out.println("localDateTime = " + localDateTime.format(dateTimeFormatter));
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
