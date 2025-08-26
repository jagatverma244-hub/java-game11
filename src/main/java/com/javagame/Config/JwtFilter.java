package com.javagame.Config;

import com.javagame.Entity.User;
import com.javagame.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = null;
        String refreshToken = null;


        if(request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();

                }else if("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();

                }
            }
        }

        if (accessToken != null && jwtService.isValidAccessToken(accessToken)){
            setAuthenticationFromToken(accessToken);
        } else if (refreshToken != null && jwtService.isValidRefreshToken(refreshToken)) {
            Optional<User> optionalUser = jwtService.getUserfromToken(refreshToken);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user,null,List.of(new SimpleGrantedAuthority(user.getRole()))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                String newAccessToken = jwtService.generateAccessToken(user,1);
                Cookie newAccessCookie = new Cookie("accessToken", newAccessToken);
                newAccessCookie.setHttpOnly(true);
                newAccessCookie.setSecure(true);
                newAccessCookie.setPath("/");
                newAccessCookie.setMaxAge(360);
                response.addCookie(newAccessCookie);
            }else {
                SecurityContextHolder.clearContext();
                response.sendRedirect("/user/login?sessionExpired=true");
                return;
            }

        }else {
            SecurityContextHolder.clearContext();
            response.sendRedirect("/user/login?sessionExpired=true");
            return;
        }
        filterChain.doFilter(request, response);

    }

    private void setAuthenticationFromToken(String token){
        Optional<User> optionalUser = jwtService.getUserfromToken(token);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getRole() != null){
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user,null, List.of(new SimpleGrantedAuthority(user.getRole())));
                        SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/user/login") || path.equals("/user/register")||
        path.startsWith("/pforgot") ||
                path.startsWith("/User")||

                path.startsWith("/css") ||
                path.startsWith("/js") ||
                path.startsWith("/images") ;

    }
}

