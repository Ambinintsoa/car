package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.TypeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, String> {
    @Query(value = "INSERT INTO type (name) VALUES (:name) RETURNING idtype", nativeQuery = true)
    String insertCustom(@Param("name") String name);

    @Query(value = "SELECT * FROM type WHERE state = 1", nativeQuery = true)
    List<TypeEntity> findAllActive();

    @Query(value = "select * from type where idtype = :id and state =1", nativeQuery = true)
    Optional<TypeEntity> findByIdActive(@Param("id") String id);

}