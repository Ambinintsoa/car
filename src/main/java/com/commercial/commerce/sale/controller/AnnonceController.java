package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.entity.PurchaseEntity;
import com.commercial.commerce.sale.service.AnnonceService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/actu")
public class AnnonceController extends Controller {

    private final RefreshTokenService refreshTokenService;
    private final AnnonceService annonceService;

    public AnnonceController(AnnonceService annonceService, RefreshTokenService refreshTokenService) {
        this.annonceService = annonceService;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("/annonces")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getAllAnnonces() {
        try {
            List<AnnonceEntity> categories = annonceService.getAllEntity();
            return createResponseEntity(categories, "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/annonces")
    public ResponseEntity<ApiResponse<AnnonceEntity>> createAnnonce(
            @Valid @RequestBody AnnonceEntity annonce) {
        try {
            annonce.setState(1);
            List<Long> favoris = new ArrayList<>();
            annonce.setFavoris(favoris);
            annonce.setDate(LocalDateTime.now());
            AnnonceEntity createdAnnonce = annonceService.insert(annonce);
            return createResponseEntity(createdAnnonce, "Announcement created successfully");

        } catch (

        Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/annonces/{id}/favoris")
    public ResponseEntity<ApiResponse<AnnonceEntity>> addFavoris(HttpServletRequest request, @PathVariable String id) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.validation(token)) {
                    AnnonceEntity createdAnnonce = annonceService.addFavoris(token, id);
                    return createResponseEntity(createdAnnonce, "Categories created successfully");
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("refused", "you can't access to this url"),
                                LocalDateTime.now()));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/annonces/{id}/favoris")
    public ResponseEntity<ApiResponse<AnnonceEntity>> removeFavoris(HttpServletRequest request,
            @PathVariable String id) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.validation(token)) {
                    AnnonceEntity updatedAnnonce = annonceService.removeFavoris(token, id);
                    return createResponseEntity(updatedAnnonce, "Favoris removed successfully");
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("refused", "you can't access to this url"),
                                LocalDateTime.now()));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)

                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/annonces/favoris")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getFavoris(HttpServletRequest request) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    List<AnnonceEntity> annonces = annonceService.getAnnoncesByFavoris(token);
                    return createResponseEntity(annonces, "Favoris retrieved successfully");
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("refused", "you can't access to this url"),
                                LocalDateTime.now()));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/own/annonces")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getMyAnnonce(HttpServletRequest request) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    List<AnnonceEntity> annonces = annonceService.getAnnoncesByVendeur(token);
                    return createResponseEntity(annonces, "Announcement retrieved successfully");
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("refused", "you can't access to this url"),
                                LocalDateTime.now()));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/annonces/recentes")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getRecentAnnonces(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<AnnonceEntity> annonces = annonceService.getRecentAnnonces(limit); // Récupère les 5 annonces les plus
            // récentes
            return createResponseEntity(annonces, "Favoris retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getLocalizedMessage()),
                            LocalDateTime.now()));
        }
    }

    @GetMapping("/annonces/{id}")
    public ResponseEntity<ApiResponse<AnnonceEntity>> getAnnonceById(@PathVariable String id

    ) {
        try {
            AnnonceEntity categories = annonceService.getById(id).get();
            return createResponseEntity(categories, "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
