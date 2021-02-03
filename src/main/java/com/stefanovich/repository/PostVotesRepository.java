package com.stefanovich.repository;

import com.stefanovich.model.PostVotes;
import com.stefanovich.model.Posts;
import com.stefanovich.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes, Integer> {
    @Query("SELECT p FROM PostVotes p WHERE p.user = :user " +
            "and p.messages = :post ")
    Optional<PostVotes> findByPostIdAndUserId(Users user, Posts post);

    @Query("SELECT p FROM PostVotes p " +
            "JOIN p.messages m " +
            "WHERE m.id = :id ")
    List<PostVotes> findByAllPost(Integer id);
}


