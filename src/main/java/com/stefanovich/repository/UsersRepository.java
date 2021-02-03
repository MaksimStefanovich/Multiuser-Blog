package com.stefanovich.repository;

import com.stefanovich.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
    @Query("SELECT u from Users u " +
            "WHERE u.email = :email ")
    Optional<Users> findByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.email LIKE %:query% ")
    List<Users> findByQuery(@Param("query")String email);
}
