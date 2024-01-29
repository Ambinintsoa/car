package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.CouleurEntity;
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.service.CouleurService;
import com.commercial.commerce.sale.service.ModelService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bibine")
@AllArgsConstructor
public class CouleurController extends Controller {

    private final CouleurService couleurService;
    private final ModelService modelService;

    @GetMapping("/actu/colors")
    public ResponseEntity<ApiResponse<List<CouleurEntity>>> getAllColors() {
        try {
            List<CouleurEntity> colors = couleurService.getAllCouleurs();
            return createResponseEntity(colors, "Colors retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/pagination/colors")
    public ResponseEntity<ApiResponse<List<CouleurEntity>>> getAllColorsWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<CouleurEntity> colors = couleurService.selectWithPagination(id, limit);
            return createResponseEntity(colors, "Colors retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/colors/{id}")
    public ResponseEntity<ApiResponse<CouleurEntity>> getColorById(@PathVariable String id) {
        try {
            Optional<CouleurEntity> color = couleurService.getCouleurById(id);
            return color.map(c -> createResponseEntity(c, "Color retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.OK)
                            .body(new ApiResponse<>(null, new Status("error", "NOT FOUND"), LocalDateTime.now())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/colors")
    public ResponseEntity<ApiResponse<CouleurEntity>> createColor(HttpServletRequest request,
            @Valid @RequestBody CouleurEntity color) {
        try {
            CouleurEntity createdColor = couleurService.insertCustom(color);
            return createResponseEntity(createdColor, "Color created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/colors/{id}")
    public ResponseEntity<ApiResponse<CouleurEntity>> updateColor(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody CouleurEntity updatedColor) {
        try {
            Optional<CouleurEntity> existingColor = couleurService.updateCouleur(id, updatedColor);

            if (existingColor.isPresent()) {
                return createResponseEntity(existingColor.get(), "Color updated successfully");
            } else {
                return createResponseEntity(null, "nothing updated ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/colors/{id}")
    public ResponseEntity<ApiResponse<CouleurEntity>> deleteColor(HttpServletRequest request, @PathVariable String id,
            String authorizationHeader) {
        try {
            Optional<CouleurEntity> existingColor = couleurService.deleteCouleur(id);
            if (existingColor.isPresent()) {

                return createResponseEntity(existingColor.get(), "Color deleted successfully");
            } else {
                return createResponseEntity(null, "nothing deleted ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/colors/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(couleurService.pagination(limit), "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
