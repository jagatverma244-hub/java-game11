package com.javagame.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String type;
    private Double amount;
    private String status ;
    private  String UPIID;
    @Column(name = "withdraw_number")
    private Integer withdrawNumber;


    private LocalDateTime createdAtl;
    @PrePersist
    protected void onCreate() {
        createdAtl = LocalDateTime.now();
    }

}
