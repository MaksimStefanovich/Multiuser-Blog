package com.stefanovich.repository;

import com.stefanovich.model.PostVotes;
import com.stefanovich.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes, Integer> {
}
