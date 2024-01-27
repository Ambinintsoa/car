package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.CouleurEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouleurRepository extends JpaRepository<CouleurEntity, String> {
    @Query(value = "INSERT INTO couleur (name) VALUES (:name) RETURNING idcouleur", nativeQuery = true)
    String insertCustom(@Param("name") String name);

    @Query(value = "SELECT * FROM couleur WHERE state = 1", nativeQuery = true)
    List<CouleurEntity> findAllActive();

    @Query(value = "select * from couleur where idcouleur = :id and state = 1", nativeQuery = true)
    Optional<CouleurEntity> findByIdActive(@Param("id") String id);
}
