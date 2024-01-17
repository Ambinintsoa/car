package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.TypeEntity;
import com.commercial.commerce.sale.service.TypeService;

import jakarta.servlet.http.HttpServletRequest;

import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actu")
public class TypeController {

    private final TypeService typeService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public TypeController(TypeService typeService, RefreshTokenService refreshTokenService) {
        this.typeService = typeService;
        this.refreshTokenService = refreshTokenService;
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponseEntity(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/types")
    public ResponseEntity<ApiResponse<List<TypeEntity>>> getAllTypes() {
        try {
            List<TypeEntity> types = typeService.getAllTypes();
            return createResponseEntity(types, "Types retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/types/{id}")
    public ResponseEntity<ApiResponse<TypeEntity>> getTypeById(@PathVariable String id) {
        try {
            Optional<TypeEntity> type = typeService.getTypeById(id);
            return type.map(c -> createResponseEntity(c, "Type retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/types")
    public ResponseEntity<ApiResponse<TypeEntity>> createType(
            @RequestParam("nom") String nom) {
        try {
            TypeEntity type = new TypeEntity();
            type.setNom(nom);
            TypeEntity createdType = typeService.insertCustom(type);
            return createResponseEntity(createdType, "Type created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/types/{id}")
    public ResponseEntity<ApiResponse<TypeEntity>> updateType(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody TypeEntity updatedType) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {

                    Optional<TypeEntity> existingType = typeService.updateType(id, updatedType);

                    if (existingType.isPresent()) {
                        return createResponseEntity(existingType.get(), "Type updated successfully");
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
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/types/{id}")
    public ResponseEntity<ApiResponse<TypeEntity>> deleteType(HttpServletRequest request, @PathVariable String id) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<TypeEntity> existingType = typeService.deleteType(id);

                    if (existingType.isPresent()) {
                        return createResponseEntity(existingType.get(), "Type deleted successfully");
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
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
