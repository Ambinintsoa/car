package com.commercial.commerce.sale.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.commercial.commerce.sale.entity.CouleurEntity;
import com.commercial.commerce.sale.entity.MakeEntity;
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.entity.TypeEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameter {
    Double supMontant;
    Double infMontant;
    Double supEtat;
    Double infEtat;
    List<MakeEntity> brands;
    List<ModelEntity> modeles;
    List<TypeEntity> types;
    List<CouleurEntity> couleurs;
    LocalDateTime dateInf;
    LocalDateTime dateSup;
    String description;
}
