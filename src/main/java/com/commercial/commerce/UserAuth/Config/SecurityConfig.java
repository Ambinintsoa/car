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
                                                .requestMatchers(HttpMethod.PUT, "/bibine/colors/{id}").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/colors/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/colors").hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/maintains/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/maintains/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/maintains").hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/categories/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/categories/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/categories").hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/models/{id}").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/models/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/models").hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/countries/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/countries/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/countries").hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/brands/{id}").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/brands/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/brands").hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/bibine/types/{id}").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/types/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/types").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/bibine/motors/{id}").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/bibine/motors/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/bibine/motors").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/bibine/annonces/{id}/validate")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/bibine/statistique/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(sessionManagement -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();

        }

}
