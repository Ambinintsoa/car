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
public class MotorController extends Controller {

    private final MotorService motorService;
    private final RefreshTokenService refreshTokenService;

    public MotorController(MotorService motorService, RefreshTokenService refreshTokenService) {
        this.motorService = motorService;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("/motors")
    public ResponseEntity<ApiResponse<List<MotorEntity>>> getAllMotors() {
        try {
            List<MotorEntity> motors = motorService.getAllMotors();
            return createResponseEntity(motors, "Motors retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/pagination/motors")
    public ResponseEntity<ApiResponse<List<MotorEntity>>> getAllTypesWithPagination(
            @RequestParam(name = "after_id") String id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<MotorEntity> types = motorService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Types retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/motors/{id}")
    public ResponseEntity<ApiResponse<MotorEntity>> getMotorById(@PathVariable String id) {
        try {
            Optional<MotorEntity> motor = motorService.getMotorById(id);
            return motor.map(c -> createResponseEntity(c, "Motor retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.OK)
                            .body(new ApiResponse<>(null, new Status("error", "NOT FOUND"), LocalDateTime.now())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/motors")
    public ResponseEntity<ApiResponse<MotorEntity>> createMotor(HttpServletRequest request,
            @Valid @RequestBody MotorEntity motor) {
        try {
            if (request.getHeader("Authorization") != null) {

                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    MotorEntity createdMotor = motorService.insertCustom(motor);
                    return createResponseEntity(createdMotor, "Motor created successfully");
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

    @PutMapping("/motors/{id}")
    public ResponseEntity<ApiResponse<MotorEntity>> updateMotor(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody MotorEntity updatedMotor) {
        try {
            if (request.getHeader("Authorization") != null) {

                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<MotorEntity> existingMotor = motorService.updateMotor(id, updatedMotor);

                    if (existingMotor.isPresent()) {
                        return createResponseEntity(existingMotor.get(), "Motor updated successfully");
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

    @DeleteMapping("/motors/{id}")
    public ResponseEntity<ApiResponse<MotorEntity>> deleteMotor(HttpServletRequest request, @PathVariable String id) {
        try {
            if (request.getHeader("Authorization") != null) {

                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<MotorEntity> existingMotor = motorService.deleteMotor(id);

                    if (existingMotor.isPresent()) {
                        return createResponseEntity(existingMotor.get(), "Motor updated successfully");
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
