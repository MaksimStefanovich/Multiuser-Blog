package com.stefanovich.repository;

import com.stefanovich.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {


    @Query("SELECT g.value from GlobalSettings  g " +
            "where g.code = 'statistics_is_public' ")
    String findByCode();




}
