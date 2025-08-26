package com.javagame.Repo.faridabad;

import com.javagame.Entity.User;
import com.javagame.Entity.delhi.delhiBet;
import com.javagame.Entity.faridabad.faridabadBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface faridabadBetRepo extends JpaRepository<faridabadBet, Long> {
    List<faridabadBet> findByGameNo(Integer gameNo);


    List<faridabadBet> findByUserOrderByCreatedAtDesc(User user);

    List<faridabadBet> findAllByOrderByCreatedAtDesc();

    List<faridabadBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime createdAtAfter);

}