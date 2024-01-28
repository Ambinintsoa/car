package com.commercial.commerce.sale.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    private Integer state;

    public MaintainEntity(String id) {
        this.setId(id);

    }

    public static List<MaintainEntity> removeDuplicates(List<MaintainEntity> maintains) {
        HashSet<String> uniqueSet = new HashSet<>();
        List<MaintainEntity> uniqueMaintains = new ArrayList<>();

        for (MaintainEntity maintain : maintains) {
            String id = maintain.getId();
            if (uniqueSet.add(id)) {
                uniqueMaintains.add(maintain);
            }
        }

        return uniqueMaintains;
    }

}
