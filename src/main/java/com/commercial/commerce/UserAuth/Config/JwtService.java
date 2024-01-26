package com.commercial.commerce.UserAuth.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.commercial.commerce.UserAuth.Models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "VxzlNlj05JJeRjOBTHIYh8c1hBIw8FMIc8NkXkVnBUxv0RZL0TuSNx";

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();

    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        claims.put("roles", roles);
        return generateToken(claims, userDetails);
    }

    public String generateTokenAdmin(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        claims.put("roles", roles);
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {

        return Jwts.builder().claims(claims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 15)))// 900min le entre //
                                                                                              // parenthèse
                // io
                // le min
                .signWith(
                        getSigninKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private SecretKey getSigninKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

}
