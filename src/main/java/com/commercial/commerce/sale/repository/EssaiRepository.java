package com.commercial.commerce.sale.repository;

import com.commercial.commerce.sale.entity.EssaiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssaiRepository extends JpaRepository<EssaiEntity, Long> {

}