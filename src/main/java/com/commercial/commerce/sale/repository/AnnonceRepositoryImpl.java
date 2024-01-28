package com.commercial.commerce.sale.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.commercial.commerce.sale.utils.Statistique;

import java.util.List;

@Repository
public class AnnonceRepositoryImpl implements AnnonceRepositoryCustom {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public AnnonceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Statistique> countAllByModele() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("state").is(2)),
                Aggregation.group("modele.nom").addToSet("modele.nom").as("label")
                        .count().as("count"),
                Aggregation.project("label", "count").andExclude("_id"));

        AggregationResults<Statistique> results = mongoTemplate.aggregate(aggregation, "annonce", Statistique.class);
        return results.getMappedResults();
    }

    @Override
    public List<Statistique> countAllByType() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("state").is(2)),
                Aggregation.group("modele.type.nom").addToSet("modele.type.nom").as("label")
                        .count().as("count"),
                Aggregation.project("label", "count").andExclude("_id"));

        AggregationResults<Statistique> results = mongoTemplate.aggregate(aggregation, "annonce", Statistique.class);
        return results.getMappedResults();
    }
}
