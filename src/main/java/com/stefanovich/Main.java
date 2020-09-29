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
    PostRepository postRepository;
    PostCommentsRepository postCommentsRepository;
    PostVotesRepository postVotesRepository;
    TagsRepository tagsRepository;

    public Main(UsersRepository usersRepository, PostRepository postRepository, PostCommentsRepository postCommentsRepository, PostVotesRepository postVotesRepository, TagsRepository tagsRepository) {
        this.usersRepository = usersRepository;
        this.postCommentsRepository = postCommentsRepository;
        this.postRepository = postRepository;
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

        Topic topic1 = new Topic();
        topic1.setActive(true);
        topic1.setModerationStatus(ModerationStatus.ACCEPTED);
        topic1.setText("Hello");
        topic1.setTitle("Hello");
        topic1.setUser(user1);
        topic1.setViewCount(2);
        topic1.setTime(LocalDateTime.now());
        topic1.getTags().add(tags1);
        topic1.getTags().add(tags2);

        Topic topic2 = new Topic();
        topic2.setActive(false);
        topic2.setModerationStatus(ModerationStatus.ACCEPTED);
        topic2.setText("Hi");
        topic2.setTitle("Hi");
        topic2.setUser(user2);
        topic2.setViewCount(2);
        topic2.setTime(LocalDateTime.now());
        topic2.getTags().add(tags3);

        Topic topic3 = new Topic();
        topic3.setActive(true);
        topic3.setModerationStatus(ModerationStatus.DECLINED);
        topic3.setText("Hello world");
        topic3.setTitle("Hello world");
        topic3.setUser(user1);
        topic3.setViewCount(2);
        topic3.setTime(LocalDateTime.now());
        topic3.getTags().add(tags3);
        topic3.getTags().add(tags2);
        topic3.getTags().add(tags1);

        postRepository.save(topic1);
        postRepository.save(topic2);
        postRepository.save(topic3);

        TopicVote topicVote1 = new TopicVote();
        topicVote1.setTopic(topic1);
        topicVote1.setTime(LocalDateTime.now());
        topicVote1.setUser(user1);
        topicVote1.setValue(true);

        TopicVote topicVote2 = new TopicVote();
        topicVote2.setTopic(topic2);
        topicVote2.setTime(LocalDateTime.now());
        topicVote2.setUser(user2);
        topicVote2.setValue(true);

        TopicVote topicVote3 = new TopicVote();
        topicVote3.setTopic(topic3);
        topicVote3.setTime(LocalDateTime.now());
        topicVote3.setUser(user3);
        topicVote3.setValue(true);

        postVotesRepository.save(topicVote1);
        postVotesRepository.save(topicVote2);
        postVotesRepository.save(topicVote3);

        TopicComment topicComment1 = new TopicComment();
        topicComment1.setTopic(topic1);
        topicComment1.setText("Nice");
        topicComment1.setUser(user1);
        topicComment1.setTime(LocalDateTime.now());

        TopicComment topicComment2 = new TopicComment();
        topicComment2.setTopic(topic1);
        topicComment2.setText("Good");
        topicComment2.setUser(user1);
        topicComment2.setTime(LocalDateTime.now());
        topicComment2.setParent(topicComment1);

        TopicComment topicComment3 = new TopicComment();
        topicComment3.setTopic(topic2);
        topicComment3.setText("Well");
        topicComment3.setUser(user2);
        topicComment3.setTime(LocalDateTime.now());

        TopicComment topicComment4 = new TopicComment();
        topicComment4.setTopic(topic3);
        topicComment4.setText("Bad");
        topicComment4.setUser(user2);
        topicComment4.setTime(LocalDateTime.now());

        postCommentsRepository.save(topicComment1);
        postCommentsRepository.save(topicComment2);
        postCommentsRepository.save(topicComment3);
        postCommentsRepository.save(topicComment4);
    }


