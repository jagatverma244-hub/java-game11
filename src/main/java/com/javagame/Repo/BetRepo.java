package com.javagame.Repo;

import com.javagame.Entity.Bet;
import com.javagame.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface BetRepo extends JpaRepository<Bet, Long> {
    List<Bet> findByGameNo(Integer gameNo);


    List<Bet> findByUserOrderByCreatedAtDesc(User user);

    List<Bet> findAllByOrderByCreatedAtDesc();

    List<Bet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime createdAtAfter);

}