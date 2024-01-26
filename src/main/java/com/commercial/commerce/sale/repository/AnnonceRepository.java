package com.commercial.commerce.sale.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.commercial.commerce.sale.entity.AnnonceEntity;

public interface AnnonceRepository extends MongoRepository<AnnonceEntity, String> {
    List<AnnonceEntity> findByFavoris(Long user);

    List<AnnonceEntity> findByVendeurIdvendeur(Long idvendeur);

    List<AnnonceEntity> findByModeleTypeId(String idtype);

    List<AnnonceEntity> findByPrixLessThanEqualAndPrixGreaterThanEqual(double sup, double inf);

    List<AnnonceEntity> findByPrixGreaterThanEqual(double inf);

    List<AnnonceEntity> findByPrixLessThanEqual(double sup);

    List<AnnonceEntity> findByBrandIn(String[] brand);

    List<AnnonceEntity> findByModeleIn(String[] modele);

    List<AnnonceEntity> findByDateBetween(LocalDateTime dateInf, LocalDateTime dateSup);

    List<AnnonceEntity> findByDateGreaterThanEqual(LocalDateTime dateInf);

    List<AnnonceEntity> findByDateLessThanEqual(LocalDateTime dateSup);
}
