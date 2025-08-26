package com.javagame.Repo.delhi;

import com.javagame.Entity.Bet;
import com.javagame.Entity.User;
import com.javagame.Entity.delhi.delhiBet;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface delhiBetRepo extends JpaRepository<delhiBet, Long> {
    List<delhiBet> findByGameNo(Integer gameNo);


    List<delhiBet> findByUserOrderByCreatedAtDesc(User user);

    List<delhiBet> findAllByOrderByCreatedAtDesc();

    List<delhiBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime createdAtAfter);

}