//    @Override
//    public void run(String... args) {
//
//        Users user1 = new Users();
//        user1.setName("Max");
//        user1.setCode("123");
//        user1.setEmail("max@com");
//        user1.setPhoto("url");
//        user1.setModerator(false);
//        user1.setPassword("123");
//        user1.setRegTime(LocalDateTime.now());
//
//        Users user2 = new Users();
//        user2.setName("Den");
//        user2.setCode("1234");
//        user2.setEmail("den@com");
//        user2.setPhoto("http");
//        user2.setModerator(false);
//        user2.setPassword("1234");
//        user2.setRegTime(LocalDateTime.now());
//
//        Users user3 = new Users();
//        user3.setName("Ivan");
//        user3.setCode("12345");
//        user3.setEmail("ivan@com");
//        user3.setPhoto("windows");
//        user3.setModerator(true);
//        user3.setPassword("12345");
//        user3.setRegTime(LocalDateTime.now());
//
//        usersRepository.save(user1);
//        usersRepository.save(user2);
//        usersRepository.save(user3);
//
//        Tags tags1 = new Tags();
//        tags1.setName("the best");
//
//        Tags tags2 = new Tags();
//        tags2.setName("Belarus");
//
//        Tags tags3 = new Tags();
//        tags3.setName("Khabarovsk");
//
//        tagsRepository.save(tags1);
//        tagsRepository.save(tags2);
//        tagsRepository.save(tags3);
//
//        Topic post1 = new Topic();
//        post1.setActive(true);
//        post1.setModerationStatus(ModerationStatus.ACCEPTED);
//        post1.setText("Hello");
//        post1.setTitle("Hello");
//        post1.setUser(user1);
//        post1.setViewCount(2);
//        post1.setTime(LocalDateTime.now());
//        post1.getTags().add(tags1);
//        post1.getTags().add(tags2);
//
//        Topic post2 = new Topic();
//        post2.setActive(false);
//        post2.setModerationStatus(ModerationStatus.ACCEPTED);
//        post2.setText("Hi");
//        post2.setTitle("Hi");
//        post2.setUser(user2);
//        post2.setViewCount(2);
//        post2.setTime(LocalDateTime.now());
//        post2.getTags().add(tags3);
//
//        Topic post3 = new Topic();
//        post3.setActive(true);
//        post3.setModerationStatus(ModerationStatus.DECLINED);
//        post3.setText("Hello world");
//        post3.setTitle("Hello world");
//        post3.setUser(user1);
//        post3.setViewCount(2);
//        post3.setTime(LocalDateTime.now());
//        post3.getTags().add(tags3);
//        post3.getTags().add(tags2);
//        post3.getTags().add(tags1);
//
//        postRepository.save(post1);
//        postRepository.save(post2);
//        postRepository.save(post3);
//
//        TopicVote postVotes1 = new TopicVote();
//        postVotes1.setTopic(post1);
//        postVotes1.setTime(LocalDateTime.now());
//        postVotes1.setUser(user1);
//        postVotes1.setValue(true);
//
//        TopicVote postVotes2 = new TopicVote();
//        postVotes2.setTopic(post2);
//        postVotes2.setTime(LocalDateTime.now());
//        postVotes2.setUser(user2);
//        postVotes2.setValue(true);
//
//        TopicVote postVotes3 = new TopicVote();
//        postVotes3.setTopic(post3);
//        postVotes3.setTime(LocalDateTime.now());
//        postVotes3.setUser(user3);
//        postVotes3.setValue(true);
//
//        postVotesRepository.save(postVotes1);
//        postVotesRepository.save(postVotes2);
//        postVotesRepository.save(postVotes3);
//
//        TopicComment postComments1 = new TopicComment();
//        postComments1.setTopic(post1);
//        postComments1.setText("Nice");
//        postComments1.setUser(user1);
//        postComments1.setTime(LocalDateTime.now());
//
//        TopicComment postComments2 = new TopicComment();
//        postComments2.setTopic(post1);
//        postComments2.setText("Good");
//        postComments2.setUser(user1);
//        postComments2.setTime(LocalDateTime.now());
//        postComments2.setParent(postComments1);
//
//        TopicComment postComments3 = new TopicComment();
//        postComments3.setTopic(post2);
//        postComments3.setText("Well");
//        postComments3.setUser(user2);
//        postComments3.setTime(LocalDateTime.now());
//
//        TopicComment postComments4 = new TopicComment();
//        postComments4.setTopic(post3);
//        postComments4.setText("Bad");
//        postComments4.setUser(user2);
//        postComments4.setTime(LocalDateTime.now());
//
//        postCommentsRepository.save(postComments1);
//        postCommentsRepository.save(postComments2);
//        postCommentsRepository.save(postComments3);
//        postCommentsRepository.save(postComments4);
//
//
//    }
}
