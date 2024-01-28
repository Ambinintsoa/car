package com.commercial.commerce.sale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class CategoryEntity {

    @Id
    @Column(name = "idcategory")
    private String id;

    @Basic
    @Column(name = "name")
    private String nom;

    @Basic
    @Column(name = "state")
    private Integer state;

}
