package com.commercial.commerce.sale.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import com.commercial.commerce.sale.entity.MakeEntity;
import com.commercial.commerce.sale.entity.ModelEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameter {
    Integer supMontant;
    Integer infMontant;
    List<MakeEntity> brands;
    List<ModelEntity> modeles;
    LocalDateTime dateInf;
    LocalDateTime dateSup;
}
