package com.javagame.Controller;

import com.javagame.Config.GameUtil;
import com.javagame.Dto.UnifiedBetDTO;
import com.javagame.Entity.*;
import com.javagame.Entity.Disawar.DisawarBet;
import com.javagame.Entity.Disawar.DisawargameResult;
import com.javagame.Entity.Disawar.DisawarharupBet;
import com.javagame.Entity.Gali.galiBet;
import com.javagame.Entity.Gali.galigameResult;
import com.javagame.Entity.Gali.galiharupBet;
import com.javagame.Entity.delhi.DelhiharupBet;
import com.javagame.Entity.delhi.delhiBet;
import com.javagame.Entity.delhi.delhigameResult;
import com.javagame.Entity.faridabad.faridabadBet;
import com.javagame.Entity.faridabad.faridabadgameResult;
import com.javagame.Entity.faridabad.faridabadharupBet;
import com.javagame.Entity.goa.GoaBet;
import com.javagame.Entity.goa.GoagameResult;
import com.javagame.Entity.goa.GoaharupBet;
import com.javagame.Repo.*;

import com.javagame.Repo.Disawar.DisawarBetRepo;
import com.javagame.Repo.Disawar.DisawarGameResultRepo;
import com.javagame.Repo.Disawar.DisawarHarupBetRepository;
import com.javagame.Repo.delhi.DelhiGameResultRepo;
import com.javagame.Repo.delhi.DelhiHarupBetRepository;
import com.javagame.Repo.delhi.delhiBetRepo;
import com.javagame.Repo.faridabad.faridabadBetRepo;
import com.javagame.Repo.faridabad.faridabadGameResultRepo;
import com.javagame.Repo.faridabad.faridabadHarupBetRepository;
import com.javagame.Repo.gali.galiBetRepo;
import com.javagame.Repo.gali.galiGameResultRepo;
import com.javagame.Repo.gali.galiHarupBetRepository;
import com.javagame.Repo.goarepo.GoaBetRepo;
import com.javagame.Repo.goarepo.GoaGameResultRepo;
import com.javagame.Repo.goarepo.GoaHarupBetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private GameResultRepo gameResultRepo;
    @Autowired
    private withdrawRepo withdrawRepository;
    @Autowired
    private DelhiGameResultRepo delhiGameResultRepo;
    @Autowired
    private delhiBetRepo delhiBetrepo;
    @Autowired
    private faridabadBetRepo faridabadbetRepo;
    @Autowired
    private faridabadGameResultRepo faridabadgameResultRepo;
    @Autowired
    private faridabadHarupBetRepository faridabadharupBetRepository;

    @Autowired
    private galiBetRepo galibetRepo;
    @Autowired
    private galiGameResultRepo galigameResultRepo;
    @Autowired
    private galiHarupBetRepository galiharupBetRepository;


    @Autowired
    private DisawarBetRepo DisawarbetRepo;
    @Autowired
    private DisawarGameResultRepo DisawargameResultRepo;
    @Autowired
    private DisawarHarupBetRepository DisawarharupBetRepository;




    @Autowired
    private GoaGameResultRepo goaGameResultRepo;
    @Autowired
    private GoaBetRepo goaBetRepo;
    @Autowired
    private GoaHarupBetRepository goaHarupBetRepository;
    @Autowired
    private DelhiHarupBetRepository delhiHarupBetRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BetRepo betRepo;
    @Autowired
    private GameUtil gameUtil;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private HarupBetRepository harupBetRepo;
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AdminpaymentImagesetRepo imagesetRepo;

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Admin/admin-deshboard";
    }

    @GetMapping("/declare-result")
    public String showDeclareResultForm() {
        return "Admin/declare-result-form";
    }

