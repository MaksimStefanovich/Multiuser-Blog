package com.stefanovich.repository;

import com.stefanovich.model.ModerationStatus;
import com.stefanovich.model.Posts;
import com.stefanovich.model.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {


    List<Posts> findAllByIsActiveTrueAndAndModerationStatusAndTimeIsBefore(ModerationStatus moderationStatus,
                                                                           LocalDateTime time, Pageable pageable);


    @Query("SELECT p FROM Posts p WHERE p.title LIKE %:query% and p.isActive = true " +
            "and p.moderationStatus = 'ACCEPTED'" +
            "and p.time <= CURRENT_TIMESTAMP ")
    List<Posts> findAllByQuery(@Param("query") String query, Pageable pageable);


    @Query("SELECT p FROM Posts p WHERE p.isActive = true " +
            "and p.moderationStatus = 'ACCEPTED'" +
            "and p.time <= CURRENT_TIMESTAMP and p.time  >= :localDateTime and p.time  <= :localDateTime1")
    List<Posts> findAllByDate(Pageable pageable, LocalDateTime localDateTime,
                              LocalDateTime localDateTime1);


    @Query("SELECT p FROM Posts p " +
            "JOIN p.tags t " +
            "WHERE p.isActive = true " +
            "and p.moderationStatus = 'ACCEPTED' " +
            "and p.time <= CURRENT_TIMESTAMP " +
            "and t.name = :tagName")
    List<Posts> findByTag(String tagName, Pageable pageable);


    @Query("SELECT p FROM Posts p WHERE p.isActive = true " +
            "and p.moderator.isModerator = true and p.moderationStatus = :status ")
    List<Posts> findAllModeration(Pageable pageable, ModerationStatus status);


    @Query("SELECT p FROM Posts p WHERE p.user = :user " +
            "and p.isActive = :isActive and p.moderationStatus = :status ")
    List<Posts> findByAllMyPost(Pageable pageable, ModerationStatus status, Users user, Boolean isActive);


    @Query("SELECT p FROM Posts p WHERE p.isActive = true " +
            "and p.moderationStatus = 'ACCEPTED'" +
            "and p.time <= CURRENT_TIMESTAMP ")
    List<Posts> findById();

    @Query("SELECT p FROM Posts p " +
            "JOIN p.postComments c WHERE " +
            "c.id = :post_id")
    List<Posts> findAllById(Integer post_id);

    @Query("SELECT count(p) FROM Posts p " +
            "JOIN p.tags t " +
            "WHERE t.id = :id ")
    Long countPostByTagId(Integer id);


    @Query("SELECT distinct year(p.time) as y FROM Posts p" +
            " order by y ")
    List<Integer> getYear();


    @Query("SELECT  p.time FROM Posts p" +
            " WHERE year(p.time) = :year")
    List<LocalDateTime> getYear(Integer year);

    @Query("SELECT p FROM Posts p " +
            "JOIN p.user u " +
            "WHERE u.id = :id ")
    List<Posts> findAllByUserId(Integer id);

    @Query("SELECT p FROM Posts p " +
            "JOIN p.user u " +
            "WHERE p.moderationStatus = 'NEW' " +
            "and u.id = :id")
    List<Posts> findAllUserId(Integer id);

    @Query("SELECT p FROM Posts p " +
            "WHERE p.id = :id ")
    Posts findByPost(Integer id);
}

