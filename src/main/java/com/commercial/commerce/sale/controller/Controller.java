package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.commercial.commerce.UserAuth.Config.JwtService;
import com.commercial.commerce.UserAuth.Models.User;
import com.commercial.commerce.UserAuth.Service.AuthService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;

public class Controller {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;

    public boolean isTokenValid(String token, Long id) {
        String username = jwtService.extractUsername(token);
        Optional<User> user = authService.findById(id);
        if (user.isPresent()) {
            return (username.equals(user.get().getEmail()) && !jwtService.isTokenExpired(token));
        }
        return false;

    }

    public boolean isTokenValidAchat(String token, Long id) {
        String username = jwtService.extractUsername(token);
        Optional<User> user = authService.findById(id);
        if (user.isPresent() && !jwtService.isTokenExpired(token)) {
            System.out.println(username);
            System.out.print(user.get().getEmail());
            System.out.println((username.equals(user.get().getEmail())));
            return (username.equals(user.get().getEmail()));
        }
        return true;

    }

    public <T> ResponseEntity<ApiResponse<T>> createResponseEntity(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

}
