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
import com.commercial.commerce.sale.entity.MaintainEntity;
import com.commercial.commerce.sale.service.AnnonceService;
import com.commercial.commerce.sale.service.MaintainService;
import com.commercial.commerce.sale.service.MakeService;
import com.commercial.commerce.sale.service.ModelService;
import com.commercial.commerce.sale.service.MotorService;
import com.commercial.commerce.sale.service.TypeService;
import com.commercial.commerce.sale.utils.Parameter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/actu")
@AllArgsConstructor
public class AnnonceController extends Controller {

    private final RefreshTokenService refreshTokenService;
    private final AnnonceService annonceService;
    private final MotorService motorService;
    private final TypeService typeService;
    private final ModelService modelService;
    private final MakeService makeService;
    private final MaintainService maintainService;

    @GetMapping("/annonces")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getAllAnnonces() {
        try {
            List<AnnonceEntity> categories = annonceService.getAllEntity();
            return createResponseEntity(categories, "Announcements retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/annonces")
    public ResponseEntity<ApiResponse<AnnonceEntity>> createAnnonce(HttpServletRequest request,
            @Valid @RequestBody AnnonceEntity annonce) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.validation(token)) {
                    annonce.setState(1);
                    annonce.getVendeur().setIdvendeur(refreshTokenService.getId(token));
                    annonce.setBrand(makeService.getMakeById(annonce.getBrand().getId()).get());
                    annonce.setType(typeService.getTypeById(annonce.getType().getId()).get());
                    annonce.setModele(modelService.getModelById(annonce.getModele().getId()).get());
                    annonce.setMotor(motorService.getMotorById(annonce.getMotor().getId()).get());
                    List<MaintainEntity> maintains = new ArrayList<>();
                    // MaintainEntity.removeDuplicates(annonce.getMaintenance());
                    for (int i = 0; i < annonce.getMaintenance().size(); i++) {
                        maintains.add(maintainService.getMaintainById(annonce.getMaintenance().get(i).getId()).get());

                    }
                    annonce.setMaintenance(maintains);
                    List<Long> favoris = new ArrayList<>();
                    annonce.setFavoris(favoris);
                    annonce.setDate(LocalDateTime.now());

                    AnnonceEntity createdAnnonce = annonceService.insert(annonce);

                    return createResponseEntity(createdAnnonce, "Announcement created successfully");
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("refused", "you can't access to this url"),
                                LocalDateTime.now()));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }
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
                    return createResponseEntity(createdAnnonce, "Announcement created successfully");
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
                    return createResponseEntity(updatedAnnonce, "Announcement removed successfully");
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
            return createResponseEntity(annonces, "Announcement retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getLocalizedMessage()),
                            LocalDateTime.now()));
        }
    }

    @GetMapping("/annonces/{id}")
    public ResponseEntity<ApiResponse<AnnonceEntity>> search(@PathVariable String id

    ) {
        try {
            AnnonceEntity categories = annonceService.getById(id).get();
            return createResponseEntity(categories, "Announcement retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/annonces/search")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getAnnonceById(@Valid @RequestBody Parameter parametre

    ) {
        try {
            return createResponseEntity(annonceService.getInf(parametre.getInfMontant()),
                    "Announcement retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
