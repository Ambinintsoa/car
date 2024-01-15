package com.commercial.commerce.UserAuth.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.commercial.commerce.UserAuth.Config.JwtService;
import com.commercial.commerce.UserAuth.Enum.Role;
import com.commercial.commerce.UserAuth.Models.RefreshToken;
import com.commercial.commerce.UserAuth.Models.User;
import com.commercial.commerce.UserAuth.Repository.UserRepository;
import com.commercial.commerce.UserAuth.Request.AuthenticationRequest;
import com.commercial.commerce.UserAuth.Request.RefreshTokenRequest;
import com.commercial.commerce.UserAuth.Request.RegisterRequest;
import com.commercial.commerce.UserAuth.Response.AuthenticationResponse;
import com.commercial.commerce.Utils.Status;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final RefreshTokenService refreshTokenService;

        public AuthenticationResponse register(RegisterRequest request) {
                User user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
                                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                                .roles(Role.USER) // role example
                                .build();
                user = repository.save(user);
                return getAuthResponse(user);
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = repository.findByEmail(request.getEmail()).orElseThrow();
                return getAuthResponse(user);
        }

        public AuthenticationResponse getAuthResponse(User user) {
                String jwtToken = jwtService.generateToken(user);
                RefreshToken tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());
                return AuthenticationResponse.builder().token(jwtToken).user(user)
                                .refresh_token(tokenRefresh.getToken())
                                .build();
        }

        public Status useRefreshToken(RefreshTokenRequest refreshTokenRequest) {
                User user = refreshTokenService.findByToken(refreshTokenRequest.getRefresh_token()).map(
                                refreshTokenService::verifyExpiration).map(RefreshToken::getUser).get();
                String accesToken = jwtService.generateToken(user);
                var tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());

                return Status.builder()
                                .data(AuthenticationResponse.builder().token(accesToken).user(user)
                                                .refresh_token(tokenRefresh.getToken())
                                                .build())
                                .status("good").details("token removed").build();
        }

        public Boolean chekcIfAlreadyExist(String email) {
                Boolean exist = false;
                Optional<User> user = repository.findByEmail(email);
                if (user.isPresent())
                        exist = true;

                return exist;
        }
}
