package com.commercial.commerce.sale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "make", schema = "public")
@RequiredArgsConstructor
public class MakeEntity {

    @Id
    @Column(name = "idmake")
    private String id;

    @Basic
    @Column(name = "name")
    private String nom;

    @Basic
    @Column(name = "state")
    private Integer state;
}
