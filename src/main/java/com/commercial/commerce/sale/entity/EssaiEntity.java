package com.commercial.commerce.sale.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "essai", schema = "public")
public class EssaiEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

        @Basic
    @Column(name = "nom")
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
