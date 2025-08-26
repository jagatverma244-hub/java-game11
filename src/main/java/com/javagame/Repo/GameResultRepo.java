package com.javagame.Repo;
import com.javagame.Entity.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface GameResultRepo extends JpaRepository<GameResult, Long> {
    @Query("SELECT g FROM GameResult g WHERE g.gameNo = :gameNo ORDER BY g.declaredAt DESC")
    List<GameResult> findByGameNoOrderByDeclaredAtDesc(@Param("gameNo") Integer gameNo);

    Optional<GameResult> findTopByOrderByDeclaredAtDesc();
}


