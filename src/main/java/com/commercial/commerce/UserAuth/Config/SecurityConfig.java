package com.commercial.commerce.UserAuth.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity

@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/bibine/actu/**").permitAll()
                                                .requestMatchers("/auth/**").permitAll()
                                                .requestMatchers("/websocket/**").permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/bibine/colors/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/colors/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/colors")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/bibine/actu/pagination/annonces")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/bibine/maintains/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/maintains/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/maintains")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/actu/annonces/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/bibine/categories/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/categories/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/categories")
                                                .hasAuthority("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/models/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/models/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/models")
                                                .hasAuthority("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/countries/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/countries/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/countries")
                                                .hasAuthority("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/brands/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/brands/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/brands")
                                                .hasAuthority("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/types/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/types/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/types").hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/bibine/motors/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/motors/{id}")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/motors")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/bibine/annonces/{id}/validate")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/bibine/statistique/**")
                                                .hasAuthority("ADMIN")

                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(sessionManagement -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();

        }

}
