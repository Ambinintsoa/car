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

    @Query(value = "SELECT * FROM country ORDER BY CAST(SUBSTRING(idcountry FROM 4) AS INTEGER) LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<CountryEntity> selectWithPagination(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT count(*)/:offset as count from country", nativeQuery = true)
    double pagination(@Param("offset") int offset);
}