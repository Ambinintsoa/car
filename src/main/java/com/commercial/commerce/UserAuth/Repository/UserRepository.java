package com.commercial.commerce.UserAuth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.commerce.UserAuth.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}
