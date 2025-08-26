package com.javagame.Entity;



import com.javagame.Enums.TransactionStatus;
import com.javagame.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String type;       // Enum: DEPOSIT, WITHDRAW, BET, WIN, REFERRAL_BONUS, REFUND
    private Double amount;
    private String status ;
    @Column(name = "deposit_number")
    private Integer depositNumber;



    private String UTRNumber ;        // Notes like “Bet Refund for Round #5”
//    private Integer transactionNumber;
    private LocalDateTime createdAtl;
    @PrePersist
    protected void onCreate() {
        createdAtl = LocalDateTime.now();
    }

}
