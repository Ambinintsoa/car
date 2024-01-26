package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.CategoryEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    @Query(value = "INSERT INTO category (name) VALUES (:name) RETURNING idcategory", nativeQuery = true)
    String insertCustom(@Param("name") String name);

    @Query(value = "SELECT * FROM category WHERE state = 1", nativeQuery = true)
    List<CategoryEntity> findAllActive();

    @Query(value = "select * from category where idcategory = :id and state =1", nativeQuery = true)
    Optional<CategoryEntity> findByIdActive(@Param("id") String id);

}