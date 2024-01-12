package com.commercial.commerce.UserAuth.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckStatusResponse {

    String token;
    Boolean token_valid;
    String refresh_token;
    Boolean refresh_token_valid;

}
