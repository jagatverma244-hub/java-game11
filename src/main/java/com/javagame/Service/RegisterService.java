package com.javagame.Service;

import com.javagame.Dto.RegisterRequest;
import com.javagame.Entity.User;

import com.javagame.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterService {
    @Autowired
    private UserRepo userRepo;
    public Boolean registerUser(RegisterRequest dto){
        if(userRepo.existsByUEmail(dto.getEmail())|| userRepo.existsByMobileNumber(dto.getMobile())){
            return false;
        }
        User user = new User();
        user.setUName(dto.getName());
        user.setUEmail(dto.getEmail());
        user.setMobileNumber(dto.getMobile());
        String hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setUPassword(hashedPassword);
        user.setCreatedAt(LocalDate.now());
        user.setReferralCode(generateReferralCode());
        user.setRole("ROLE_USER");
        if(dto.getReferredBy()!= null && !dto.getReferredBy().isEmpty()){
            user.setReferralBy(dto.getReferredBy());
            Optional<User> referrer = userRepo.findByReferralCode(dto.getReferredBy());
        if(referrer.isPresent()) {
            user.setWalletBalance(1.0);
        }else {
            return false;
        }
        }else {
            user.setWalletBalance(0.0);
        }
        userRepo.save(user);
        return true;


        }
    private String generateReferralCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    }

