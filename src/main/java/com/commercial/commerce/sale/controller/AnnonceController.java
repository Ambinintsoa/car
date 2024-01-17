package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.entity.CategoryEntity;
import com.commercial.commerce.sale.entity.TestEntity;
import com.commercial.commerce.sale.service.AnnonceService;
import com.commercial.commerce.sale.service.CategoryService;
import com.commercial.commerce.sale.service.TestService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/actu")
public class AnnonceController {
    private final RefreshTokenService refreshTokenService;
    private final AnnonceService annonceService;

    public AnnonceController(AnnonceService annonceService, RefreshTokenService refreshTokenService) {
        this.annonceService = annonceService;
        this.refreshTokenService = refreshTokenService;
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponseEntity(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/annonces")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getAllCategories() {
        try {
            List<AnnonceEntity> categories = annonceService.getAllEntity();
            return createResponseEntity(categories, "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/annonces")
    public ResponseEntity<ApiResponse<AnnonceEntity>> createCategory(
            @Valid @RequestBody AnnonceEntity annonce) {
        try {
            annonce.setState(1);
            AnnonceEntity createdAnnonce = annonceService.insert(annonce);
            return createResponseEntity(createdAnnonce, "Categories created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

}
