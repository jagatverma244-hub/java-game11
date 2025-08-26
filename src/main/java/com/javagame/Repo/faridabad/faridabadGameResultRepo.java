package com.javagame.Repo.faridabad;

import com.javagame.Entity.delhi.delhigameResult;
import com.javagame.Entity.faridabad.faridabadgameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface faridabadGameResultRepo extends JpaRepository<faridabadgameResult, Long> {
    @Query("SELECT g FROM faridabadgameResult g WHERE g.gameNo = :gameNo ORDER BY g.declaredAt DESC")
    List<faridabadgameResult> findByGameNoOrderByDeclaredAtDesc(@Param("gameNo") Integer gameNo);

    Optional<faridabadgameResult> findTopByOrderByDeclaredAtDesc();
}


