package com.javagame.Repo.delhi;

import com.javagame.Entity.HarupBet;
import com.javagame.Entity.User;
import com.javagame.Entity.delhi.DelhiharupBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DelhiHarupBetRepository extends JpaRepository<DelhiharupBet, Long> {
    List<DelhiharupBet> findByGameNo(Integer gameNo);

    List<DelhiharupBet> findAllByOrderByCreatedAtDesc();
    List<DelhiharupBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime dateTime);
}
