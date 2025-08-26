package com.javagame.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class UnifiedBetDTO {
    private String userName;
    private int gameNo;
    private String selectedNumber;
    private double amount;
    private String status;
    private LocalDateTime createdAt;

    // constructor, getters and setters
}
