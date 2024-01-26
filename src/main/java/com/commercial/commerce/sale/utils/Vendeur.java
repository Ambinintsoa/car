package com.commercial.commerce.sale.utils;

import com.commercial.commerce.UserAuth.Models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vendeur extends User {
    Long idvendeur;
    int proprietaire;
}
