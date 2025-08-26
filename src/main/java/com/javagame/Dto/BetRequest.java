package com.javagame.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class BetRequest {
    private Integer selectedNumber;
    private Double amount;
    private String type;

    // --- Getters & Setters ---
    public Integer getSelectedNumber() {
        return selectedNumber;
    }

    public void setSelectedNumber(Integer selectedNumber) {
        this.selectedNumber = selectedNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

