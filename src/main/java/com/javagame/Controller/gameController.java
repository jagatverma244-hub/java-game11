package com.javagame.Controller;

import com.javagame.Config.GameUtil;
import com.javagame.Dto.BetRequest;
import com.javagame.Entity.Bet;
import com.javagame.Entity.HarupBet;
import com.javagame.Entity.User;
import com.javagame.Repo.BetRepo;
import com.javagame.Repo.HarupBetRepository;
import com.javagame.Repo.UserRepo;
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
@RequestMapping("/game")
public class gameController {

    @Autowired
    private BetRepo betRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BetService betService;
    @Autowired
    private HarupBetRepository harupBetRepo;

    @Autowired
    private GameUtil gameUtil;

    // ============================
    // 🔹 1 to 100 Game Page
    // ============================
    @GetMapping("/game1")
    public String showGamePage(Model model) {
        model.addAttribute("betRequest", new BetRequest());
        return "/user/game";  // game.html
    }

    @PostMapping("/placeBet")
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
            return "redirect:/game/game1";
        }

        Bet bet = new Bet();
        bet.setUser(user);
        bet.setAmount(betRequest.getAmount());
        bet.setSelectedNumber(betRequest.getSelectedNumber());
        bet.setGameNo(gameUtil.getCurrentGameNo());
        bet.setCreatedAt(LocalDateTime.now());
        bet.setStatus("PENDING");

        betRepo.save(bet);

        user.setWalletBalance(user.getWalletBalance() - betRequest.getAmount());
        userRepo.save(user);

        redirectAttributes.addFlashAttribute("success", "Bet placed successfully!");
        return "redirect:/game/game1";
    }

    // ============================
    // 🔸 UnderBahr (Harup) Game Page
    // ============================
    @GetMapping("/gameharup")
    public String showHarupPage(Model model) {
        model.addAttribute("betRequest", new BetRequest());
        return "/user/gameharup";  // gameharup.html
    }

    @PostMapping("/placebetharup")
    public String placeHarupBet(@ModelAttribute BetRequest betRequest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("error", "Please login first.");
            return "user/gameharup";
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            model.addAttribute("error", "Invalid session. Please login again.");
            return "user/gameharup";
        }

        if (user.getWalletBalance() < betRequest.getAmount()) {
            model.addAttribute("error", "Insufficient wallet balance!");
            return "user/gameharup";
        }

        HarupBet harupBet = new HarupBet();
        harupBet.setUser(user);
        harupBet.setAmount(betRequest.getAmount());
        harupBet.setDigit(betRequest.getSelectedNumber());
        harupBet.setType(betRequest.getType());
        harupBet.setGameNo(gameUtil.getCurrentGameNo());
        harupBet.setCreatedAt(LocalDateTime.now());
        harupBet.setStatus("PENDING");

        harupBetRepo.save(harupBet);

        user.setWalletBalance(user.getWalletBalance() - betRequest.getAmount());
        userRepo.save(user);

        model.addAttribute("success", "Harup Bet placed successfully!");
        return "redirect:/game/gameharup";
    }


    // ============================
    // 🔹 Bet History Page (Optional)
    // ============================
    @GetMapping("/bet")
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
        model.addAttribute("currentGameNo", gameUtil.getCurrentGameNo());
        return "user/my-bets";
    }


    // Optional direct bet without modal (not used in your HTML)
    @PostMapping("/place-bet")
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
            return "redirect:/game/bet";
        }

        Bet bet = new Bet();
        bet.setUser(user);
        bet.setSelectedNumber(selectedNumber);
        bet.setAmount(amount);
        bet.setGameNo(gameUtil.getCurrentGameNo());
        bet.setCreatedAt(LocalDateTime.now());
        bet.setStatus("PENDING");

        user.setWalletBalance(user.getWalletBalance() - amount);
        userRepo.save(user);
        betRepo.save(bet);

        redirectAttributes.addFlashAttribute("success", "Bet placed successfully!");
        return "redirect:/game/bet";
    }

}
