package com.stefanovich.repository;

import com.stefanovich.model.Posts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends CrudRepository<Posts, Integer>{
}
