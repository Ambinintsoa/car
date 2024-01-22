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
public class TypeController extends Controller {

    private final TypeService typeService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public TypeController(TypeService typeService, RefreshTokenService refreshTokenService) {
        this.typeService = typeService;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("/types")
    public ResponseEntity<ApiResponse<List<TypeEntity>>> getAllTypes() {
        try {
            List<TypeEntity> types = typeService.getAllTypes();
            return createResponseEntity(types, "Types retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/pagination/types")
    public ResponseEntity<ApiResponse<List<TypeEntity>>> getAllTypesWithPagination(
            @RequestParam(name = "after_id") String id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<TypeEntity> types = typeService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Types retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/types/{id}")
    public ResponseEntity<ApiResponse<TypeEntity>> getTypeById(@PathVariable String id) {
        try {
            Optional<TypeEntity> type = typeService.getTypeById(id);
            return type.map(c -> createResponseEntity(c, "Type retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.OK)
                            .body(new ApiResponse<>(null, new Status("error", "NOT FOUND"), LocalDateTime.now())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/types")
    public ResponseEntity<ApiResponse<TypeEntity>> createType(HttpServletRequest request,
            @Valid @RequestBody TypeEntity type) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    TypeEntity createdType = typeService.insertCustom(type);
                    return createResponseEntity(createdType, "Type created successfully");
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
}
