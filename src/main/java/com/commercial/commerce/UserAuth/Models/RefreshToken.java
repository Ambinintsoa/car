package com.commercial.commerce.UserAuth.Models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "refresh_token")
@Table(name = "refresh_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(generator = "rt_seq")
    @SequenceGenerator(name = "rt_seq", sequenceName = "rtf_seq", allocationSize = 1)
    Long id;

    @Column(name = "token")
    String token;

    @Column(name = "expire_date", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    Instant expireDate;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    User user;

}
