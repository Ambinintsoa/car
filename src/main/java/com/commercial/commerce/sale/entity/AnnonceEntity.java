package com.commercial.commerce.sale.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.commercial.commerce.sale.utils.Caracteristic;
import com.commercial.commerce.sale.utils.Maintenance;
import com.commercial.commerce.sale.utils.Vendeur;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "annonce")
public class AnnonceEntity {

    @Id
    private String id;
    private MakeEntity brand;
    private ModelEntity modele;
    private List<Caracteristic> caracteristic;
    private double prix;
    private String year;
    private CouleurEntity couleur;
    private double kilometre;

    private double consommation;

    private List<MaintainEntity> maintenance;

    private CountryEntity localisation;

    private Vendeur vendeur;

    private int commission;

    private List<String> pictures;

    private MotorEntity motor;

    private double etat;

    private int state;

    private List<Long> favoris;

    private LocalDateTime date;
    @TextIndexed
    private String description;
}
