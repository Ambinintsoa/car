package com.commercial.commerce.sale.repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.commercial.commerce.sale.entity.AnnonceEntity;

public interface AnnonceRepository extends MongoRepository<AnnonceEntity, String> {
    List<AnnonceEntity> findByFavoris(Long user);

    List<AnnonceEntity> findByVendeur(Long idvendeur);

    List<AnnonceEntity> findByPrixLessThanEqualAndPrixGreaterThanEqual(double sup, double inf);

    List<AnnonceEntity> findByPrixGreaterThanEqual(double inf);

    List<AnnonceEntity> findByPrixLessThanEqual(double sup);

    List<AnnonceEntity> findByBrandIn(String[] brand);

    List<AnnonceEntity> findByModeleIn(String[] modele);

    List<AnnonceEntity> findByDateBetween(LocalDateTime dateInf, LocalDateTime dateSup);

    List<AnnonceEntity> findByDateGreaterThanEqual(LocalDateTime dateInf);

    List<AnnonceEntity> findByDateLessThanEqual(LocalDateTime dateSup);
}
