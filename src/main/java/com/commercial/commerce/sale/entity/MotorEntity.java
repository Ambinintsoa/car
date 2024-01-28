package com.commercial.commerce.sale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "motor", schema = "public")
@RequiredArgsConstructor
public class MotorEntity {

    @Id
    @Column(name = "idmotor")
    private String id;

    @Basic
    @Column(name = "name")
    private String nom;

    @Basic
    @Column(name = "state")
    private Integer state;

}
