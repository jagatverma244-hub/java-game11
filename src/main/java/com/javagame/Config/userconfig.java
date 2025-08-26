package com.javagame.Config;
import com.javagame.Entity.User;
import com.javagame.Repo.UserRepo;
import org.apache.catalina.Manager;
import org.apache.catalina.session.StandardManager;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity

public class userconfig {


private final UserRepo userRepo;
    public static final String[] PUBLIC_URLS = {

            "/home","/user/login", "/user/register", "/user/deshboard",
            "/v3/api-docs/**", "/quizadmin","/swagger-ui/**", "/swagger-ui.html",
            "/swagger-resources/**", "/webjars/**","/profile/**","/user/logout"
    };

    public userconfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity https, JwtFilter jwtFilter)throws Exception{
        https.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth ->
                        auth.requestMatchers(PUBLIC_URLS).permitAll()




                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasRole("USER").anyRequest().permitAll()
                ).logout(logout -> logout.logoutSuccessUrl("/user/login?logout").permitAll())

                .formLogin(AbstractHttpConfigurer::disable) // Disable form login
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/user/login?logout"))

                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/user/login?alreadyLoggedIn=true")
                );

        https.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return https.build();



    }
    



        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User log = userRepo.findByUEmail(username);
            return new org.springframework.security.core.userdetails.User(log.getUEmail(), log.getUPassword(), Collections.singletonList(new SimpleGrantedAuthority(log.getRole())));
        };


    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TomcatContextCustomizer tomcatContextCustomizer() {
        return context -> {
            Manager manager = new StandardManager();
            manager.setContext(null); // disables session persistence to file
            context.setManager(manager);
        };
    }


}




