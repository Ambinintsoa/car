package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import com.commercial.commerce.sale.utils.Statistique;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bibine")
@AllArgsConstructor
public class PurchaseController extends Controller {
    private final PurchaseService purchaseService;
    private final AnnonceService annonceService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(value = "/user/{iduser}/purchases")
    public ResponseEntity<ApiResponse<PurchaseEntity>> save(HttpServletRequest request,
            @Valid @RequestBody PurchaseEntity purchase, @PathVariable Long iduser) {
        try {
            AnnonceEntity annonceEntity = annonceService.getById(purchase.getAnnouncement());
            if (this.isTokenValidAchat(refreshTokenService.splitToken(request.getHeader("Authorization")),
                    annonceEntity.getVendeur().getIdvendeur()) == true) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "cannot buy your own car"),
                                LocalDateTime.now()));
            }
            purchase.setUser(new User());
            purchase.getUser().setId(iduser);
            PurchaseEntity createdAnnonce = purchaseService.insert(purchase);
            return createResponseEntity(createdAnnonce, "Purchase created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/achat")
    public ResponseEntity<ApiResponse<TransactionEntity>> achat(HttpServletRequest request,
            @Valid @RequestBody PurchaseEntity purchaseEntity) {
        try {
            purchaseEntity = purchaseService.getById(purchaseEntity.getId()).get();
            if (this.isTokenValid(refreshTokenService.splitToken(request.getHeader("Authorization")),
                    purchaseEntity.getUser().getId()) == false) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "not the user"),
                                LocalDateTime.now()));
            }

            purchaseEntity = purchaseService.getById(purchaseEntity.getId()).get();
            purchaseService.updateState(purchaseEntity, 2);

            AnnonceEntity annonce = annonceService.getById(purchaseEntity.getAnnouncement());
            annonce = annonceService.updateAnnonceState(annonce.getId(), 2).get();
            TransactionEntity createdAnnonce = purchaseService.achat(purchaseEntity,
                    annonce.getVendeur().getIdvendeur());
            return createResponseEntity(createdAnnonce, "Purchase created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/purchases")
    public ResponseEntity<ApiResponse<List<PurchaseEntity>>> getAllPurchases() {
        try {
            List<PurchaseEntity> categories = purchaseService.getAllPurchase();
            return createResponseEntity(categories, "Purchases retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/user/{iduser}/accepted")
    public ResponseEntity<ApiResponse<List<PurchaseEntity>>> getAllValid(HttpServletRequest request,
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @PathVariable Long iduser) {
        try {
            List<PurchaseEntity> annonces = purchaseService.getAllPurchaseValid(iduser, id, limit);
            return createResponseEntity(annonces, "Purchases retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/purchases/{id}")
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

    @GetMapping("/user/{iduser}/valid/purchases")
    public ResponseEntity<ApiResponse<List<PurchaseEntity>>> getFavoris(HttpServletRequest request,
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @PathVariable Long iduser) {
        try {
            List<PurchaseEntity> annonces = purchaseService.selectPurchase(iduser, id, limit);
            return createResponseEntity(annonces, "Purchases retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/user/{iduser}/sent_purchases")
    public ResponseEntity<ApiResponse<List<PurchaseEntity>>> getAllSent(HttpServletRequest request,
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @PathVariable Long iduser) {
        try {
            List<PurchaseEntity> annonces = purchaseService.getAllSent(iduser, id, limit);
            return createResponseEntity(annonces, "Purchases retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/pagination/purchases")
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

    @PutMapping("/actu/valid/purchases/{id}")
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

    @PutMapping("/actu/unvalid/purchases/{id}")
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

    @GetMapping("/statistique/purchases/sent")
    public ResponseEntity<ApiResponse<List<Statistique>>> countSoldCarsTypes() {
        try {

            return createResponseEntity(purchaseService.statPurchase(),
                    "Annonces retrieved successfully for the given state");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

}
