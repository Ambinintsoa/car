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
@Table(name = "transaction", schema = "public")
@RequiredArgsConstructor
public class TransactionEntity {
    @Id
    @Column(name = "idtransaction")
    private String id;

    @ManyToOne
    @JoinColumn(name = "idpurchase", referencedColumnName = "idpurchase")
    private PurchaseEntity purchase;

    @Basic
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "idreceiver", referencedColumnName = "id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "idsender", referencedColumnName = "id")
    private User sender;

}
