package com.commercial.commerce.sale.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "maintains", schema = "public")
@RequiredArgsConstructor
public class MaintainEntity {

    @Id
    @Column(name = "idmaintains")
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

    public MaintainEntity(String id, String nom, int state) {
        this.setId(id);
        this.setNom(nom);
        this.setState(state);

    }

    public static List<MaintainEntity> removeDuplicates(List<MaintainEntity> maintains) {
        HashSet<MaintainEntity> uniqueSet = new HashSet<>();
        List<MaintainEntity> uniqueMaintains = new ArrayList<>();

        for (MaintainEntity maintain : maintains) {
            String id = maintain.getId();
            String nom = maintain.getNom();
            int state = maintain.getState();
            String key = id + nom + state;
            if (uniqueSet.add(new MaintainEntity(id, nom, state))) {
                uniqueMaintains.add(maintain);
            }
        }

        return uniqueMaintains;
    }

}
