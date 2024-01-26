package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.MakeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeRepository extends JpaRepository<MakeEntity, String> {
    @Query(value = "INSERT INTO make (name) VALUES (:name) RETURNING idmake", nativeQuery = true)
    String insertCustom(@Param("name") String name);

    @Query(value = "SELECT * FROM make WHERE state = 1", nativeQuery = true)
    List<MakeEntity> findAllActive();

    @Query(value = "select * from make where idmake = :id and state =1", nativeQuery = true)
    Optional<MakeEntity> findByIdActive(@Param("id") String id);

}