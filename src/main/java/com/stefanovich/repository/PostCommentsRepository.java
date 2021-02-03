package com.stefanovich.repository;

import com.stefanovich.model.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComments, Integer> {
    @Query("SELECT p FROM PostComments p " +
            "JOIN p.messages m " +
            "WHERE m.id = :id ")
    List<PostComments> findByAllPost(Integer id);
}
