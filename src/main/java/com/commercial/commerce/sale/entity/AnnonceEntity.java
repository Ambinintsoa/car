package com.commercial.commerce.sale.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.commercial.commerce.sale.utils.Caracteristic;
import com.commercial.commerce.sale.utils.Maintenance;
import com.commercial.commerce.sale.utils.Model;
import com.commercial.commerce.sale.utils.Brand;
import com.commercial.commerce.sale.utils.Motor;
import com.commercial.commerce.sale.utils.Type;
import com.commercial.commerce.sale.utils.Vendeur;

import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "annonce")
public class AnnonceEntity {

    @Id
    private String id;

    private Brand brand;
    private Model modele;

    private List<Caracteristic> caracteristic;

    private String year;

    private double kilometre;

    private double consommation;

    private List<Maintenance> maintenance;

    private String localisation;

    private int stock;

    private Vendeur vendeur;

    private int commission;

    private List<String> pictures;

    private Motor motor;

    private double note;

    private int state;

    private List<Long> favoris;

    private LocalDateTime date;

    private String description;
    private Type type;
}
