package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.MaintainEntity;
import com.commercial.commerce.sale.service.MaintainService;

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
public class MaintainController extends Controller {

    private final MaintainService maintainService;

    @GetMapping("/actu/maintains")
    public ResponseEntity<ApiResponse<List<MaintainEntity>>> getAllMaintains() {
        try {
            List<MaintainEntity> maintains = maintainService.getAllMaintains();
            return createResponseEntity(maintains, "Maintains retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/pagination/maintains")
    public ResponseEntity<ApiResponse<List<MaintainEntity>>> getAllMaintainssWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<MaintainEntity> types = maintainService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Maintains retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/maintains/{id}")
    public ResponseEntity<ApiResponse<MaintainEntity>> getMaintainById(@PathVariable String id) {
        try {
            Optional<MaintainEntity> maintain = maintainService.getMaintainById(id);
            return maintain.map(c -> createResponseEntity(c, "Maintain retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.OK)
                            .body(new ApiResponse<>(null, new Status("error", "NOT FOUND"), LocalDateTime.now())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/maintains")
    public ResponseEntity<ApiResponse<MaintainEntity>> createMaintain(HttpServletRequest request,
            @Valid @RequestBody MaintainEntity maintain) {
        try {
            MaintainEntity createdMaintain = maintainService.insertCustom(maintain);
            return createResponseEntity(createdMaintain, "Maintain created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/maintains/{id}")
    public ResponseEntity<ApiResponse<MaintainEntity>> updateMaintain(HttpServletRequest request,
            @PathVariable String id,
            @Valid @RequestBody MaintainEntity updatedMaintain) {
        try {
            Optional<MaintainEntity> existingMaintain = maintainService.updateMaintain(id, updatedMaintain);

            if (existingMaintain.isPresent()) {
                return createResponseEntity(existingMaintain.get(), "Maintain updated successfully");
            } else {
                return createResponseEntity(null, "nothing updated ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/maintains/{id}")
    public ResponseEntity<ApiResponse<MaintainEntity>> deleteMaintain(HttpServletRequest request,
            @PathVariable String id) {
        try {
            Optional<MaintainEntity> existingMaintain = maintainService.deleteMaintain(id);

            if (existingMaintain.isPresent()) {
                return createResponseEntity(existingMaintain.get(), "Maintain updated successfully");
            } else {
                return createResponseEntity(null, "nothing deleted ");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/maintains/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(maintainService.pagination(limit), "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
