package com.commercial.commerce.sale.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.commercial.commerce.sale.utils.Caracteristic;
import com.commercial.commerce.sale.utils.Maintenance;
import com.commercial.commerce.sale.utils.Make;
import com.commercial.commerce.sale.utils.Motor;
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

    private Make make;

    private List<Caracteristic> caracteristic;

    private String year;

    private double kilometre;

    private double consommation;

    private List<Maintenance> maintenance;

    private String localisation;

    private int stock;

    private Vendeur vendeur;

    private int commission;

    private List<String> picture;

    private Motor motor;

    private double note;

    private int state;

    private List<String> favoris;
    private LocalDateTime date;

}
