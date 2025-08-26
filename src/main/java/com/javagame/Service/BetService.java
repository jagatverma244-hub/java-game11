package com.javagame.Service;

import com.javagame.Entity.Bet;
import com.javagame.Entity.User;
import com.javagame.Repo.BetRepo;
import com.javagame.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BetService {

    @Autowired
    private BetRepo betRepo;

    @Autowired
    private UserRepo userRepo;

    public String placeBet(User user, Integer selectedNumber, Double amount) {
        // 1. Check if user is blocked
        if (user.getIsBlocked() != null && user.getIsBlocked()) {
            return "User is blocked";
        }

        // 2. Check wallet balance
        if (user.getWalletBalance() < amount) {
            return "Insufficient balance";
        }

        // 3. Deduct wallet
        user.setWalletBalance(user.getWalletBalance() - amount);
        userRepo.save(user);

        // 4. Save Bet
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setSelectedNumber(selectedNumber);
        bet.setAmount(amount);
        bet.setGameNo(getCurrentGameNo()); // logic for current game
        bet.setCreatedAt(LocalDateTime.now());
        bet.setStatus("PENDING");

        betRepo.save(bet);

        return "Bet Placed Successfully";
    }

    private Integer getCurrentGameNo() {
        // For now, dummy game no: always 1
        return 1;
    }
}
