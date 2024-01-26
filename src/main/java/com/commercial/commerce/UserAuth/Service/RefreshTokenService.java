package com.commercial.commerce.UserAuth.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.commerce.UserAuth.Config.JwtService;
import com.commercial.commerce.UserAuth.Models.RefreshToken;
import com.commercial.commerce.UserAuth.Repository.RefreshTokenRepository;
import com.commercial.commerce.UserAuth.Repository.UserRepository;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    public RefreshToken generateRefreshToken(String userEmail) {
        deleteActiveRefreshToken(userEmail);
        // System.out.println("Time = " + Instant.now().plusMillis(60000 * 60 * 0));
        RefreshToken token = RefreshToken.builder().user(userRepository.findByEmail(userEmail).get())
                .token(UUID.randomUUID().toString()).expireDate(
                        Instant.now().plusSeconds(1296000))// add 3h for EAT //10h ony
                .build();

        return refreshTokenRepository.save(token);
    }

    public void deleteActiveRefreshToken(String userEmail) {
        refreshTokenRepository.deleteByEmail(userEmail);
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    public void removeRefreshToken(String refreshToken) {
        RefreshToken token = null;
        try {
            token = findByToken(refreshToken).get();
            deleteActiveRefreshToken(token.getUser().getEmail());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpireDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Expired refresh token. Please sign in again.");
        }
        return refreshToken;
    }

    public Boolean isRefreshTokenValid(String refreshToken) {
        RefreshToken token = null;
        try {

            token = findByToken(refreshToken).get();

        } catch (Exception e) {
            return false;
        }
        return !token.getExpireDate().isBefore(Instant.now());
    }

    public String splitToken(String header) {
        String[] tokens = header.split("Bearer ");
        if (tokens.length == 1) {
            return "";
        }
        return tokens[1];

    }
}
