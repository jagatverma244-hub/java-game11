package com.javagame.Repo.goarepo;

import com.javagame.Entity.User;
import com.javagame.Entity.delhi.delhiBet;
import com.javagame.Entity.goa.GoaBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface GoaBetRepo extends JpaRepository<GoaBet, Long> {
    List<GoaBet> findByGameNo(Integer gameNo);


    List<GoaBet> findByUserOrderByCreatedAtDesc(User user);

    List<GoaBet> findAllByOrderByCreatedAtDesc();

    List<GoaBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime createdAtAfter);

}