package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.MakeEntity; // Importe la classe MakeEntity
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.service.MakeService; // Importe le service pour les makes
import com.commercial.commerce.sale.service.ModelService;

import jakarta.servlet.http.HttpServletRequest;

import com.commercial.commerce.UserAuth.Config.JwtService;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actu")
public class MakeController {

    private final MakeService makeService;
    private final ModelService modelService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public MakeController(MakeService makeService, ModelService modelService, RefreshTokenService refreshTokenService) {
        this.makeService = makeService;
        this.modelService = modelService;
        this.refreshTokenService = refreshTokenService;
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponseEntity(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/makes")
    public ResponseEntity<ApiResponse<List<MakeEntity>>> getAllMakes() {
        try {
            List<MakeEntity> makes = makeService.getAllMakes();
            return createResponseEntity(makes, "Makes retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/makes/{id}")
    public ResponseEntity<ApiResponse<MakeEntity>> getMakeById(@PathVariable String id) {
        try {
            Optional<MakeEntity> make = makeService.getMakeById(id);
            return make.map(c -> createResponseEntity(c, "Make retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/makes", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<MakeEntity>> createMake(
            @RequestParam("nom") String nom) {
        try {
            MakeEntity make = new MakeEntity();
            make.setNom(nom);
            MakeEntity createdMake = makeService.insertCustom(make);
            return createResponseEntity(createdMake, "Make created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/makes/{id}")
    public ResponseEntity<ApiResponse<MakeEntity>> updateMake(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody MakeEntity updatedMake) { // Change le nom du type d'entité
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<MakeEntity> existingMake = makeService.updateMake(id, updatedMake);

                    if (existingMake.isPresent()) {
                        return createResponseEntity(existingMake.get(), "Make updated successfully");
                    } else {
                        return createResponseEntity(null, "nothing updated ");
                    }
                }
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ApiResponse<>(null, new Status("refused", "you can't access to this url"),
                                LocalDateTime.now()));

            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/makes/{id}")
    public ResponseEntity<ApiResponse<MakeEntity>> deleteMake(HttpServletRequest request, @PathVariable String id,
            String authorizationHeader) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    Optional<MakeEntity> existingMake = makeService.deleteMake(id);
                    // supprime recursivement les models associes
                    List<ModelEntity> models = modelService.getModelsByMake(id);
                    for (int i = 0; i < models.size(); i++) {
                        modelService.deleteModel(models.get(i).getId());
                    }
                    if (existingMake.isPresent()) {

                        return createResponseEntity(existingMake.get(), "Make deleted successfully");
                    } else {
                        return createResponseEntity(null, "nothing deleted ");
                    }
                }
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ApiResponse<>(null, new Status("refused", "you can't access to this url"),
                                LocalDateTime.now()));

            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

}