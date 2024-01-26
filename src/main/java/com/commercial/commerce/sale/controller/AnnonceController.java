package com.commercial.commerce.sale.controller;

import java.sql.Timestamp;
import java.time.Instant;
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

import com.commercial.commerce.UserAuth.Models.User;
import com.commercial.commerce.UserAuth.Service.AuthService;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.entity.MaintainEntity;
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.service.AnnonceService;
import com.commercial.commerce.sale.service.CountryService;
import com.commercial.commerce.sale.service.MaintainService;
import com.commercial.commerce.sale.service.MakeService;
import com.commercial.commerce.sale.service.ModelService;
import com.commercial.commerce.sale.service.MotorService;
import com.commercial.commerce.sale.service.TypeService;
import com.commercial.commerce.sale.utils.Parameter;
import com.commercial.commerce.sale.utils.Vendeur;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/bibine")
@AllArgsConstructor
public class AnnonceController extends Controller {

    private final AnnonceService annonceService;
    private final MotorService motorService;
    private final ModelService modelService;
    private final MakeService makeService;
    private final MaintainService maintainService;
    private final RefreshTokenService refreshTokenService;
    private final CountryService countryService;
    private final AuthService authService;

    @GetMapping("/actu/annonces")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getAllAnnonces() {
        try {
            List<AnnonceEntity> categories = annonceService.getAllEntity();
            return createResponseEntity(categories, "Announcements retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/pagination/annonces")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getAllAnnoncesPagination(
            @RequestParam(name = "offset", defaultValue = "0") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<AnnonceEntity> categories = annonceService.getAllWithPagination(id, limit);
            return createResponseEntity(categories, "Announcements retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/annonces/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(annonceService.pagination(limit), "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/user/{iduser}/annonces")
    public ResponseEntity<ApiResponse<AnnonceEntity>> createAnnonce(HttpServletRequest request,
            @Valid @RequestBody AnnonceEntity annonce, @PathVariable Long iduser) {
        try {
            if (this.isTokenValid(refreshTokenService.splitToken(request.getHeader("Authorization")),
                    iduser) == false) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "cannot access to this request"),
                                LocalDateTime.now()));
            }
            annonce.setState(1);
            annonce.setVendeur(new Vendeur());
            annonce.setDate(LocalDateTime.now());
            annonce.getVendeur().setIdvendeur(iduser);
            annonce.setBrand(makeService.getMakeById(annonce.getBrand().getId()).get());
            annonce.setLocalisation(countryService.getCountryById(annonce.getLocalisation().getId()).get());
            annonce.setModele(modelService.getModelById(annonce.getModele().getId()).get());
            annonce.setMotor(motorService.getMotorById(annonce.getMotor().getId()).get());
            if (annonce.getEtat() > 10 || annonce.getEtat() < 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "Etat not valid"),
                                LocalDateTime.now()));
            }
            List<MaintainEntity> maintains = new ArrayList<>();
            annonce.setMaintenance(MaintainEntity.removeDuplicates(annonce.getMaintenance()));
            for (int i = 0; i < annonce.getMaintenance().size(); i++) {
                maintains.add(maintainService.getMaintainById(annonce.getMaintenance().get(i).getId()).get());

            }
            annonce.setMaintenance(maintains);
            List<Long> favoris = new ArrayList<>();
            annonce.setFavoris(favoris);

            AnnonceEntity createdAnnonce = annonceService.insert(annonce);

            return createResponseEntity(createdAnnonce, "Announcement created successfully");
        } catch (

        Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/user/{iduser}/annonces_favoris/{id}")
    public ResponseEntity<ApiResponse<AnnonceEntity>> addFavoris(HttpServletRequest request, @PathVariable String id,
            @PathVariable Long iduser) {
        try {
            if (this.isTokenValid(refreshTokenService.splitToken(request.getHeader("Authorization")),
                    iduser) == false) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "cannot access to this request"),
                                LocalDateTime.now()));
            }
            AnnonceEntity createdAnnonce = annonceService.addFavoris(iduser, id);
            return createResponseEntity(createdAnnonce, "Announcement created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/user/{iduser}/annonces_favoris/{id}")
    public ResponseEntity<ApiResponse<AnnonceEntity>> removeFavoris(HttpServletRequest request,
            @PathVariable String id, @PathVariable Long iduser) {
        try {
            if (this.isTokenValid(refreshTokenService.splitToken(request.getHeader("Authorization")),
                    iduser) == false) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "cannot access to this request"),
                                LocalDateTime.now()));
            }
            AnnonceEntity updatedAnnonce = annonceService.removeFavoris(iduser, id);
            return createResponseEntity(updatedAnnonce, "Announcement removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)

                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/user/{iduser}/annonces_favoris")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getFavoris(HttpServletRequest request,
            @PathVariable Long iduser) {
        try {
            if (this.isTokenValid(refreshTokenService.splitToken(request.getHeader("Authorization")),
                    iduser) == false) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "cannot access to this request"),
                                LocalDateTime.now()));
            }
            List<AnnonceEntity> annonces = annonceService.getAnnoncesByFavoris(iduser);
            return createResponseEntity(annonces, "Announcement retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/user/{iduser}/own_annonces")
    public ResponseEntity<ApiResponse<List<AnnonceEntity>>> getMyAnnonce(HttpServletRequest request,
            @PathVariable Long iduser) {
        try {
            if (this.isTokenValid(refreshTokenService.splitToken(request.getHeader("Authorization")),
                    iduser) == false) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(null, new Status("error", "cannot access to this request"),
                                LocalDateTime.now()));
            }
            List<AnnonceEntity> annonces = annonceService.getAnnoncesByVendeur(iduser);
            return createResponseEntity(annonces, "Announcement retrieved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/annonces/recentes")
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

    @GetMapping("/actu/annonces/{id}")
    public ResponseEntity<ApiResponse<AnnonceEntity>> search(@PathVariable String id

    ) {
        try {
            AnnonceEntity categories = annonceService.getById(id);
            return createResponseEntity(categories, "Announcement retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/annonces/search")
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
