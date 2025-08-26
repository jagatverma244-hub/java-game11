package com.javagame.Repo.Disawar;

import com.javagame.Entity.Disawar.DisawarharupBet;
import com.javagame.Entity.User;
import com.javagame.Entity.delhi.DelhiharupBet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DisawarHarupBetRepository extends JpaRepository<DisawarharupBet, Long> {
    List<DisawarharupBet> findByGameNo(Integer gameNo);

    List<DisawarharupBet> findAllByOrderByCreatedAtDesc();
    List<DisawarharupBet> findByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime dateTime);
}
