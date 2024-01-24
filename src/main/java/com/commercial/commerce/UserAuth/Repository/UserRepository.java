package com.commercial.commerce.UserAuth.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.commerce.UserAuth.Models.User;
import com.commercial.commerce.sale.entity.PurchaseEntity;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}
