package com.javagame.Entity;


import com.javagame.Enums.Role;
import jakarta.persistence.*;
import lombok.*;


import java.time.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UId;
    private String UName;

    @Column(unique = true, nullable = false)
    private String UEmail;

    @Column(unique = true, nullable = false)
    private String mobileNumber;
    private String UPassword;
    private String referralCode;
    private String referralBy;

    private String role;

    private Double walletBalance;
private   Boolean isBlocked;

   private LocalDate createdAt;
    @OneToMany(mappedBy = "referrer")
    private List<Refferal> referralsMade;

    @OneToOne(mappedBy = "referred")
    private Refferal referralInfo;

    public <E> User(String uEmail, String uPassword, List<E> es) {
    }


    public String getUsername() {
        return this.getUEmail();
    }

}
