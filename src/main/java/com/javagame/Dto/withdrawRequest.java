package com.javagame.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.PrimitiveIterator;

@Data
public class withdrawRequest {

    private Integer withdrawNumber;
    private Double Amount;
    private String status ;
    private  String UPIID;


    private LocalDateTime createdAtl;
}
