package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.TypeEntity;
import com.commercial.commerce.sale.service.TypeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
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
public class TypeController extends Controller {

    private final TypeService typeService;

    @GetMapping("/actu/types")
    public ResponseEntity<ApiResponse<List<TypeEntity>>> getAllTypes() {
        try {
            List<TypeEntity> types = typeService.getAllTypes();
            return createResponseEntity(types, "Types retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/pagination/types")
    public ResponseEntity<ApiResponse<List<TypeEntity>>> getAllTypesWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<TypeEntity> types = typeService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Types retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/types/{id}")
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
            TypeEntity createdType = typeService.insertCustom(type);
            return createResponseEntity(createdType, "Type created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/types/{id}")
    public ResponseEntity<ApiResponse<TypeEntity>> updateType(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody TypeEntity updatedType) {
        try {

            Optional<TypeEntity> existingType = typeService.updateType(id, updatedType);

            if (existingType.isPresent()) {
                return createResponseEntity(existingType.get(), "Type updated successfully");
            } else {
                return createResponseEntity(null, "nothing updated ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/types/{id}")
    public ResponseEntity<ApiResponse<TypeEntity>> deleteType(HttpServletRequest request, @PathVariable String id) {
        try {
            Optional<TypeEntity> existingType = typeService.deleteType(id);

            if (existingType.isPresent()) {
                return createResponseEntity(existingType.get(), "Type deleted successfully");
            } else {
                return createResponseEntity(null, "nothing deleted ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/types/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(typeService.pagination(limit), "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
