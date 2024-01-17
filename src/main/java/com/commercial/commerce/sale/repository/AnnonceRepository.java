package com.commercial.commerce.sale.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.commercial.commerce.sale.entity.AnnonceEntity;

public interface AnnonceRepository extends MongoRepository<AnnonceEntity, String> {

}
