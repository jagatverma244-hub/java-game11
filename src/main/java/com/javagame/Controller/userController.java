package com.javagame.Controller;


import com.javagame.Dto.RegisterRequest;
import com.javagame.Entity.Bet;
import com.javagame.Entity.HarupBet;
import com.javagame.Entity.User;
import com.javagame.Entity.delhi.DelhiharupBet;
import com.javagame.Entity.delhi.delhiBet;
import com.javagame.Repo.BetRepo;
import com.javagame.Repo.HarupBetRepository;
import com.javagame.Repo.UserRepo;
import com.javagame.Repo.delhi.DelhiHarupBetRepository;
import com.javagame.Repo.delhi.delhiBetRepo;
import com.javagame.Service.JwtService;
import com.javagame.Service.RegisterService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserRepo loginsRepository;

@Autowired
private BetRepo betRepo;
@Autowired
private UserRepo userRepo;
@Autowired
private delhiBetRepo delbetRepo;
@Autowired
private HarupBetRepository harupBetRepository;

@Autowired
private DelhiHarupBetRepository delhiHarupBetRepository;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "/user/register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute RegisterRequest registerRequest, Model model) {
        boolean success = registerService.registerUser(registerRequest);
        if (success) {
            model.addAttribute("message", "Registration successful!");
            return "redirect:/user/login";
        } else {
            model.addAttribute("error", "User already exists or referral invalid.");
            return "/user/register";
        }
    }
//        @GetMapping("/login")
//        public String login(Model model) {
//            model.addAttribute("error", "email password is incorrect.");
//            return "user/login";
//        }
@GetMapping("/login")
public String login(HttpServletRequest request,@RequestParam(value = "logout",required = false) String logout, Model model) {

        if(logout != null) {
            model.addAttribute("logout",true);

        }
        if (jwtService.hasValidAccessToken(request) || jwtService.hasValidRefreshToken(request)){
            return "redirect:/user/deshboard";
        }
//    model.addAttribute("error", "email password is incorrect.");
    return "user/login";
}
@PostMapping("/login")
public String doLogin(@RequestParam String username,@RequestParam String password,
                     HttpServletResponse response,Model model) {

        try{Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepo.findByUEmail(username);
            String accessToken = jwtService.generateAccessToken(user, 60);
            String refreshToken = jwtService.generateRefreshToken(user,5);

            Cookie  accessCookie = new Cookie("accessToken",accessToken);
            accessCookie.setHttpOnly(true);
            accessCookie.setPath("/");
            accessCookie.setMaxAge(60*60); // 3600 seconds = 1 hour

            Cookie refreshCookie = new  Cookie("refreshToken",refreshToken);
             refreshCookie.setHttpOnly(true);
             refreshCookie.setPath("/");
             refreshCookie.setMaxAge(5*24*60*60);
             response.addCookie(accessCookie);
             response.addCookie(refreshCookie);
             return  "redirect:/user/deshboard";
        }catch (AuthenticationException ex){
            model.addAttribute("error",true);
            return "/user/login";
        }
}

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the authentication context
        SecurityContextHolder.clearContext();

        // Clear the cookies by setting maxAge to 0
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return "redirect:/user/login?logout=true";
    }





        @GetMapping("/deshboard")
        public String deshboard(Authentication auth, Model model) {
            String role = auth.getAuthorities().iterator().next().getAuthority();
            if (role.equals("ROLE_ADMIN")) {


                return "redirect:/admin/dashboard";
            }
            return "redirect:/home/homepage";


        }



    @GetMapping("/my-bets")
    public String viewMyBets(Model model, Principal principal) {
        Authentication authentication = (Authentication) principal;
        User user = (User) authentication.getPrincipal();

        if (user == null) {
            return "redirect:/user/login";
        }

        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);

        List<Bet> bets = betRepo.findByUserAndCreatedAtAfterOrderByCreatedAtDesc(user, oneDayAgo);
        List<HarupBet> gharupBets = harupBetRepository.findByUserAndCreatedAtAfterOrderByCreatedAtDesc(user, oneDayAgo);

        model.addAttribute("bets", bets);
        model.addAttribute("gharupBets", gharupBets);
        return "user/my-bets";
    }



    @GetMapping("/delhimy-bets")
    public String viewdelhiMyBets(Model model, Principal principal) {
        Authentication authentication = (Authentication) principal;
        User user = (User) authentication.getPrincipal();

        if (user == null) {
            return "redirect:/user/login";
        }
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(1);
        List<delhiBet> bets = delbetRepo.findByUserAndCreatedAtAfterOrderByCreatedAtDesc(user, threeDaysAgo);
        List<DelhiharupBet> gharupBets = delhiHarupBetRepository.findByUserAndCreatedAtAfterOrderByCreatedAtDesc(user,oneDayAgo );

        model.addAttribute("bets", bets);
        model.addAttribute("gharupBets", gharupBets);
        return "user/delhi/my-bets";
    }




}

