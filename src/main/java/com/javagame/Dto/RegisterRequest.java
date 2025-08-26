package com.javagame.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String referredBy; // optional
}
