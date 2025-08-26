package com.javagame.Repo.goarepo;

import com.javagame.Entity.User;
import com.javagame.Entity.delhi.DelhiharupBet;
import com.javagame.Entity.goa.GoaharupBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GoaHarupBetRepository extends JpaRepository<GoaharupBet, Long> {
    List<GoaharupBet> findByGameNo(Integer gameNo);

    List<GoaharupBet> findAllByOrderByCreatedAtDesc();
    List<GoaharupBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime dateTime);
}
