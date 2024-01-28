package com.commercial.commerce.sale.entity;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "model", schema = "public")
@RequiredArgsConstructor
public class ModelEntity {
    @Id
    @Column(name = "idmodel")
    private String id;

    @Basic
    @Column(name = "name")
    private String nom;

    @Basic
    @Column(name = "places_number")
    private int places;

    @Basic
    @Column(name = "doors_number")
    private int doors;

    @Basic
    @Column(name = "state")
    private Integer state;

    @Basic
    @Column(name = "release_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "idmake", referencedColumnName = "idmake")
    private MakeEntity brand;

    @ManyToOne
    @JoinColumn(name = "idtype", referencedColumnName = "idtype")
    private TypeEntity type;

    public ModelEntity(String name, LocalDate date, int doors, int places, String type, String make) {
        this.setNom(name);
        this.setDate(date);
        this.setDoors(doors);
        this.setPlaces(places);
        this.setType(new TypeEntity());
        this.getType().setId(type);
        this.setBrand(new MakeEntity());
        this.getBrand().setId(make);

    }

    public void verify(ModelEntity update) {
        if (this.getNom() == null) {
            this.setNom(update.getNom());
        }
        if (this.getDate() == null) {
            this.setDate(update.getDate());
        }
        if (this.getDoors() == 0) {
            this.setDoors(update.getDoors());
        }
        if (this.getPlaces() == 0) {
            this.setPlaces(update.getPlaces());
        }
        if (this.getType() == null) {
            this.setType(update.getType());
        }
        if (this.getBrand() == null) {
            this.setBrand(update.getBrand());
        }
        if (this.getState() == null) {
            this.setState(update.getState());
        }

    }

}
