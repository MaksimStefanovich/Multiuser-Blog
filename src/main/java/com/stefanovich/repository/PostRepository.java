package com.stefanovich.repository;

import com.stefanovich.model.Topic;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Topic, Integer>{
}
