package com.javagame.Repo;

import com.javagame.Entity.HarupBet;
import com.javagame.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HarupBetRepository extends JpaRepository<HarupBet, Long> {
   List<HarupBet> findAllByOrderByCreatedAtDesc();


    List<HarupBet> findByGameNo(Integer gameNo);
    List<HarupBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime dateTime);


}
