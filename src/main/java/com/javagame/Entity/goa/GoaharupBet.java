package com.javagame.Entity.goa;

import com.javagame.Entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class GoaharupBet {
    @Id
    @GeneratedValue
    private Long id;

    private Integer gameNo;

    @ManyToOne
    private User user;

    private String type; // "UNDER" or "BAHAR"
    private int digit;   // 0–9
    private double amount;



    private String status; // "WON" / "LOST"
    private LocalDateTime createdAt;

}
