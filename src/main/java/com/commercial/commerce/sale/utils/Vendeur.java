package com.commercial.commerce.sale.utils;

import com.commercial.commerce.UserAuth.Models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vendeur {
    Long idvendeur;
    int proprietaire;
    String nom;
    String profile;
}
