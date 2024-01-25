package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.commercial.commerce.UserAuth.Models.User;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.entity.PurchaseEntity;
import com.commercial.commerce.sale.entity.TransactionEntity;
import com.commercial.commerce.sale.service.AnnonceService;
import com.commercial.commerce.sale.service.PurchaseService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/actu")
@AllArgsConstructor
public class PurchaseController extends Controller {
    private final PurchaseService purchaseService;
    private final RefreshTokenService refreshTokenService;
    private final AnnonceService annonceService;

    @PostMapping(value = "/purchases")
    public ResponseEntity<ApiResponse<PurchaseEntity>> save(HttpServletRequest request,
            @Valid @RequestBody PurchaseEntity purchase) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.validation(token)) {
                    purchase.setUser(new User());
                    purchase.getUser().setId(refreshTokenService.getId(token));
                    PurchaseEntity createdAnnonce = purchaseService.insert(purchase);
                    return createResponseEntity(createdAnnonce, "Purchase created successfully");
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

    @PostMapping(value = "/achat")
    public ResponseEntity<ApiResponse<TransactionEntity>> achat(HttpServletRequest request,
            @RequestParam(name = "purchase") String id) {
        try {
            PurchaseEntity purchase = new PurchaseEntity();
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.validation(token)) {

                    purchase = purchaseService.getById(id).get();
                    AnnonceEntity annonce = annonceService.getById(purchase.getAnnouncement()).get();
                    TransactionEntity createdAnnonce = purchaseService.achat(purchase,
                            annonce.getVendeur());
                    return createResponseEntity(createdAnnonce, "Purchase created successfully");
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

    @GetMapping("/purchases")
    public ResponseEntity<ApiResponse<List<PurchaseEntity>>> getAllPurchases() {
        try {
            List<PurchaseEntity> categories = purchaseService.getAllPurchase();
            return createResponseEntity(categories, "Purchases retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/accepted/purchases")
    public ResponseEntity<ApiResponse<List<PurchaseEntity>>> getAllValid(HttpServletRequest request,
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    List<PurchaseEntity> annonces = purchaseService.getAllPurchaseValid(token, id, limit);
                    return createResponseEntity(annonces, "Purchases retrieved successfully");
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

    @GetMapping("/purchases/{id}")
    public ResponseEntity<ApiResponse<PurchaseEntity>> getPurchaseById(@PathVariable String id

    ) {
        try {
            PurchaseEntity categories = purchaseService.getById(id).get();
            return createResponseEntity(categories, "Purchases retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/valid/purchases")
    public ResponseEntity<ApiResponse<List<PurchaseEntity>>> getFavoris(HttpServletRequest request,
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    List<PurchaseEntity> annonces = purchaseService.selectPurchase(token, id, limit);
                    return createResponseEntity(annonces, "Purchases retrieved successfully");
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

    @GetMapping("/pagination/purchases")
    public ResponseEntity<ApiResponse<List<PurchaseEntity>>> getAllPurchasesWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<PurchaseEntity> types = purchaseService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Purchases retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/valid/purchases/{id}")
    public ResponseEntity<ApiResponse<PurchaseEntity>> validePurchase(@PathVariable String id

    ) {
        try {
            PurchaseEntity categories = purchaseService.getById(id).get();
            purchaseService.updateState(categories, 2);
            return createResponseEntity(categories, "purchase  updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/unvalid/purchases/{id}")
    public ResponseEntity<ApiResponse<PurchaseEntity>> unvalidePurchase(@PathVariable String id

    ) {
        try {
            PurchaseEntity categories = purchaseService.getById(id).get();
            purchaseService.updateState(categories, 0);
            return createResponseEntity(categories, "purchase  updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

}
