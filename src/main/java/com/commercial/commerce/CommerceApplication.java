package com.commercial.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class CommerceApplication {

	@Bean
	CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// Configure allowed origins, methods, headers, etc.
		config.addAllowedOrigin("*"); // Replace with your allowed origins
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	public static void main(String[] args) {
		SpringApplication.run(CommerceApplication.class, args);
	}

}
