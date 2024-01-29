package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.MotorEntity;
import com.commercial.commerce.sale.service.MotorService;
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
public class MotorController extends Controller {

    private final MotorService motorService;

    @GetMapping("/actu/motors")
    public ResponseEntity<ApiResponse<List<MotorEntity>>> getAllMotors() {
        try {
            List<MotorEntity> motors = motorService.getAllMotors();
            return createResponseEntity(motors, "Motors retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/pagination/motors")
    public ResponseEntity<ApiResponse<List<MotorEntity>>> getAllMotorsWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<MotorEntity> types = motorService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Motors retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/motors/{id}")
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
            MotorEntity createdMotor = motorService.insertCustom(motor);
            return createResponseEntity(createdMotor, "Motor created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/motors/{id}")
    public ResponseEntity<ApiResponse<MotorEntity>> updateMotor(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody MotorEntity updatedMotor) {
        try {

            Optional<MotorEntity> existingMotor = motorService.updateMotor(id, updatedMotor);

            if (existingMotor.isPresent()) {
                return createResponseEntity(existingMotor.get(), "Motor updated successfully");
            } else {
                return createResponseEntity(null, "nothing updated ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/motors/{id}")
    public ResponseEntity<ApiResponse<MotorEntity>> deleteMotor(HttpServletRequest request, @PathVariable String id) {
        try {
            Optional<MotorEntity> existingMotor = motorService.deleteMotor(id);

            if (existingMotor.isPresent()) {
                return createResponseEntity(existingMotor.get(), "Motor updated successfully");
            } else {
                return createResponseEntity(null, "nothing deleted ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/motors/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(motorService.pagination(limit), "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
