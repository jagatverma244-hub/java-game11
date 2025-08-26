package com.javagame.Repo.Disawar;

import com.javagame.Entity.Disawar.DisawarBet;
import com.javagame.Entity.User;
import com.javagame.Entity.delhi.delhiBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface DisawarBetRepo extends JpaRepository<DisawarBet, Long> {
    List<DisawarBet> findByGameNo(Integer gameNo);


    List<DisawarBet> findByUserOrderByCreatedAtDesc(User user);

    List<DisawarBet> findAllByOrderByCreatedAtDesc();

    List<DisawarBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime createdAtAfter);

}