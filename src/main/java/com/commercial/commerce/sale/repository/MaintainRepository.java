package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.MaintainEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintainRepository extends JpaRepository<MaintainEntity, String> {
    @Query(value = "INSERT INTO maintains (name,state) VALUES (:name,1) RETURNING idmaintains", nativeQuery = true)
    String insertCustom(@Param("name") String name);

    @Query(value = "SELECT * FROM maintains WHERE state = 1", nativeQuery = true)
    List<MaintainEntity> findAllActive();

    @Query(value = "select * from maintains where idmaintains = :id and state =1", nativeQuery = true)
    Optional<MaintainEntity> findByIdActive(@Param("id") String id);

}