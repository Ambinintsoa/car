package com.commercial.commerce.UserAuth.Models;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.commercial.commerce.UserAuth.Enum.Role;
import com.commercial.commerce.sale.entity.CountryEntity;
import com.commercial.commerce.sale.entity.TypeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "user")
@Table(name = "_user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user__seq")
    @SequenceGenerator(name = "user__seq", sequenceName = "_user_seq", allocationSize = 1)
    Long id;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "name")
    String name;

    @Column(name = "dtn")
    Date dtn;

    @Column(name = "gender")
    Integer gender;

    @Column(name = "profile")
    String profile;

    @Column(name = "compte")
    Double compte;

    @Enumerated(EnumType.STRING)
    Role roles;

    @ManyToOne
    @JoinColumn(name = "idcountry", referencedColumnName = "idcountry")
    private CountryEntity country;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
