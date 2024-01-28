package com.commercial.commerce.sale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "country", schema = "public")
@RequiredArgsConstructor
public class CountryEntity {

    @Id
    @Column(name = "idcountry")
    private String id;

    @Basic
    @Column(name = "name")
    private String nom;

    @Basic
    @Column(name = "state")
    private Integer state;

    public CountryEntity(String id) {
        this.setId(id);
    }

}
