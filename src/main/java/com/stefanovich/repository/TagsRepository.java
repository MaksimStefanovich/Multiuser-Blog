package com.stefanovich.repository;

import com.stefanovich.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {
    List<Tags> findByName(String name);

    @Query("SELECT t FROM Tags t WHERE t.name LIKE :query% ")
        List<Tags> findAllByQuery(@Param("query") String query);






}
