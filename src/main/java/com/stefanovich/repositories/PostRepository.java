package com.stefanovich.repositories;

import com.stefanovich.model.Posts;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Posts, Integer>{
}
