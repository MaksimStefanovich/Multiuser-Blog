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

    @PostConstruct
    public void init() {
        Users user1 = new Users();
        user1.setName("Max");
        user1.setCode("123");
        user1.setEmail("max@com");
        user1.setPhoto("url");
        user1.setModerator(false);
        user1.setPassword("123");
        user1.setRegTime(LocalDateTime.now());

        Users user2 = new Users();
        user2.setName("Den");
        user2.setCode("1234");
        user2.setEmail("den@com");
        user2.setPhoto("http");
        user2.setModerator(false);
        user2.setPassword("1234");
        user2.setRegTime(LocalDateTime.now());

        Users user3 = new Users();
        user3.setName("Ivan");
        user3.setCode("12345");
        user3.setEmail("ivan@com");
        user3.setPhoto("windows");
        user3.setModerator(true);
        user3.setPassword("12345");
        user3.setRegTime(LocalDateTime.now());

        usersRepository.save(user1);
        usersRepository.save(user2);
        usersRepository.save(user3);

        Tags tags1 = new Tags();
        tags1.setName("the best");

        Tags tags2 = new Tags();
        tags2.setName("Belarus");

        Tags tags3 = new Tags();
        tags3.setName("Khabarovsk");

        tagsRepository.save(tags1);
        tagsRepository.save(tags2);
        tagsRepository.save(tags3);

        Posts messages1 = new Posts();
        messages1.setActive(true);
        messages1.setModerationStatus(ModerationStatus.ACCEPTED);
        messages1.setText("Hello");
        messages1.setTitle("Hello");
        messages1.setModerator(user3);
        messages1.setUser(user1);
        messages1.setViewCount(2);
        messages1.setTime(LocalDateTime.now());
        messages1.getTags().add(tags1);
        messages1.getTags().add(tags2);

        Posts messages2 = new Posts();
        messages2.setActive(false);
        messages2.setModerationStatus(ModerationStatus.ACCEPTED);
        messages2.setText("Hi");
        messages2.setTitle("Hi");
        messages2.setUser(user2);
        messages2.setModerator(user3);
        messages2.setViewCount(2);
        messages2.setTime(LocalDateTime.now());
        messages2.getTags().add(tags3);

        Posts messages3 = new Posts();
        messages3.setActive(true);
        messages3.setModerationStatus(ModerationStatus.DECLINED);
        messages3.setText("Hello world");
        messages3.setTitle("Hello world");
        messages3.setUser(user3);
        messages3.setModerator(user3);
        messages3.setViewCount(2);
        messages3.setTime(LocalDateTime.now());
        messages3.getTags().add(tags3);
        messages3.getTags().add(tags2);
        messages3.getTags().add(tags1);

        messagesRepository.save(messages1);
        messagesRepository.save(messages2);
        messagesRepository.save(messages3);

        PostVotes postVotes1 = new PostVotes();
        postVotes1.setMessages(messages1);
        postVotes1.setTime(LocalDateTime.now());
        postVotes1.setUser(user1);
        postVotes1.setValue(true);

        PostVotes postVotes2 = new PostVotes();
        postVotes2.setMessages(messages2);
        postVotes2.setTime(LocalDateTime.now());
        postVotes2.setUser(user2);
        postVotes2.setValue(true);

        PostVotes postVotes3 = new PostVotes();
        postVotes3.setMessages(messages3);
        postVotes3.setTime(LocalDateTime.now());
        postVotes3.setUser(user3);
        postVotes3.setValue(true);

        postVotesRepository.save(postVotes1);
        postVotesRepository.save(postVotes2);
        postVotesRepository.save(postVotes3);

        PostComments postComments1 = new PostComments();
        postComments1.setMessages(messages1);
        postComments1.setText("Nice");
        postComments1.setUser(user1);
        postComments1.setTime(LocalDateTime.now());

        PostComments postComments2 = new PostComments();
        postComments2.setMessages(messages1);
        postComments2.setText("Good");
        postComments2.setUser(user1);
        postComments2.setTime(LocalDateTime.now());
        postComments2.setParent(postComments1);

        PostComments postComments3 = new PostComments();
        postComments3.setMessages(messages2);
        postComments3.setText("Well");
        postComments3.setUser(user2);
        postComments3.setTime(LocalDateTime.now());

        PostComments postComments4 = new PostComments();
        postComments4.setMessages(messages3);
        postComments4.setText("Bad");
        postComments4.setUser(user2);
        postComments4.setTime(LocalDateTime.now());

        postCommentsRepository.save(postComments1);
        postCommentsRepository.save(postComments2);
        postCommentsRepository.save(postComments3);
        postCommentsRepository.save(postComments4);
    }

}
