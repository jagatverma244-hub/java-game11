package com.javagame.Entity.goa;

import com.javagame.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

public class GoaBet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer selectedNumber; // Number selected by the user (1–100)

    private Double amount; // Amount user bet

    private Integer gameNo; // Game number/session

    private String status; // PENDING / WON / LOST

    private LocalDateTime createdAt;

}
