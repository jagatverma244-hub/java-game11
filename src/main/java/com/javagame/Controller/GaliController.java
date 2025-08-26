package com.javagame.Controller;

import com.javagame.Config.GameUtil;
import com.javagame.Dto.BetRequest;
import com.javagame.Entity.Gali.galiBet;
import com.javagame.Entity.Gali.galiharupBet;
import com.javagame.Entity.User;

import com.javagame.Entity.delhi.DelhiharupBet;
import com.javagame.Entity.delhi.delhiBet;
import com.javagame.Entity.faridabad.faridabadBet;
import com.javagame.Entity.faridabad.faridabadharupBet;
import com.javagame.Repo.UserRepo;
import com.javagame.Repo.delhi.DelhiHarupBetRepository;
import com.javagame.Repo.delhi.delhiBetRepo;
import com.javagame.Repo.faridabad.faridabadBetRepo;
import com.javagame.Repo.faridabad.faridabadHarupBetRepository;
import com.javagame.Repo.gali.galiBetRepo;
import com.javagame.Repo.gali.galiHarupBetRepository;
import com.javagame.Service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
@Controller
@RequestMapping("/gali")
public class GaliController {

    @Autowired
    private galiBetRepo betRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BetService betService;
    @Autowired
    private galiHarupBetRepository harupBetRepo;
    @Autowired
    private GameUtil gameUtil;
    @GetMapping("/galigame")
    public String showGamePage(Model model) {
        model.addAttribute("betRequest", new BetRequest());
        return "/user/gali/galigame";  // game.html
    }
    @PostMapping("/galiplaceBet")
    public String placeGame1Bet(@ModelAttribute BetRequest betRequest,
                                RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "Please login first.");
            return "redirect:/user/login";
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            redirectAttributes.addFlashAttribute("error", "Invalid session. Please login again.");
            return "redirect:/user/login";
        }

        if (user.getWalletBalance() < betRequest.getAmount()) {
            redirectAttributes.addFlashAttribute("error", "Insufficient wallet balance!");
            return "redirect:/gali/galigame";
        }

        galiBet bet = new galiBet();
        bet.setUser(user);
        bet.setAmount(betRequest.getAmount());
        bet.setSelectedNumber(betRequest.getSelectedNumber());
        bet.setGameNo(gameUtil.getgaliCurrentGameno());
        bet.setCreatedAt(LocalDateTime.now());
        bet.setStatus("PENDING");

        betRepo.save(bet);


        user.setWalletBalance(user.getWalletBalance() - betRequest.getAmount());
        userRepo.save(user);

        redirectAttributes.addFlashAttribute("success", "Bet placed successfully!");
        return "redirect:/gali/galigame";
    }

    // ============================
// 🔸 UnderBahr (Harup) Game Page
// ============================
    @GetMapping("/galigameharup")
    public String showHarupPage(Model model) {
        model.addAttribute("betRequest", new BetRequest());
        return "/user/gali/galigameharup";  // gameharup.html
    }

    @PostMapping("/galiplacebetharup")
    public String placeHarupBet(@ModelAttribute BetRequest betRequest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("error", "Please login first.");
            return "user/gali/galigameharup";
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            model.addAttribute("error", "Invalid session. Please login again.");
            return "user/gali/galigameharup";
        }

        if (user.getWalletBalance() < betRequest.getAmount()) {
            model.addAttribute("error", "Insufficient wallet balance!");
            return "user/gali/galigameharup";
        }

        galiharupBet harupBet = new galiharupBet();
        harupBet.setUser(user);
        harupBet.setAmount(betRequest.getAmount());
        harupBet.setDigit(betRequest.getSelectedNumber());
        harupBet.setType(betRequest.getType());
        harupBet.setGameNo(gameUtil.getgaliCurrentGameno());
        harupBet.setCreatedAt(LocalDateTime.now());
        harupBet.setStatus("PENDING");

        harupBetRepo.save(harupBet);

        user.setWalletBalance(user.getWalletBalance() - betRequest.getAmount());
        userRepo.save(user);

        model.addAttribute("success", "Harup Bet placed successfully!");
        return "redirect:/gali/galigameharup";
    }


    // ============================
// 🔹 Bet History Page (Optional)
// ============================
    @GetMapping("/galibet")
    public String showBetHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/user/login";
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            return "redirect:/user/login";
        }
        model.addAttribute("bets", betRepo.findByUserOrderByCreatedAtDesc(user));
        model.addAttribute("gharupBets",harupBetRepo.findAllByOrderByCreatedAtDesc());
        model.addAttribute("currentGameNo", gameUtil.getgaliCurrentGameno());
        return "user/gali/galimy-bets";
    }
    // Optional direct bet without modal (not used in your HTML)
    @PostMapping("/galiplace-bet")
    public String placeBetDirect(@RequestParam Integer selectedNumber,
                                 @RequestParam Double amount,
                                 RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "Please login first.");
            return "redirect:/user/login";
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            redirectAttributes.addFlashAttribute("error", "Invalid session. Please login again.");
            return "redirect:/user/login";
        }
        if (user.getWalletBalance() < amount) {
            redirectAttributes.addFlashAttribute("error", "Insufficient balance");
            return "redirect:/user/bet?error=Insufficient balance";
        }
        galiBet bet = new galiBet();
        bet.setUser(user);
        bet.setSelectedNumber(selectedNumber);
        bet.setAmount(amount);
        bet.setGameNo(gameUtil.getgaliCurrentGameno());
        bet.setCreatedAt(LocalDateTime.now());
        bet.setStatus("PENDING");
        user.setWalletBalance(user.getWalletBalance() - amount);
        userRepo.save(user);
        betRepo.save(bet);
        redirectAttributes.addFlashAttribute("success", "Bet placed successfully!");
        return "redirect:/gali/galibet";
    }
}

