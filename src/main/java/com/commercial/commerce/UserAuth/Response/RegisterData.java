package com.commercial.commerce.UserAuth.Response;

import com.commercial.commerce.UserAuth.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterData {

    Role[] roles;
    // List<Service> services;
    // List<Supplier> supplier;

}
