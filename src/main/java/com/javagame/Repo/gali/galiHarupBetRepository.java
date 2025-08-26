package com.javagame.Repo.gali;

import com.javagame.Entity.Gali.galiharupBet;
import com.javagame.Entity.User;
import com.javagame.Entity.faridabad.faridabadharupBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface galiHarupBetRepository extends JpaRepository<galiharupBet, Long> {
    List<galiharupBet> findByGameNo(Integer gameNo);

    List<galiharupBet> findAllByOrderByCreatedAtDesc();
    List<galiharupBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime dateTime);
}
