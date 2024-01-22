package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.MotorEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorRepository extends JpaRepository<MotorEntity, String> {
    @Query(value = "INSERT INTO motor (name) VALUES (:name) RETURNING idmotor", nativeQuery = true)
    String insertCustom(@Param("name") String name);

    @Query(value = "SELECT * FROM motor WHERE state = 1", nativeQuery = true)
    List<MotorEntity> findAllActive();

    @Query(value = "select * from motor where idmotor = :id and state =1", nativeQuery = true)
    Optional<MotorEntity> findByIdActive(@Param("id") String id);

    @Query(value = "SELECT * FROM motor WHERE CAST(SUBSTRING(idmotor FROM 4) AS INTEGER) >= CAST(SUBSTRING(:id FROM 4) AS INTEGER) ORDER BY CAST(SUBSTRING(idmotor FROM 4) AS INTEGER) LIMIT :limit", nativeQuery = true)
    List<MotorEntity> selectWithPagination(@Param("id") String id, @Param("limit") int limit);
}