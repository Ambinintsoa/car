package com.commercial.commerce.sale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "couleur", schema = "public")
@RequiredArgsConstructor
public class CouleurEntity {

    @Id
    @Column(name = "idcouleur")
    private String id;

    @Basic
    @Column(name = "name")
    private String nom;

    @Basic
    @Column(name = "state")
    private Integer state;

}
