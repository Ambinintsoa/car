package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.MotorEntity;
import com.commercial.commerce.sale.service.MotorService;

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
public class MotorController {

    private final MotorService motorService;
    private final RefreshTokenService refreshTokenService;

    public MotorController(MotorService motorService, RefreshTokenService refreshTokenService) {
        this.motorService = motorService;
        this.refreshTokenService = refreshTokenService;
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponseEntity(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<ApiResponse<Void>> createVoidResponseEntity(String message) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/motors")
    public ResponseEntity<ApiResponse<List<MotorEntity>>> getAllMotors() {
        try {
            List<MotorEntity> motors = motorService.getAllMotors();
            return createResponseEntity(motors, "Motors retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/motors/{id}")
    public ResponseEntity<ApiResponse<MotorEntity>> getMotorById(@PathVariable String id) {
        try {
            Optional<MotorEntity> motor = motorService.getMotorById(id);
            return motor.map(c -> createResponseEntity(c, "Motor retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/motors", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<MotorEntity>> createMotor(
            @RequestParam("nom") String nom) {
        try {
            MotorEntity motor = new MotorEntity();
            motor.setNom(nom);
            MotorEntity createdMotor = motorService.insertCustom(motor);
            return createResponseEntity(createdMotor, "Motor created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/motors/{id}")
    public ResponseEntity<ApiResponse<MotorEntity>> updateMotor(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody MotorEntity updatedMotor) {
        try {
            if (request.getHeader("Authorization") != null) {

                String token = request.getHeader("Authorization").split("Bearer ")[1];
                if (refreshTokenService.isRefreshTokenValid(token)
                        && refreshTokenService.getRole(token).compareToIgnoreCase("admin") == 0) {
                    Optional<MotorEntity> existingMotor = motorService.updateMotor(id, updatedMotor);

                    if (existingMotor.isPresent()) {
                        return createResponseEntity(existingMotor.get(), "Motor updated successfully");
                    } else {
                        return createResponseEntity(null, "nothing updated ");
                    }
                }
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", "token not valid"), LocalDateTime.now()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/motors/{id}")
    public ResponseEntity<ApiResponse<MotorEntity>> deleteMotor(HttpServletRequest request, @PathVariable String id) {
        try {
            if (request.getHeader("Authorization") != null) {

                String token = request.getHeader("Authorization").split("Bearer ")[1];
                if (refreshTokenService.isRefreshTokenValid(token)
                        && refreshTokenService.getRole(token).compareToIgnoreCase("admin") == 0) {
                    Optional<MotorEntity> existingMotor = motorService.deleteMotor(id);

                    if (existingMotor.isPresent()) {
                        return createResponseEntity(existingMotor.get(), "Motor updated successfully");
                    } else {
                        return createResponseEntity(null, "nothing deleted ");
                    }
                }
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ApiResponse<>(null, new Status("error", "this url is protected"),
                                LocalDateTime.now()));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(null, new Status("error", "token not valid"), LocalDateTime.now()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
