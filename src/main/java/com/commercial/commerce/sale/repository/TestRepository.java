package com.commercial.commerce.sale.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.commercial.commerce.sale.entity.TestEntity;

public interface TestRepository extends MongoRepository<TestEntity, String> {

}
