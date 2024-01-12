package com.commercial.commerce.UserAuth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.commerce.UserAuth.Config.JwtService;
import com.commercial.commerce.UserAuth.Response.CheckStatusResponse;

@Service
public class CheckStatusService {

    @Autowired
    public JwtService jwtService;
    @Autowired
    public RefreshTokenService refreshTokenService;

    public CheckStatusResponse checkAll(CheckStatusResponse request) {
        Boolean token_expired = false;
        try {
            token_expired = !jwtService.isTokenExpired(request.getToken());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Boolean refreshTokenExpired = refreshTokenService.isRefreshTokenValid(request.getRefresh_token());
        CheckStatusResponse check = CheckStatusResponse.builder().token(request.getToken()).token_valid(token_expired)
                .refresh_token(request.getRefresh_token()).refresh_token_valid(refreshTokenExpired).build();
        return check;
    }

}
