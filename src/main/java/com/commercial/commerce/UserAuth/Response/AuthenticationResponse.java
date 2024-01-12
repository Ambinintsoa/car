package com.commercial.commerce.UserAuth.Response;

import com.commercial.commerce.UserAuth.Models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;
    private User user;
    private String refresh_token;
    private Object details_user_;
}
