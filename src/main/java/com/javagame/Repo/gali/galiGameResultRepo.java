package com.javagame.Repo.gali;

import com.javagame.Entity.Gali.galigameResult;
import com.javagame.Entity.faridabad.faridabadgameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface galiGameResultRepo extends JpaRepository<galigameResult, Long> {
    @Query("SELECT g FROM galigameResult g WHERE g.gameNo = :gameNo ORDER BY g.declaredAt DESC")
    List<galigameResult> findByGameNoOrderByDeclaredAtDesc(@Param("gameNo") Integer gameNo);

    Optional<galigameResult> findTopByOrderByDeclaredAtDesc();
}


