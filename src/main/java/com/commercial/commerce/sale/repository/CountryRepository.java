package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.CountryEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, String> {
    @Query(value = "INSERT INTO country (name) VALUES (:name) RETURNING idcountry", nativeQuery = true)
    String insertCustom(@Param("name") String name);

    @Query(value = "SELECT * FROM country WHERE state = 1", nativeQuery = true)
    List<CountryEntity> findAllActive();

    @Query(value = "select * from country where idcountry = :id and state =1", nativeQuery = true)
    Optional<CountryEntity> findByIdActive(@Param("id") String id);

}