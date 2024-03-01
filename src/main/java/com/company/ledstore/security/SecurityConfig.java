package com.company.ledstore.security;

import com.company.ledstore.data.entity.User;
import com.company.ledstore.data.repository.UserRepository;
import com.company.ledstore.service.dto.UserDTO;
import com.company.ledstore.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserServiceImpl userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserDTO userDTO = userService.findByUsername(username);
            if (userDTO != null) {
                User user = new User();
                user.setId(userDTO.getId());
                user.setUsername(userDTO.getUsername());
                user.setPassword(userDTO.getPassword());
                user.setFullName(userDTO.getFullName());
                user.setStreet(userDTO.getStreet());
                user.setCity(userDTO.getCity());
                user.setState(userDTO.getState());
                user.setZip(userDTO.getZip());
                user.setPhoneNumber(userDTO.getPhoneNumber());
                return user;
            }
            throw new UsernameNotFoundException(
                    "User '" + username + "' not found");
        };
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/design", "/orders").hasRole("USER")
                    .anyRequest().permitAll())
            .formLogin(formLogin -> formLogin
                    .loginPage("/login"))
            .logout(logout -> logout
                    .logoutSuccessUrl("/"));
        return http.build();
    }

}


