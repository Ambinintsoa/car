package com.commercial.commerce.sale.entity;

import java.sql.Date;

import com.commercial.commerce.UserAuth.Models.User;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "purchase", schema = "public")
@RequiredArgsConstructor
public class PurchaseEntity {
    @Id
    @Column(name = "idpurchase")
    private String id;

    @Column(name = "idannouncement")
    private String announcement;

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    private User user;

    @Basic
    @Column(name = "state")
    private Integer state;

    @Basic
    @Column(name = "date")
    private Date date;

    @Basic
    @Column(name = "montant")
    private float montant;
}
