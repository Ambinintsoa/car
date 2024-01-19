package com.commercial.commerce.sale.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.commercial.commerce.sale.entity.AnnonceEntity;

public interface AnnonceRepository extends MongoRepository<AnnonceEntity, String> {
    List<AnnonceEntity> findByFavoris(String user);
}
