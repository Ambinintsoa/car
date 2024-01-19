package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.service.ModelService;

import jakarta.servlet.http.HttpServletRequest;

import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actu")
public class ModelController extends Controller {

    private final ModelService modelService;
    private final RefreshTokenService refreshTokenService;

    public ModelController(ModelService modelService, RefreshTokenService refreshTokenService) {
        this.modelService = modelService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(value = "/models")
    public ResponseEntity<ApiResponse<ModelEntity>> createModel(HttpServletRequest request,
            @Valid @RequestBody ModelEntity modelEntity) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    modelEntity = modelService.insertCustom(modelEntity);
                    return createResponseEntity(modelEntity, "Model created successfully");
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

    @GetMapping("brand/{mark}/models")
    public ResponseEntity<ApiResponse<List<ModelEntity>>> getModelsByMarks(@PathVariable String mark) {
        try {
            List<ModelEntity> models = modelService.getModelsByMake(mark);
            return createResponseEntity(models, "models retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/models/{id}")
    public ResponseEntity<ApiResponse<ModelEntity>> getModelById(@PathVariable String id) {
        try {
            Optional<ModelEntity> model = modelService.getModelById(id);

            return model.map(c -> createResponseEntity(c, "Make retrieved successfully for this id"))
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
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<ModelEntity> existingMake = modelService.update(id, updatedMake);

                    if (existingMake.isPresent()) {
                        return createResponseEntity(existingMake.get(), "Make updated successfully");
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

    @DeleteMapping("/models/{id}")
    public ResponseEntity<ApiResponse<ModelEntity>> deleteModel(HttpServletRequest request, @PathVariable String id,
            String authorizationHeader) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<ModelEntity> existingMake = modelService.delete(id);
                    if (existingMake.isPresent()) {

                        return createResponseEntity(existingMake.get(), "Make deleted successfully");
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
