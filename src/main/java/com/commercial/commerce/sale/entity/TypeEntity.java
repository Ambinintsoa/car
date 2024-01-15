package com.commercial.commerce.sale.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "type", schema = "public")
@RequiredArgsConstructor
public class TypeEntity {

    @Id
    @Column(name = "idtype")
    private String id;

    @Basic
    @Column(name = "name")
    private String nom;

    @Basic
    @Column(name = "state")
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
