package com.commercial.commerce.sale.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.commercial.commerce.sale.entity.TransactionEntity;

import jakarta.transaction.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    @Query(value = "INSERT INTO transaction (idpurchase,idreceiver,idsender) VALUES (:purchase ,:receiver,:sender ) RETURNING idtransaction", nativeQuery = true)
    String insertCustom(@Param("purchase") String purchase, @Param("receiver") Long receiver,
            @Param("sender") Long sendder);

    @Query(value = "WITH all_months AS (SELECT generate_series(1, 12) AS month)" +
            "SELECT TO_CHAR(TO_DATE(all_months.month\\:\\:text, 'MM'), 'Month') AS label, " +
            "COALESCE(COUNT(transaction.date), 0) AS count " +
            "FROM all_months " +
            "LEFT JOIN transaction ON EXTRACT(MONTH FROM transaction.date) = all_months.month " +
            "GROUP BY all_months.month " +
            "ORDER BY all_months.month", nativeQuery = true)
    List<Object[]> getStatsPerMonth();

    @Query(value = "SELECT * FROM transaction WHERE state = 1", nativeQuery = true)
    List<TransactionEntity> findAllActive();

    @Query(value = "SELECT * FROM transaction WHERE state = 1 and idpurchase=:annonce ", nativeQuery = true)
    List<TransactionEntity> findAllValid(@Param("annonce") String annonce);

    @Query(value = "SELECT * FROM transaction ORDER BY CAST(SUBSTRING(idtransaction FROM 4) AS INTEGER) LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<TransactionEntity> selectWithPagination(@Param("limit") int limit, @Param("offset") int offset);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE transaction set state = :state where idtransaction =:purchase", nativeQuery = true)
    void updateTransactionState(@Param("purchase") String purchase, @Param("state") int state);

}
