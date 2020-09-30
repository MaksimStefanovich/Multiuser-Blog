package com.stefanovich.repository;

import com.stefanovich.model.Posts;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Posts, Integer>{
}
