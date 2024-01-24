package com.commercial.commerce.sale.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.entity.PurchaseEntity;

import jakarta.transaction.Transactional;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, String> {

    @Query(value = "INSERT INTO purchase (idannouncement,iduser,state,montant) VALUES (:annonce ,:user , 1,:montant) RETURNING idpurchase", nativeQuery = true)
    String insertCustom(@Param("annonce") String annonce, @Param("user") Long user, @Param("montant") float montant);

    @Query(value = "SELECT * FROM purchase WHERE state = 1", nativeQuery = true)
    List<PurchaseEntity> findAllActive();

    @Query(value = "SELECT * FROM purchase WHERE state = 2 and iduser =:user ORDER BY CAST(SUBSTRING(idpurchase FROM 4) AS INTEGER) LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<PurchaseEntity> findAllActiveValid(@Param("user") Long user, @Param("limit") int limit,
            @Param("offset") int offset);

    @Query(value = "SELECT * FROM purchase WHERE state = 1 and idannouncement in :annonce ORDER BY CAST(SUBSTRING(idpurchase FROM 4) AS INTEGER) LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<PurchaseEntity> findAllValid(@Param("annonce") String[] annonce, @Param("limit") int limit,
            @Param("offset") int offset);

    @Query(value = "SELECT * FROM purchase ORDER BY CAST(SUBSTRING(idpurchase FROM 4) AS INTEGER) LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<PurchaseEntity> selectWithPagination(@Param("limit") int limit, @Param("offset") int offset);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE purchase set state = :state where idpurchase =:purchase", nativeQuery = true)
    void updatePurchaseState(@Param("purchase") String prurchase, @Param("state") int state);

}
