package com.javagame.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private  String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Boolean isRevoked;

}
