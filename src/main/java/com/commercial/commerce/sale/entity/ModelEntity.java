package com.commercial.commerce.sale.entity;

import java.sql.Date;

import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Mod;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
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
    private int state;

    @Basic
    @Column(name = "release_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "idmake", referencedColumnName = "idmake")
    private MakeEntity make;

    @ManyToOne
    @JoinColumn(name = "idtype", referencedColumnName = "idtype")
    private TypeEntity type;

    public MakeEntity getMake() {
        return make;
    }

    public void setMake(MakeEntity make) {
        this.make = make;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    public void setPlaces(int places) {
        this.places = places;
    }

    public int getPlaces() {
        return this.places;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getDoors() {
        return this.doors;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ModelEntity(String name, Date date, int doors, int places, String type, String make) {
        this.setNom(name);
        this.setDate(date);
        this.setDoors(doors);
        this.setPlaces(places);
        this.setType(new TypeEntity());
        this.getType().setId(type);
        this.setMake(new MakeEntity());
        this.getMake().setId(make);
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
        if (this.getMake() == null) {
            this.setMake(update.getMake());
        }

    }

}
