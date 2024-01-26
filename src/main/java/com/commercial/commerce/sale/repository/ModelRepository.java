package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.ModelEntity;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, String> {
        @Query(value = "INSERT INTO model (name, release_date, doors_number,places_number,idtype,idmake) VALUES (:name, :release_date, :doors_number, :places_number, :idtype, :idmake) RETURNING idmodel", nativeQuery = true)
        String insertCustom(
                        @Param("name") String name,
                        @Param("release_date") Date date,
                        @Param("doors_number") int doors,
                        @Param("places_number") int places,
                        @Param("idtype") String type,
                        @Param("idmake") String make);

        @Query(value = "select * from model where idmodel = :id and state =1", nativeQuery = true)
        Optional<ModelEntity> findByIdActive(@Param("id") String id);

        @Query(value = "select * from model where idmake = :id and state =1", nativeQuery = true)
        List<ModelEntity> findByMake(@Param("id") String id);

}