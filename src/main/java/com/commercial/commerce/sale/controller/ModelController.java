package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.MakeEntity;
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.entity.MotorEntity;
import com.commercial.commerce.sale.service.ModelService;
import com.commercial.commerce.sale.service.MotorService;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actu")
public class ModelController {

    private final ModelService modelService;
    private final RefreshTokenService refreshTokenService;

    public ModelController(ModelService modelService, RefreshTokenService refreshTokenService) {
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

    @PostMapping(value = "/models")
    public ResponseEntity<ApiResponse<ModelEntity>> createModel(
            @Valid @RequestBody ModelEntity modelEntity) {
        try {
            modelEntity = modelService.insertCustom(modelEntity);
            return createResponseEntity(modelEntity, "Model created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/models")
    public ResponseEntity<ApiResponse<List<ModelEntity>>> getModelsByMarks(@RequestParam("mark") String id) {
        try {
            List<ModelEntity> models = modelService.getModelsByMake(id);
            return createResponseEntity(models, "models retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/models/{id}")
    public ResponseEntity<ApiResponse<ModelEntity>> getMakeById(@PathVariable String id) {
        try {
            Optional<ModelEntity> model = modelService.getMakeById(id);

            return model.map(c -> createResponseEntity(c, "Make retrieved successfully for this id"))
                    .orElseGet(() -> createResponseEntity(model.get(), "Not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

}
