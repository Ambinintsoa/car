package com.commercial.commerce.sale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.entity.ModelEntity;

public interface AnnonceRepository extends MongoRepository<AnnonceEntity, String> {
    List<AnnonceEntity> findByFavoris(Long user);

    List<AnnonceEntity> findByVendeurIdvendeur(Long idvendeur);

}
