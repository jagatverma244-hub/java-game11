package com.javagame.Repo.delhi;

import com.javagame.Entity.GameResult;
import com.javagame.Entity.delhi.delhigameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelhiGameResultRepo extends JpaRepository<delhigameResult, Long> {
    @Query("SELECT g FROM delhigameResult g WHERE g.gameNo = :gameNo ORDER BY g.declaredAt DESC")
    List<delhigameResult> findByGameNoOrderByDeclaredAtDesc(@Param("gameNo") Integer gameNo);

    Optional<delhigameResult> findTopByOrderByDeclaredAtDesc();
}


