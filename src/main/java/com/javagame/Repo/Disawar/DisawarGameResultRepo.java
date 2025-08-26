package com.javagame.Repo.Disawar;

import com.javagame.Entity.Disawar.DisawargameResult;
import com.javagame.Entity.delhi.delhigameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DisawarGameResultRepo extends JpaRepository<DisawargameResult, Long> {
    @Query("SELECT g FROM DisawargameResult g WHERE g.gameNo = :gameNo ORDER BY g.declaredAt DESC")
    List<DisawargameResult> findByGameNoOrderByDeclaredAtDesc(@Param("gameNo") Integer gameNo);

    Optional<DisawargameResult> findTopByOrderByDeclaredAtDesc();
}


