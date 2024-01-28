package com.commercial.commerce.sale.repository;

import java.util.List;

import com.commercial.commerce.sale.utils.Statistique;

public interface AnnonceRepositoryCustom {
    List<Statistique> countAllByModele();

    List<Statistique> countAllByBrand();

    List<Statistique> countAllByType();

    List<Statistique> getBestVenteModel();

    List<Statistique> getBestVenteBrand();

    List<Statistique> getBestVenteType();

    public double sumOfCommissions();
}
