package com.commercial.commerce.sale.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.commercial.commerce.sale.entity.AnnonceEntity;

public interface AnnonceRepository extends MongoRepository<AnnonceEntity, String> {

    List<AnnonceEntity> findByFavoris(Long user);

    List<AnnonceEntity> findByVendeurIdvendeur(Long idvendeur);

    List<AnnonceEntity> findByModeleTypeId(String idtype);

    List<AnnonceEntity> findByPrixBetween(double prixMin, double prixMax);

    List<AnnonceEntity> findByEtatBetween(double min, double max);

    List<AnnonceEntity> findByPrixGreaterThanEqual(double inf);

    List<AnnonceEntity> findByPrixLessThanEqual(double sup);

    List<AnnonceEntity> findByEtatGreaterThanEqual(double inf);

    List<AnnonceEntity> findByEtatLessThanEqual(double sup);

    List<AnnonceEntity> findAllByBrand_IdIn(String[] brand);

    List<AnnonceEntity> findAllByModele_Type_IdIn(String[] brand);

    List<AnnonceEntity> findAllByCouleur_IdIn(String[] couleur);

    List<AnnonceEntity> findAllByModele_IdIn(String[] modele);

    List<AnnonceEntity> findByDateBetween(LocalDateTime dateInf, LocalDateTime dateSup);

    List<AnnonceEntity> findByDateGreaterThanEqual(LocalDateTime dateInf);

    List<AnnonceEntity> findAllByState(int state);

    List<AnnonceEntity> findByDateLessThanEqual(LocalDateTime dateSup);

    List<AnnonceEntity> findAllByVendeur_IdvendeurAndState(Long idVendeur, int state);

    Page<AnnonceEntity> findAllByVendeur_IdvendeurAndState(Long idVendeur, int state, Pageable pageable);

    @Query("{ 'status' : 2, 'modele.id' : ?0 }")
    List<AnnonceEntity> findSoldCarsByModelId(String modelId);

    @Query("{'status': 2}")
    List<AnnonceEntity> findSoldCars();

    @Query("{'status': 2, 'modele.id': ?0}")
    Long countSoldCarsByModelId(String modelId);

}
