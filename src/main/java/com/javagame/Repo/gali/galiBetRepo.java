package com.javagame.Repo.gali;

import com.javagame.Entity.Gali.galiBet;
import com.javagame.Entity.User;
import com.javagame.Entity.faridabad.faridabadBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface galiBetRepo extends JpaRepository<galiBet, Long> {
    List<galiBet> findByGameNo(Integer gameNo);


    List<galiBet> findByUserOrderByCreatedAtDesc(User user);

    List<galiBet> findAllByOrderByCreatedAtDesc();

    List<galiBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime createdAtAfter);

}