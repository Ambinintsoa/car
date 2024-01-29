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
		config.addAllowedOrigin("http://localhost:8100"); // Replace with yourallowed origins
		config.addAllowedOrigin("http://localhost");
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedOrigin("https://main--deft-churros-fac1e5.netlify.app/");
		config.addAllowedOrigin("https://deft-churros-fac1e5.netlify.app/");

		config.addAllowedOrigin("https://main--serene-mooncake-d83e90.netlify.app/");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");

		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	public static void main(String[] args) {
		SpringApplication.run(CommerceApplication.class, args);
	}

}
