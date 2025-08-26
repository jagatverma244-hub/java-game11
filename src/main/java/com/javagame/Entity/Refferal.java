package com.javagame.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Refferal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @ManyToOne
    @JoinColumn(name = "referrer_id")  // FK to User
    private User referrer;

    @OneToOne
    @JoinColumn(name = "referred_id", unique = true)  // FK to User
    private User referred;            // FK to User
 private    Double bonusGiven;          // ₹40
   private LocalDateTime referredAt;

}
