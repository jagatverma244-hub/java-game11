package com.javagame.Controller;

import com.javagame.Dto.DepositRequest;
import com.javagame.Dto.withdrawRequest;
import com.javagame.Entity.AdminSetpaymentImage;
import com.javagame.Entity.Transaction;
import com.javagame.Entity.User;
import com.javagame.Entity.withdraw;
import com.javagame.Repo.AdminpaymentImagesetRepo;
import com.javagame.Repo.TransactionRepo;

import com.javagame.Repo.UserRepo;
import com.javagame.Repo.withdrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Base64;

@Controller
@RequestMapping("/payment")
public class UserPayment {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private withdrawRepo withdrawRepository;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AdminpaymentImagesetRepo imagesetRepo;

    // Step 1: User opens deposit form
    @GetMapping("/deposit")
    public String deposit(DepositRequest depositRequest, Model model) {
        model.addAttribute("depositRequest", depositRequest);
        return "/user/deposit";
    }

    // Step 2: User submits amount -> transaction saved -> redirect to show image
    @PostMapping("/paymentprocess")
    public String paymentprocess(DepositRequest depositRequest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/user/login?sessionExpired=true";
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            return "redirect:/user/login?sessionExpired=true";
        }

        Integer userLastDepositNo = transactionRepo.findMaxDepositNumberByUser(user.getUId());
        int nextDepositNo = (userLastDepositNo == null) ? 1 : userLastDepositNo + 1;

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(depositRequest.getAmount());
        transaction.setStatus("PENDING");
        transaction.setCreatedAtl(LocalDateTime.now());
        transaction.setDepositNumber(nextDepositNo);

        transactionRepo.save(transaction);

        return "redirect:/payment/image/show?txId=" + transaction.getId();
    }

    @GetMapping("/image/show")
    public String showImage(@RequestParam("txId") Long txId, Model model,
                            @ModelAttribute("message") String message,
                            @ModelAttribute("error") String error) {

        AdminSetpaymentImage img = imagesetRepo.findTopByOrderByIdDesc();

        if (img != null) {
            String base64Image = Base64.getEncoder().encodeToString(img.getProfileImage());
            model.addAttribute("image", base64Image);
        }

        model.addAttribute("txId", txId);

        // Show success message only if message exists
        if (message != null && !message.isEmpty()) {
            model.addAttribute("successMessage", message);
        }

        // Show error message only if error exists
        if (error != null && !error.isEmpty()) {
            model.addAttribute("errorMessage", error);
        }

        return "/user/show-image";
    }


    @PostMapping("/submit-utr")
    public String submitUtr(@RequestParam("txId") Long txId,
                            @RequestParam("utrNumber") String utrNumber,
                            RedirectAttributes redirectAttributes) {
        Transaction transaction = transactionRepo.findById(txId).orElse(null);

        if (transaction != null) {
            transaction.setUTRNumber(utrNumber);
            transaction.setStatus("PROCESSING");
            transactionRepo.save(transaction);
            redirectAttributes.addFlashAttribute("successMessage", "UTR submitted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Transaction not found.");
        }

        return "redirect:/payment/image/show?txId=" + txId;
    }
@GetMapping("/withdraw")
    private String withdraw(Model model, withdrawRequest  withdraw) {
    model.addAttribute("withdrawRequest", withdraw);
    return "/user/withdraw";
}
    @PostMapping("/withdrawprocess")
    public String withdrawprocess(withdrawRequest withdrawRequest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/user/login?sessionExpired=true";
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            return "redirect:/user/login?sessionExpired=true";
        }

        // Wallet balance check
        if (user.getWalletBalance() < withdrawRequest.getAmount()) {
            model.addAttribute("error", "Insufficient wallet balance for withdrawal.");
            return "/user/withdraw";
        }

        Integer userlastwithdraw = withdrawRepository.findMaxDepositNumberByUser(user.getUId());
        int nextWithdrawNo = (userlastwithdraw == null) ? 1 : userlastwithdraw + 1;


        user.setWalletBalance(user.getWalletBalance() - withdrawRequest.getAmount());
        userRepo.save(user);

        // Save withdrawal entry
        withdraw withowl = new withdraw();
        withowl.setUser(user);

        withowl.setAmount(withdrawRequest.getAmount());
        withowl.setStatus("PENDING");
        withowl.setUPIID(withdrawRequest.getUPIID());
        withowl.setCreatedAtl(LocalDateTime.now());
        withowl.setWithdrawNumber(nextWithdrawNo);
        withdrawRepository.save(withowl);

        return "/user/withdrawldone";
    }



}
