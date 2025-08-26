package com.javagame.Dto;

import com.javagame.Enums.TransactionStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DepositRequest {

    private Integer DepositNumber;
    private Double Amount;
    private String status ;


    private LocalDateTime createdAtl;
}