//    @PostMapping("/declare-result")
//    public String declareResult(@RequestParam Integer winningNumber) {
//        Integer currentGameNo = gameUtil.getCurrentGameNo();
//
//        // 1. Save result in GameResult table
//        GameResult result = new GameResult();
//        result.setGameNo(currentGameNo);
//        result.setWinningNumber(winningNumber);
//        result.setDeclaredAt(LocalDateTime.now());
//        gameResultRepo.save(result);
//
//        // 2. Fetch all bets of this game and admin
//        List<Bet> bets = betRepo.findByGameNo(currentGameNo);
//        User admin = userRepo.findByRole("ROLE_ADMIN");
//
//        double totalPayout = 0.0;
//        double totalLoss = 0.0;
//
//        for (Bet bet : bets) {
//            User user = bet.getUser();
//            double amount = bet.getAmount();
//
//            if (bet.getSelectedNumber().equals(winningNumber)) {
//
//                double winningAmount = amount * 9.5;
//
//                // Add winning to user's wallet
//                user.setWalletBalance(user.getWalletBalance() + winningAmount);
//
//                // Mark bet as WON
//                bet.setStatus("WON");
//
//                // Admin pays this amount
//                totalPayout += winningAmount;
//
//            } else {
//
//                bet.setStatus("LOST");
//
//                // Admin gains this amount
//                totalLoss += amount;
//            }
//
//            // Save user and bet
//            userRepo.save(user);
//            betRepo.save(bet);
//        }
//
//        // 3. Final admin wallet update
//        admin.setWalletBalance(admin.getWalletBalance() + totalLoss - totalPayout);
//        userRepo.save(admin);
//
//        return "redirect:/admin/result-summary"; // or your success page
//    }

    @PostMapping("/declare-result")
    public String declareResult(@RequestParam Integer winningNumber) {
        Integer currentGameNo = gameUtil.getCurrentGameNo();

        // 1. Save result in GameResult table
        GameResult result = new GameResult();
        result.setGameNo(currentGameNo);
        result.setWinningNumber(winningNumber);
        result.setDeclaredAt(LocalDateTime.now());
        gameResultRepo.save(result);

        // Extract Bahar and Under from result like 74 => B: 7, U: 4
        String resultStr = String.format("%02d", winningNumber); // always 2-digit
        int baharDigit = Integer.parseInt(resultStr.substring(0, 1));
        int underDigit = Integer.parseInt(resultStr.substring(1, 2));

        // 2. Fetch all bets of this game and admin
        List<Bet> numberBets = betRepo.findByGameNo(currentGameNo); // for 1–100 game
        List<HarupBet> harupBets = harupBetRepo.findByGameNo(currentGameNo); // Harup
        User admin = userRepo.findByRole("ROLE_ADMIN");

        double totalPayout = 0.0;
        double totalLoss = 0.0;

        // === 1–100 Number Game Logic ===
        for (Bet bet : numberBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();

            if (bet.getSelectedNumber().equals(winningNumber)) {
                double winningAmount = amount * 95;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            betRepo.save(bet);
        }

        // === Harup (Under–Bahar) Game Logic ===
        for (HarupBet bet : harupBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();
            boolean isWon = false;

            if (bet.getType().equals("BAHAR") && bet.getDigit() == baharDigit) {
                isWon = true;
            } else if (bet.getType().equals("UNDER") && bet.getDigit() == underDigit) {
                isWon = true;
            }

            if (isWon) {
                double winningAmount = amount * 9.5;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            harupBetRepo.save(bet);
        }

        // 3. Final admin wallet update
        admin.setWalletBalance(admin.getWalletBalance() + totalLoss - totalPayout);
        userRepo.save(admin);

        return "redirect:/admin/result-summary";
    }


    // Result Summary View (after declaring result)
    @GetMapping("/result-summary")
    public String getResultSummary(Model model) {
        Integer currentGameNo = gameUtil.getCurrentGameNo();


        List<GameResult> results = gameResultRepo.findByGameNoOrderByDeclaredAtDesc(currentGameNo);
        List<Bet> bets = betRepo.findByGameNo(currentGameNo);
        if (!results.isEmpty()) {
            GameResult latestResult = results.get(0);
            model.addAttribute("gameResult", latestResult.getWinningNumber());
        } else {
            model.addAttribute("gameResult", null);
        }

        model.addAttribute("bets", bets); // List of bets

        return "Admin/admin-result-summary";
    }


    @GetMapping("/updatepassword")
    public String showPasswordUpdatePage(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(defaultValue = "0") int page,
                                         Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> users;

        if (keyword != null && !keyword.isBlank()) {
            users = userRepo.findByUEmailContainingIgnoreCaseOrMobileNumberContaining(keyword, keyword, pageable);
        } else {
            users = userRepo.findAll(pageable);
        }

        model.addAttribute("user", users.getContent());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentpage", page);
        model.addAttribute("keyword", keyword);

        return "Admin/updatepassword";
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("id") Long id,
                                 @RequestParam("newPassword") String newPassword,
                                 RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
            redirectAttributes.addFlashAttribute("success", "Password updated successfully for " + user.getUEmail());
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found.");
        }
        return "redirect:/admin/updatepassword";
    }









    // All Bets View (history)
//    @GetMapping("/all-bets")
//    public String viewAllBets(Model model) {
//        List<Bet> allBets = betRepo.findAllByOrderByCreatedAtDesc();
//        model.addAttribute("bets", allBets);
//        return "Admin/admin-all-bets";
//    }
//-------------------------------------

    @GetMapping("/all-bets")
    public String viewAllBets(Model model) {
        List<UnifiedBetDTO> combinedBets = new ArrayList<>();

        List<Bet> allBets = betRepo.findAllByOrderByCreatedAtDesc();
        for (Bet bet : allBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getSelectedNumber()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        List<HarupBet> allHarupBets = harupBetRepo.findAllByOrderByCreatedAtDesc();
        for (HarupBet bet : allHarupBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getDigit()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        combinedBets.sort(Comparator.comparing(UnifiedBetDTO::getCreatedAt).reversed());
        model.addAttribute("bets", combinedBets);
        return "Admin/delhi/admin-all-bets";
    }
   @GetMapping("/image")
    public String image(Model model) {
        model.addAttribute("setimage", new AdminSetpaymentImage());
        return "/Admin/admin-image";
    }

    @PostMapping("/imagesave")
    public String imageSave(AdminSetpaymentImage setimage, @RequestParam("file") MultipartFile file) throws IOException {


        setimage.setCreatedAt(LocalDateTime.now());
        setimage.setProfileImage(file.getBytes());


        imagesetRepo.save(setimage);
        return "redirect:/admin/image";


    }


    @GetMapping("/depositpayment")
    public String showAllPayments(Model model,
                                  @RequestParam(value = "success", required = false) String success,
                                  @RequestParam(value = "error", required = false) String error) {
        List<Transaction> allTransactions = transactionRepo.findAll();

        // Sort by createdAt descending
        allTransactions.sort((a, b) -> b.getCreatedAtl().compareTo(a.getCreatedAtl()));

        model.addAttribute("transactions", allTransactions);
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "Admin/admin-deposit-payment";
    }


    @PostMapping("/depositpayment/submit")
    public String handleDepositUpdate(@RequestParam("transactionId") Long transactionId,
                                      @RequestParam("status") String status,
                                      RedirectAttributes redirectAttributes) {

        Optional<Transaction> optionalTransaction = transactionRepo.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            redirectAttributes.addAttribute("error", "Transaction not found.");
            return "redirect:/admin/depositpayment";
        }

        Transaction txn = optionalTransaction.get();

        if ("SUCCESS".equalsIgnoreCase(txn.getStatus())) {
            redirectAttributes.addAttribute("error", "Already marked as SUCCESS.");
            return "redirect:/admin/depositpayment";
        }

        txn.setStatus(status);

        if ("SUCCESS".equalsIgnoreCase(status)) {
            User user = txn.getUser();
            double txnAmount = txn.getAmount() != null ? txn.getAmount() : 0.0;

            // ✅ Step 1: Add deposit amount to user's wallet
            double currentUserBalance = user.getWalletBalance() != null ? user.getWalletBalance() : 0.0;
            user.setWalletBalance(currentUserBalance + txnAmount);
            userRepo.save(user);

            // ✅ Step 2: Handle 5% referral bonus if user was referred by someone
            String referredByCode = user.getReferralBy();
            if (referredByCode != null && !referredByCode.isBlank()) {
                Optional<User> optionalReferrer = userRepo.findByReferralCode(referredByCode);
                if (optionalReferrer.isPresent()) {
                    User referrer = optionalReferrer.get();
                    double bonus = txnAmount * 0.05;
                    double referrerBalance = referrer.getWalletBalance() != null ? referrer.getWalletBalance() : 0.0;
                    referrer.setWalletBalance(referrerBalance + bonus);
                    userRepo.save(referrer);
                }
            }
        }

        transactionRepo.save(txn);
        redirectAttributes.addAttribute("success", "Transaction updated successfully.");
        return "redirect:/admin/depositpayment";
    }


    @GetMapping("/withdrawpayment")
    public String showallPayments(Model model,
                                  @RequestParam(value = "success",required = false )String success,
                                  @RequestParam(value = "error",required = false)String error
                                  ) {
        List<withdraw> allwithdraw =  withdrawRepository.findAll();
        allwithdraw.sort((a,b) ->b.getCreatedAtl().compareTo(a.getCreatedAtl()));
        model.addAttribute("withdraws", allwithdraw);
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "Admin/admin-withdraw-payment";

    }
    @PostMapping("/withdrawpayment/submit")
    public String handleWithdrawPayment(@RequestParam("transactionId") Long transactionId,
                                        @RequestParam("status") String status,
                                        RedirectAttributes redirectAttributes) {
        Optional<withdraw> optionalWithdraw = withdrawRepository.findById(transactionId);
        if (optionalWithdraw.isEmpty()) {
            redirectAttributes.addAttribute("error", "Transaction not found.");
            return "redirect:/admin/withdrawpayment";
        }

        withdraw txn = optionalWithdraw.get();

        // Prevent re-processing
        if ("SUCCESS".equalsIgnoreCase(txn.getStatus()) || "FAILED".equalsIgnoreCase(txn.getStatus())) {
            redirectAttributes.addAttribute("error", "Transaction already processed.");
            return "redirect:/admin/withdrawpayment";
        }

        txn.setStatus(status);
        User user = txn.getUser();
        double currentBalance = user.getWalletBalance() != null ? user.getWalletBalance() : 0.0;
        double txnAmount = txn.getAmount() != null ? txn.getAmount() : 0.0;

        if ("FAILED".equalsIgnoreCase(status)) {
            // Refund amount to user
            user.setWalletBalance(currentBalance + txnAmount);
            userRepo.save(user);
        }

        if ("SUCCESS".equalsIgnoreCase(status)) {
            // ✅ Referral bonus logic (5% of withdrawn amount to referrer)
            String referredByCode = user.getReferralBy();
            if (referredByCode != null && !referredByCode.isBlank()) {
                Optional<User> optionalReferrer = userRepo.findByReferralCode(referredByCode);
                if (optionalReferrer.isPresent()) {
                    User referrer = optionalReferrer.get();
                    double bonus = txnAmount * 0.00;
                    double refBalance = referrer.getWalletBalance() != null ? referrer.getWalletBalance() : 0.0;
                    referrer.setWalletBalance(refBalance + bonus);
                    userRepo.save(referrer);
                }
            }

        }

        withdrawRepository.save(txn);
        redirectAttributes.addAttribute("success", "Transaction updated successfully.");
        return "redirect:/admin/withdrawpayment";
    }







//  delhi    ---------------------

    @GetMapping("/delhideclare-result")
    public String showdelhiDeclareResultForm() {
        return "Admin/delhi/declare-result-form";
    }

    @PostMapping("/delhideclare-result")
    public String delhideclareResult(@RequestParam Integer winningNumber) {
        Integer currentGameNo = gameUtil.getCurrentGameNo();

        // 1. Save result in GameResult table
        delhigameResult result = new delhigameResult();
        result.setGameNo(currentGameNo);
        result.setWinningNumber(winningNumber);
        result.setDeclaredAt(LocalDateTime.now());
        delhiGameResultRepo.save(result);

        // Extract Bahar and Under from result like 74 => B: 7, U: 4
        String resultStr = String.format("%02d", winningNumber); // always 2-digit
        int baharDigit = Integer.parseInt(resultStr.substring(0, 1));
        int underDigit = Integer.parseInt(resultStr.substring(1, 2));

        // 2. Fetch all bets of this game and admin
        List<delhiBet> numberBets = delhiBetrepo.findByGameNo(currentGameNo); // for 1–100 game
        List<DelhiharupBet> harupBets = delhiHarupBetRepository.findByGameNo(currentGameNo); // Harup
        User admin = userRepo.findByRole("ROLE_ADMIN");

        double totalPayout = 0.0;
        double totalLoss = 0.0;

        // === 1–100 Number Game Logic ===
        for (delhiBet bet : numberBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();

            if (bet.getSelectedNumber().equals(winningNumber)) {
                double winningAmount = amount * 95;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
           delhiBetrepo.save(bet);
        }

        // === Harup (Under–Bahar) Game Logic ===
        for (DelhiharupBet bet : harupBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();
            boolean isWon = false;

            if (bet.getType().equals("BAHAR") && bet.getDigit() == baharDigit) {
                isWon = true;
            } else if (bet.getType().equals("UNDER") && bet.getDigit() == underDigit) {
                isWon = true;
            }

            if (isWon) {
                double winningAmount = amount * 9.5;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            delhiHarupBetRepository.save(bet);
        }

        // 3. Final admin wallet update
        admin.setWalletBalance(admin.getWalletBalance() + totalLoss - totalPayout);
        userRepo.save(admin);

        return "redirect:/admin/delhiresult-summary";
    }


    // Result Summary View (after declaring result)
    @GetMapping("/delhiresult-summary")
    public String getdelhiResultSummary(Model model) {
        Integer currentGameNo = gameUtil.getCurrentGameNo();


        List<delhigameResult> results = delhiGameResultRepo.findByGameNoOrderByDeclaredAtDesc(currentGameNo);
        List<delhiBet> bets = delhiBetrepo.findByGameNo(currentGameNo);
        if (!results.isEmpty()) {
            delhigameResult latestResult = results.get(0);
            model.addAttribute("gameResult", latestResult.getWinningNumber());
        } else {
            model.addAttribute("gameResult", null);
        }

        model.addAttribute("bets", bets); // List of bets

        return "Admin/delhi/admin-result-summary";
    }


    // All Bets View (history)
    @GetMapping("/delhiall-bets")
    public String viewdelhiAllBets(Model model) {
        List<UnifiedBetDTO> combinedBets = new ArrayList<>();

        List<delhiBet> allBets = delhiBetrepo.findAllByOrderByCreatedAtDesc();
        for (delhiBet bet : allBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getSelectedNumber()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        List<DelhiharupBet> allHarupBets = delhiHarupBetRepository.findAllByOrderByCreatedAtDesc();
        for (DelhiharupBet bet : allHarupBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getDigit()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        combinedBets.sort(Comparator.comparing(UnifiedBetDTO::getCreatedAt).reversed());
        model.addAttribute("bets", combinedBets);
        return "Admin/delhi/admin-all-bets";
    }




//    ---------------------------------------------///-------------------------------------
    //---------Goa--------------------------------//----------------------
@GetMapping("/Goadeclare-result")
public String showGoaDeclareResultForm() {
    return "Admin/goa/declare-result-form";
}

    @PostMapping("/goadeclare-result")
    public String GoadeclareResult(@RequestParam Integer winningNumber) {
        Integer currentGameNo = gameUtil.getCurrentGameNo();

        // 1. Save result in GameResult table
        GoagameResult result = new GoagameResult();
        result.setGameNo(currentGameNo);
        result.setWinningNumber(winningNumber);
        result.setDeclaredAt(LocalDateTime.now());
        goaGameResultRepo.save(result);

        // Extract Bahar and Under from result like 74 => B: 7, U: 4
        String resultStr = String.format("%02d", winningNumber); // always 2-digit
        int baharDigit = Integer.parseInt(resultStr.substring(0, 1));
        int underDigit = Integer.parseInt(resultStr.substring(1, 2));

        // 2. Fetch all bets of this game and admin
        List<GoaBet> numberBets = goaBetRepo.findByGameNo(currentGameNo); // for 1–100 game
        List<GoaharupBet> harupBets = goaHarupBetRepository.findByGameNo(currentGameNo); // Harup
        User admin = userRepo.findByRole("ROLE_ADMIN");

        double totalPayout = 0.0;
        double totalLoss = 0.0;

        // === 1–100 Number Game Logic ===
        for (GoaBet bet : numberBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();

            if (bet.getSelectedNumber().equals(winningNumber)) {
                double winningAmount = amount * 95;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            goaBetRepo.save(bet);
        }

        // === Harup (Under–Bahar) Game Logic ===
        for (GoaharupBet bet : harupBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();
            boolean isWon = false;

            if (bet.getType().equals("BAHAR") && bet.getDigit() == baharDigit) {
                isWon = true;
            } else if (bet.getType().equals("UNDER") && bet.getDigit() == underDigit) {
                isWon = true;
            }

            if (isWon) {
                double winningAmount = amount * 9.5;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            goaHarupBetRepository.save(bet);
        }

        // 3. Final admin wallet update
        admin.setWalletBalance(admin.getWalletBalance() + totalLoss - totalPayout);
        userRepo.save(admin);

        return "redirect:/admin/goaresult-summary";
    }


    // Result Summary View (after declaring result)
    @GetMapping("/goaresult-summary")
    public String getgoaResultSummary(Model model) {
        Integer currentGameNo = gameUtil.getCurrentGameNo();


        List<GoagameResult> results = goaGameResultRepo.findByGameNoOrderByDeclaredAtDesc(currentGameNo);
        List<GoaBet> bets = goaBetRepo.findByGameNo(currentGameNo);
        if (!results.isEmpty()) {
            GoagameResult latestResult = results.get(0);
            model.addAttribute("gameResult", latestResult.getWinningNumber());
        } else {
            model.addAttribute("gameResult", null);
        }

        model.addAttribute("bets", bets); // List of bets

        return "Admin/goa/admin-result-summary";
    }


    // All Bets View (history)
    @GetMapping("/goaall-bets")
    public String viewgoaallBets(Model model) {
        List<UnifiedBetDTO> combinedBets = new ArrayList<>();

        List<GoaBet> allBets = goaBetRepo.findAllByOrderByCreatedAtDesc();
        for (GoaBet bet : allBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getSelectedNumber()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        List<GoaharupBet> allHarupBets = goaHarupBetRepository.findAllByOrderByCreatedAtDesc();
        for (GoaharupBet bet : allHarupBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getDigit()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        combinedBets.sort(Comparator.comparing(UnifiedBetDTO::getCreatedAt).reversed());
        model.addAttribute("bets", combinedBets);
        return "Admin/goa/admin-all-bets";
    }
//---------------------------------faridabad------------------------
//    --------------------------//----------------------------
    //--------------------------------faridabad--------------


    @GetMapping("/faridabaddeclare-result")
    public String showfaridabadDeclareResultForm() {
        return "Admin/faridabad/faridabaddeclare-result-form";
    }

    @PostMapping("/faridabaddeclare-result")
    public String faridabaddeclareResult(@RequestParam Integer winningNumber) {
        Integer currentGameNo = gameUtil.getCurrentGameNo();

        // 1. Save result in GameResult table
        faridabadgameResult result = new faridabadgameResult();
        result.setGameNo(currentGameNo);
        result.setWinningNumber(winningNumber);
        result.setDeclaredAt(LocalDateTime.now());
        faridabadgameResultRepo.save(result);

        // Extract Bahar and Under from result like 74 => B: 7, U: 4
        String resultStr = String.format("%02d", winningNumber); // always 2-digit
        int baharDigit = Integer.parseInt(resultStr.substring(0, 1));
        int underDigit = Integer.parseInt(resultStr.substring(1, 2));

        // 2. Fetch all bets of this game and admin
        List<faridabadBet> numberBets = faridabadbetRepo.findByGameNo(currentGameNo); // for 1–100 game
        List<faridabadharupBet> harupBets = faridabadharupBetRepository.findByGameNo(currentGameNo); // Harup
        User admin = userRepo.findByRole("ROLE_ADMIN");

        double totalPayout = 0.0;
        double totalLoss = 0.0;

        // === 1–100 Number Game Logic ===
        for (faridabadBet bet : numberBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();

            if (bet.getSelectedNumber().equals(winningNumber)) {
                double winningAmount = amount * 95;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            faridabadbetRepo.save(bet);
        }

        // === Harup (Under–Bahar) Game Logic ===
        for (faridabadharupBet bet : harupBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();
            boolean isWon = false;

            if (bet.getType().equals("BAHAR") && bet.getDigit() == baharDigit) {
                isWon = true;
            } else if (bet.getType().equals("UNDER") && bet.getDigit() == underDigit) {
                isWon = true;
            }

            if (isWon) {
                double winningAmount = amount * 9.5;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            faridabadharupBetRepository.save(bet);
        }

        // 3. Final admin wallet update
        admin.setWalletBalance(admin.getWalletBalance() + totalLoss - totalPayout);
        userRepo.save(admin);

        return "redirect:/admin/faridabadresult-summary";
    }


    // Result Summary View (after declaring result)
    @GetMapping("/faridabadresult-summary")
    public String getfaridabadResultSummary(Model model) {
        Integer currentGameNo = gameUtil.getCurrentGameNo();


        List<faridabadgameResult> results = faridabadgameResultRepo.findByGameNoOrderByDeclaredAtDesc(currentGameNo);
        List<faridabadBet> bets = faridabadbetRepo.findByGameNo(currentGameNo);
        if (!results.isEmpty()) {
            faridabadgameResult latestResult = results.get(0);
            model.addAttribute("gameResult", latestResult.getWinningNumber());
        } else {
            model.addAttribute("gameResult", null);
        }

        model.addAttribute("bets", bets); // List of bets

        return "Admin/faridabad/faridabadadmin-result-summary";
    }


    // All Bets View (history)
    @GetMapping("/faridabadall-bets")
    public String viewfaridabadallBets(Model model) {
        List<UnifiedBetDTO> combinedBets = new ArrayList<>();

        List<faridabadBet> allBets = faridabadbetRepo.findAllByOrderByCreatedAtDesc();
        for (faridabadBet bet : allBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getSelectedNumber()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        List<faridabadharupBet> allHarupBets = faridabadharupBetRepository.findAllByOrderByCreatedAtDesc();
        for (faridabadharupBet bet : allHarupBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getDigit()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        combinedBets.sort(Comparator.comparing(UnifiedBetDTO::getCreatedAt).reversed());
        model.addAttribute("bets", combinedBets);
        return "Admin/faridabad/faridabadadmin-all-bets";
    }

//----------------gali--------------------gali----------------------gali
//    ------------------------gali-------------gali--------------





    @GetMapping("/galideclare-result")
    public String showgaliDeclareResultForm() {
        return "Admin/gali/galideclare-result-form";
    }

    @PostMapping("/galideclare-result")
    public String galideclareResult(@RequestParam Integer winningNumber) {
        Integer currentGameNo = gameUtil.getgaliCurrentGameno();

        // 1. Save result in GameResult table
        galigameResult result = new galigameResult();
        result.setGameNo(currentGameNo);
        result.setWinningNumber(winningNumber);
        result.setDeclaredAt(LocalDateTime.now());
        galigameResultRepo.save(result);

        // Extract Bahar and Under from result like 74 => B: 7, U: 4
        String resultStr = String.format("%02d", winningNumber); // always 2-digit
        int baharDigit = Integer.parseInt(resultStr.substring(0, 1));
        int underDigit = Integer.parseInt(resultStr.substring(1, 2));

        // 2. Fetch all bets of this game and admin
        List<galiBet> numberBets = galibetRepo.findByGameNo(currentGameNo); // for 1–100 game
        List<galiharupBet> harupBets = galiharupBetRepository.findByGameNo(currentGameNo); // Harup
        User admin = userRepo.findByRole("ROLE_ADMIN");

        double totalPayout = 0.0;
        double totalLoss = 0.0;

        // === 1–100 Number Game Logic ===
        for (galiBet bet : numberBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();

            if (bet.getSelectedNumber().equals(winningNumber)) {
                double winningAmount = amount * 95;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            galibetRepo.save(bet);
        }

        // === Harup (Under–Bahar) Game Logic ===
        for (galiharupBet bet : harupBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();
            boolean isWon = false;

            if (bet.getType().equals("BAHAR") && bet.getDigit() == baharDigit) {
                isWon = true;
            } else if (bet.getType().equals("UNDER") && bet.getDigit() == underDigit) {
                isWon = true;
            }

            if (isWon) {
                double winningAmount = amount * 9.5;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            galiharupBetRepository.save(bet);
        }

        // 3. Final admin wallet update
        admin.setWalletBalance(admin.getWalletBalance() + totalLoss - totalPayout);
        userRepo.save(admin);

        return "redirect:/admin/galiresult-summary";
    }


    // Result Summary View (after declaring result)
    @GetMapping("/galiresult-summary")
    public String getgaliResultSummary(Model model) {
        Integer currentGameNo = gameUtil.getgaliCurrentGameno();


        List<galigameResult> results = galigameResultRepo.findByGameNoOrderByDeclaredAtDesc(currentGameNo);
        List<galiBet> bets = galibetRepo.findByGameNo(currentGameNo);
        if (!results.isEmpty()) {
            galigameResult latestResult = results.get(0);
            model.addAttribute("gameResult", latestResult.getWinningNumber());
        } else {
            model.addAttribute("gameResult", null);
        }

        model.addAttribute("bets", bets); // List of bets

        return "Admin/gali/galiadmin-result-summary";
    }


    // All Bets View (history)
    @GetMapping("/galiall-bets")
    public String viewgaliallBets(Model model) {
        List<UnifiedBetDTO> combinedBets = new ArrayList<>();

        List<galiBet> allBets = galibetRepo.findAllByOrderByCreatedAtDesc();
        for (galiBet bet : allBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getSelectedNumber()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        List<galiharupBet> allHarupBets = galiharupBetRepository.findAllByOrderByCreatedAtDesc();
        for (galiharupBet bet : allHarupBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getDigit()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        combinedBets.sort(Comparator.comparing(UnifiedBetDTO::getCreatedAt).reversed());
        model.addAttribute("bets", combinedBets);
        return "Admin/gali/galiadmin-all-bets";
    }
//---------------------------disawar-------------disawar//------------//--------------------
//---------------------------------------//---------------//-///---------------------//-
//--------------disawar---------------//------------disawar-------------------


    @GetMapping("/Disawardeclare-result")
    public String showDisawarDeclareResultForm() {
        return "Admin/Disawar/disawardeclare-result-form";
    }

    @PostMapping("/Disawardeclare-result")
    public String DisawardeclareResult(@RequestParam Integer winningNumber) {
        Integer currentGameNo = gameUtil.getDisawarCurrentGameno();

        // 1. Save result in GameResult table
        DisawargameResult result = new DisawargameResult();
        result.setGameNo(currentGameNo);
        result.setWinningNumber(winningNumber);
        result.setDeclaredAt(LocalDateTime.now());
        DisawargameResultRepo.save(result);

        // Extract Bahar and Under from result like 74 => B: 7, U: 4
        String resultStr = String.format("%02d", winningNumber); // always 2-digit
        int baharDigit = Integer.parseInt(resultStr.substring(0, 1));
        int underDigit = Integer.parseInt(resultStr.substring(1, 2));

        // 2. Fetch all bets of this game and admin
        List<DisawarBet> numberBets = DisawarbetRepo.findByGameNo(currentGameNo); // for 1–100 game
        List<DisawarharupBet> harupBets = DisawarharupBetRepository.findByGameNo(currentGameNo); // Harup
        User admin = userRepo.findByRole("ROLE_ADMIN");

        double totalPayout = 0.0;
        double totalLoss = 0.0;

        // === 1–100 Number Game Logic ===
        for (DisawarBet bet : numberBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();

            if (bet.getSelectedNumber().equals(winningNumber)) {
                double winningAmount = amount * 95;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            DisawarbetRepo.save(bet);
        }

        // === Harup (Under–Bahar) Game Logic ===
        for (DisawarharupBet bet : harupBets) {
            User user = bet.getUser();
            double amount = bet.getAmount();
            boolean isWon = false;

            if (bet.getType().equals("BAHAR") && bet.getDigit() == baharDigit) {
                isWon = true;
            } else if (bet.getType().equals("UNDER") && bet.getDigit() == underDigit) {
                isWon = true;
            }

            if (isWon) {
                double winningAmount = amount * 9.5;
                user.setWalletBalance(user.getWalletBalance() + winningAmount);
                bet.setStatus("WON");
                totalPayout += winningAmount;
            } else {
                bet.setStatus("LOST");
                totalLoss += amount;
            }

            userRepo.save(user);
            DisawarharupBetRepository.save(bet);
        }

        // 3. Final admin wallet update
        admin.setWalletBalance(admin.getWalletBalance() + totalLoss - totalPayout);
        userRepo.save(admin);

        return "redirect:/admin/Disawarresult-summary";
    }


    // Result Summary View (after declaring result)
    @GetMapping("/Disawarresult-summary")
    public String getDisawarResultSummary(Model model) {
        Integer currentGameNo = gameUtil.getDisawarCurrentGameno();


        List<DisawargameResult> results = DisawargameResultRepo.findByGameNoOrderByDeclaredAtDesc(currentGameNo);
        List<DisawarBet> bets = DisawarbetRepo.findByGameNo(currentGameNo);
        if (!results.isEmpty()) {
            DisawargameResult latestResult = results.get(0);
            model.addAttribute("gameResult", latestResult.getWinningNumber());
        } else {
            model.addAttribute("gameResult", null);
        }

        model.addAttribute("bets", bets); // List of bets

        return "Admin/Disawar/disawaradmin-result-summary";
    }


    // All Bets View (history)
    @GetMapping("/Disawarall-bets")
    public String viewDisawarallBets(Model model) {
        List<UnifiedBetDTO> combinedBets = new ArrayList<>();

        List<DisawarBet> allBets = DisawarbetRepo.findAllByOrderByCreatedAtDesc();
        for (DisawarBet bet : allBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getSelectedNumber()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        List<DisawarharupBet> allHarupBets = DisawarharupBetRepository.findAllByOrderByCreatedAtDesc();
        for (DisawarharupBet bet : allHarupBets) {
            UnifiedBetDTO dto = new UnifiedBetDTO();
            dto.setUserName(bet.getUser().getUName());
            dto.setGameNo(bet.getGameNo());
            dto.setSelectedNumber(String.valueOf(bet.getDigit()));
            dto.setAmount(bet.getAmount());
            dto.setStatus(bet.getStatus());
            dto.setCreatedAt(bet.getCreatedAt());
            combinedBets.add(dto);
        }

        combinedBets.sort(Comparator.comparing(UnifiedBetDTO::getCreatedAt).reversed());
        model.addAttribute("bets", combinedBets);
        return "Admin/Disawar/disawaradmin-all-bets";
    }


}

