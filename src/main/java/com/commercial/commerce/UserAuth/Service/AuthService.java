package com.commercial.commerce.UserAuth.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.commercial.commerce.chat.model.JsonResponse;
import com.commercial.commerce.chat.service.FileHelper;
import com.commercial.commerce.sale.entity.CountryEntity;
import com.commercial.commerce.sale.service.CountryService;

import jakarta.el.ELException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository repository;
        private final CountryService countryService;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final RefreshTokenService refreshTokenService;

        public AuthenticationResponse register(RegisterRequest request) throws Exception {
                if (repository.findByEmail(request.getEmail()).isEmpty()) {
                        String profile = null;
                        User user = User.builder().name(request.getName())
                                        .country(new CountryEntity(request.getIdcountry()))
                                        .gender(request.getGender())
                                        .dtn(request.getDtn())
                                        .profile(profile)
                                        .email(request.getEmail())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .roles(Role.USER) // role example
                                        .build();
                        if (user.getGender() < 0 && user.getGender() > 1) {
                                throw new Exception("Gender is not valid");
                        }
                        Date now = new Date(System.currentTimeMillis());
                        LocalDate localDate1 = now.toLocalDate();
                        LocalDate localDate2 = user.getDtn().toLocalDate();
                        Period period = Period.between(localDate2, localDate1);
                        System.out.println(period.getYears());
                        if (period.getYears() < 18) {
                                throw new Exception("Age is not valid");
                        }
                        if (request.getProfile() != null) {
                                FileHelper file = new FileHelper();
                                JsonResponse json = file.uploadOnline(request.getProfile());
                                profile = json.getData().getUrl();
                                user.setProfile(profile);
                        }

                        user = repository.save(user);
                        user.setCountry(countryService.getCountryById(request.getIdcountry()).get());

                        return getAuthResponse(user);
                }
                throw new Exception("email is already present");

        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = repository.findByEmail(request.getEmail()).orElseThrow();

                return getAuthResponse(user);
        }

        public AuthenticationResponse authenticateAdmin(AuthenticationRequest request) throws Exception {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = repository.findByEmail(request.getEmail()).orElseThrow();
                if (user.getRoles() == Role.ADMIN) {
                        return getAuthResponseAdmin(user);
                } else {
                        throw new ELException("Cannot access");
                }

        }

        public AuthenticationResponse getAuthResponse(User user) {

                String jwtToken = jwtService.generateToken(user);
                RefreshToken tokenRefresh = refreshTokenService.generateRefreshToken(user.getEmail());
                return AuthenticationResponse.builder().token(jwtToken).user(user)
                                .refresh_token(tokenRefresh.getToken())
                                .build();
        }

        public AuthenticationResponse getAuthResponseAdmin(User user) {
                String jwtToken = jwtService.generateTokenAdmin(user);
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

        public Status useRefreshTokenAdmin(RefreshTokenRequest refreshTokenRequest) {

                User user = refreshTokenService.findByToken(refreshTokenRequest.getRefresh_token()).map(
                                refreshTokenService::verifyExpiration).map(RefreshToken::getUser).get();

                String accesToken = jwtService.generateTokenAdmin(user);
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

        public Optional<User> findById(Long id) {

                return repository.findById(id);
        }

        public Optional<User> recharge(Long id, double sum) throws Exception {
                if (sum > 0) {
                        User user = repository.findById(id).get();
                        if (user != null) {
                                if (user.getCompte() == null) {
                                        user.setCompte(0.0);
                                }
                                user.setCompte(user.getCompte() + sum);
                                ;
                                return Optional.of(repository.save(user));
                        } else {
                                return Optional.empty();
                        }
                }
                throw new Exception("somme non-valid");

        }
}