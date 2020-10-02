package com.stefanovich.repository;

import com.stefanovich.model.Tags;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends CrudRepository<Tags, Integer> {
}
