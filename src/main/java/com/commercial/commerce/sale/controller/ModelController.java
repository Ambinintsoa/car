package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.ModelEntity;
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
public class ModelController extends Controller {

    private final ModelService modelService;

    @PostMapping(value = "/models")
    public ResponseEntity<ApiResponse<ModelEntity>> createModel(HttpServletRequest request,
            @Valid @RequestBody ModelEntity modelEntity) {
        try {
            modelEntity = modelService.insertCustom(modelEntity);
            return createResponseEntity(modelEntity, "Model created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/pagination/models")
    public ResponseEntity<ApiResponse<List<ModelEntity>>> getAllModelsWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<ModelEntity> types = modelService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Models retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/brand/{mark}/models")
    public ResponseEntity<ApiResponse<List<ModelEntity>>> getModelsByMarks(@PathVariable String mark) {
        try {
            List<ModelEntity> models = modelService.getModelsByMake(mark);
            return createResponseEntity(models, "models retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/models")
    public ResponseEntity<ApiResponse<List<ModelEntity>>> getModels() {
        try {
            List<ModelEntity> models = modelService.getAll();
            return createResponseEntity(models, "models retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/models/{id}")
    public ResponseEntity<ApiResponse<ModelEntity>> getModelById(@PathVariable String id) {
        try {
            Optional<ModelEntity> model = modelService.getModelById(id);

            return model.map(c -> createResponseEntity(c, "Model retrieved successfully for this id"))
                    .orElseGet(() -> createResponseEntity(model.get(), "Not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/models/{id}")
    public ResponseEntity<ApiResponse<ModelEntity>> updateModel(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody ModelEntity updatedMake) { // Change le nom du type d'entité
        try {
            Optional<ModelEntity> existingMake = modelService.update(id, updatedMake);

            if (existingMake.isPresent()) {
                return createResponseEntity(existingMake.get(), "Model updated successfully");
            } else {
                return createResponseEntity(null, "nothing updated ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/models/{id}")
    public ResponseEntity<ApiResponse<ModelEntity>> deleteModel(HttpServletRequest request, @PathVariable String id,
            String authorizationHeader) {
        try {
            Optional<ModelEntity> existingMake = modelService.delete(id);
            if (existingMake.isPresent()) {

                return createResponseEntity(existingMake.get(), "Model deleted successfully");
            } else {
                return createResponseEntity(null, "nothing deleted ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/models/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(modelService.pagination(limit), "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
