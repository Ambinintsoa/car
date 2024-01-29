package com.commercial.commerce.sale.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.commercial.commerce.sale.entity.PurchaseEntity;
import com.commercial.commerce.sale.utils.Statistique;

import jakarta.transaction.Transactional;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, String> {

        @Query(value = "INSERT INTO purchase (idannouncement,iduser,state,montant) VALUES (:annonce ,:user , 1,:montant) RETURNING idpurchase", nativeQuery = true)
        String insertCustom(@Param("annonce") String annonce, @Param("user") Long user,
                        @Param("montant") float montant);

        @Query(value = "SELECT * FROM purchase WHERE state = 1", nativeQuery = true)
        List<PurchaseEntity> findAllActive();

        @Query(value = "WITH all_months AS (SELECT generate_series(1, 12) AS month)" +
                        "SELECT TO_CHAR(TO_DATE(all_months.month\\:\\:text, 'MM'), 'Month') AS label, " +
                        "COALESCE(COUNT(purchase.date), 0) AS count " +
                        "FROM all_months " +
                        "LEFT JOIN purchase ON EXTRACT(MONTH FROM purchase.date) = all_months.month " +
                        "GROUP BY all_months.month " +
                        "ORDER BY all_months.month", nativeQuery = true)
        List<Object[]> getStatsPerMonth();

        @Query(value = "SELECT * FROM purchase WHERE state = 1 AND iduser =:user", nativeQuery = true)
        Page<PurchaseEntity> findAllSent(@Param("user") Long user, Pageable pageable);

        @Query(value = "SELECT * FROM purchase WHERE state = 2 and iduser = :user ORDER BY CAST(SUBSTRING(idpurchase FROM 4) AS INTEGER)", countQuery = "SELECT count(*) FROM purchase WHERE state = 2 and iduser = :user", nativeQuery = true)
        Page<PurchaseEntity> findAllActiveValid(@Param("user") Long user, Pageable pageable);

        @Query(value = "SELECT * FROM purchase WHERE state = 1 and idannouncement in :annonce ORDER BY CAST(SUBSTRING(idpurchase FROM 4) AS INTEGER)", countQuery = "SELECT count(*) FROM purchase WHERE state = 1 and idannouncement in :annonce", nativeQuery = true)
        Page<PurchaseEntity> findAllValid(@Param("annonce") String[] annonce, Pageable pageable);

        @Query(value = "SELECT * FROM purchase ORDER BY CAST(SUBSTRING(idpurchase FROM 4) AS INTEGER)", countQuery = "SELECT count(*) FROM purchase", nativeQuery = true)
        Page<PurchaseEntity> selectWithPagination(Pageable pageable);

        @Modifying(clearAutomatically = true)
        @Transactional
        @Query(value = "UPDATE purchase set state = :state where idpurchase =:purchase", nativeQuery = true)
        void updatePurchaseState(@Param("purchase") String prurchase, @Param("state") int state);

}
