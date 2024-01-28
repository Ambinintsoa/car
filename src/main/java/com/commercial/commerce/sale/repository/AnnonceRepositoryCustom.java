package com.commercial.commerce.sale.repository;

import java.util.List;

import com.commercial.commerce.sale.utils.Statistique;

public interface AnnonceRepositoryCustom {
    List<Statistique> countAllByModele();

    List<Statistique> countAllByType();
}
