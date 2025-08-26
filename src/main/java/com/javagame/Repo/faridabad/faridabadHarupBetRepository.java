package com.javagame.Repo.faridabad;

import com.javagame.Entity.User;
import com.javagame.Entity.delhi.DelhiharupBet;
import com.javagame.Entity.faridabad.faridabadharupBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface faridabadHarupBetRepository extends JpaRepository<faridabadharupBet, Long> {
    List<faridabadharupBet> findByGameNo(Integer gameNo);

    List<faridabadharupBet> findAllByOrderByCreatedAtDesc();
    List<faridabadharupBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime dateTime);
}
