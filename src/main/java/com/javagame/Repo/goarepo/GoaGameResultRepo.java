package com.javagame.Repo.goarepo;

import com.javagame.Entity.delhi.delhigameResult;
import com.javagame.Entity.goa.GoagameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoaGameResultRepo extends JpaRepository<GoagameResult, Long> {
    @Query("SELECT g FROM GoagameResult g WHERE g.gameNo = :gameNo ORDER BY g.declaredAt DESC")
    List<GoagameResult> findByGameNoOrderByDeclaredAtDesc(@Param("gameNo") Integer gameNo);

    Optional<GoagameResult> findTopByOrderByDeclaredAtDesc();
}


