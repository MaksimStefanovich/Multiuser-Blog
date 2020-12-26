package com.stefanovich.repository;

import com.stefanovich.model.CaptchaCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaptchaCodesRepository extends JpaRepository<CaptchaCodes, Integer> {

 @Query("SELECT c from CaptchaCodes c " +
         "WHERE c.code = :code " +
         "and c.secretCode = :secretCode ")
    Optional<CaptchaCodes> findByCode(String code, String secretCode);
}
