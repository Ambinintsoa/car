package com.commercial.commerce.UserAuth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.commerce.UserAuth.Models.RefreshToken;

import jakarta.transaction.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("DELETE FROM refresh_token rf WHERE rf.user.email = :useremail ")
    void deleteByEmail(@Param("useremail") String email);

    @Query(value = "SELECT roles FROM v_user where token = :token", nativeQuery = true)
    String getRole(@Param("token") String token);

    @Query(value = "SELECT id FROM v_user where token = :token", nativeQuery = true)
    Long getId(@Param("token") String token);
}
