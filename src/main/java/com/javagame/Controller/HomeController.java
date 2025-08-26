package com.javagame.Controller;
import com.javagame.Entity.Transaction;
import com.javagame.Entity.User;
import com.javagame.Entity.withdraw;
import com.javagame.Repo.Disawar.DisawarGameResultRepo;
import com.javagame.Repo.GameResultRepo;
import com.javagame.Repo.TransactionRepo;
import com.javagame.Repo.UserRepo;
import com.javagame.Repo.delhi.DelhiGameResultRepo;
import com.javagame.Repo.faridabad.faridabadGameResultRepo;
import com.javagame.Repo.gali.galiGameResultRepo;
import com.javagame.Repo.goarepo.GoaGameResultRepo;
import com.javagame.Repo.withdrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private withdrawRepo withdrawRepo;
    @Autowired
    private GameResultRepo ganeshResultRepo;

    @Autowired
    private DelhiGameResultRepo delhiResultRepo;

    @Autowired
    private GoaGameResultRepo goaResultRepo;

    @Autowired
    private faridabadGameResultRepo faridabadResultRepo;

    @Autowired
    private galiGameResultRepo galiResultRepo;

    @Autowired
    private DisawarGameResultRepo disawarResultRepo;


    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

//        if (principal == null) {
//            return "redirect:/user/login";
//        }
        String useremail = principal.getName();

        User userprofile = (User) ((Authentication) principal).getPrincipal();


        if (userprofile == null) {
            return "fail";

        }
        model.addAttribute("userdetails", userprofile);
        return "/user/profile";
    }
//        if (principal != null) {
//            String email = principal.getName();
//            Optional<User> userOptional = userRepo.findByUEmail(email);
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//                model.addAttribute("userdetails", user); // Send actual User
//                return "/user/profile";
//            }
//        }
//        return "redirect:/user/login";
//    }


//    @GetMapping("/homepage")
//    public String homepage(Model model, Principal principal) {
//        User user = (User) ((Authentication) principal).getPrincipal();
//        if (user == null) {
//            System.out.println("principal: " + principal.getName());
//            return "fail";
//        }
//
//
////        if (principal != null) {
////            String email = principal.getName();
////            User userOptional = userRepo.findByUEmail(email);
////
//
//
//        model.addAttribute("user", user);
//        return "user/home";
//
//
//    }



@GetMapping("/homepage")
public String homepage(Model model, Principal principal) {
    User user = (User) ((Authentication) principal).getPrincipal();
    if (user == null) {
        System.out.println("principal: " + principal.getName());
        return "fail";
    }

    model.addAttribute("user", user);

    // Fetch latest results
    ganeshResultRepo.findTopByOrderByDeclaredAtDesc().ifPresent(r -> model.addAttribute("ganeshResult", r.getWinningNumber()));
    delhiResultRepo.findTopByOrderByDeclaredAtDesc().ifPresent(r -> model.addAttribute("delhiResult", r.getWinningNumber()));
    goaResultRepo.findTopByOrderByDeclaredAtDesc().ifPresent(r -> model.addAttribute("goaResult", r.getWinningNumber()));
    faridabadResultRepo.findTopByOrderByDeclaredAtDesc().ifPresent(r -> model.addAttribute("faridabadResult", r.getWinningNumber()));
    galiResultRepo.findTopByOrderByDeclaredAtDesc().ifPresent(r -> model.addAttribute("galiResult", r.getWinningNumber()));
    disawarResultRepo.findTopByOrderByDeclaredAtDesc().ifPresent(r -> model.addAttribute("disawarResult", r.getWinningNumber()));

    return "user/home";
}

    @GetMapping("/showtrangaction")
    public String showtrangaction(Model model, Principal principal) {
        Authentication authentication = (Authentication) principal;
        User user = (User) authentication.getPrincipal();

        if (user == null) {
            return "redirect:/user/login";
        }

        List<Transaction> transactions = transactionRepo.findAllByUser_UId(user.getUId());
        List<withdraw> withdrawals = withdrawRepo.findAllByUser_UId(user.getUId());

        model.addAttribute("transactions", transactions);
        model.addAttribute("withdrawals", withdrawals);
        return "user/showtrangaction";
    }

    @GetMapping("/referral")
    public String referralPage(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal(); // logged-in user
        User user1 = userRepo.findByUEmail(user.getUEmail()); // get by email, not ID
        model.addAttribute("user", user1);
        return "/user/refereal"; // fix spelling
    }


    @GetMapping("/contact")
    public String contact(Model model, Principal principal) {
        return "user/contact";
    }
@GetMapping("/terms")
    public String tream(Model model, Principal principal) {
        return "user/terms";
}
}


