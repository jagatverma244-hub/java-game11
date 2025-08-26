package com.javagame.Service;

import com.javagame.Entity.User;
import com.javagame.Repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private final UserRepo userRepo;

    public JwtService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateAccessToken(User user, int minutes) {
        return generateToken(user, minutes, ChronoUnit.MINUTES, "access");

    }

    public String generateToken(User user, int amount, ChronoUnit unit, String type) {
        Instant now = Instant.now();
        return Jwts.builder().setSubject(user.getUEmail())
                .claim("role", user.getRole())
                .claim("type", type)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(amount, unit)))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(User user, int days) {
        return generateToken(user, days, ChronoUnit.DAYS, "refresh");
    }

    public boolean isValidAccessToken(String token) {
        return isValidToken(token, "access");
    }

    public boolean isValidRefreshToken(String token) {
        return isValidToken(token, "refresh");
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean isValidToken(String token, String expectedType) {
        try {
            Claims claims = parseClaims(token);
            return expectedType.equals(claims.get("type", String.class)) && claims.getExpiration().after(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("token validation failed : " + e.getMessage());
            return false;
        }
    }

    public Optional<User> getUserfromToken(String token) {
        try {
            Claims claims = parseClaims(token);
            String email = claims.getSubject();
            return Optional.ofNullable(userRepo.findByUEmail(email));


        } catch (Exception e) {
            System.out.println("token validation failed : " + e.getMessage());
            return Optional.empty();
        }


    }

    private Claims parseCLaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public Optional<TokenPair> refreshToken(String refreshtoken) {
        if(!isValidRefreshToken(refreshtoken)) return Optional.empty();
        Optional<User> userOpt = getUserfromToken(refreshtoken);
        if(userOpt.isEmpty()) return Optional.empty();
        User user = userOpt.get();
        String newAccess =  generateAccessToken(user, 60);
        String newRefresh = generateRefreshToken(user, 5);
         return Optional.of(new TokenPair(newAccess, newRefresh));
    }

    public static class TokenPair {
        public final String accessToken;
        public final String refreshToken;

        public TokenPair(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
    private Optional<String> getTokenFromCookie(HttpServletRequest request,String name){
        if(request.getCookies() == null) return Optional.empty();
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }




    public boolean hasValidAccessToken(HttpServletRequest request){
        return  getTokenFromCookie(request,"accessToken")
                .map(this::isValidAccessToken)
                .orElse(false);

    }
    public boolean hasValidRefreshToken(HttpServletRequest request){
        return getTokenFromCookie(request,"refreshToken")
                .map(this::isValidRefreshToken)
                .orElse(false);
    }
    public Optional<String> extractEmail(String token) {
        try {
            Claims claims = parseClaims(token);
            return Optional.ofNullable(claims.getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }



}
