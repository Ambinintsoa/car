package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.MakeEntity;
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.service.MakeService;
import com.commercial.commerce.sale.service.ModelService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MakeController extends Controller {

    private final MakeService makeService;
    private final ModelService modelService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<MakeEntity>>> getAllMakes() {
        try {
            List<MakeEntity> makes = makeService.getAllMakes();
            return createResponseEntity(makes, "Brands retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/pagination/brands")
    public ResponseEntity<ApiResponse<List<MakeEntity>>> getAllBrandsWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<MakeEntity> types = makeService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Brands retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<ApiResponse<MakeEntity>> getMakeById(@PathVariable String id) {
        try {
            Optional<MakeEntity> make = makeService.getMakeById(id);
            return make.map(c -> createResponseEntity(c, "Brands retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.OK)
                            .body(new ApiResponse<>(null, new Status("error", "NOT FOUND"), LocalDateTime.now())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/brands")
    public ResponseEntity<ApiResponse<MakeEntity>> createMake(HttpServletRequest request,
            @Valid @RequestBody MakeEntity make) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    MakeEntity createdMake = makeService.insertCustom(make);
                    return createResponseEntity(createdMake, "Brand created successfully");
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

    @PutMapping("/brands/{id}")
    public ResponseEntity<ApiResponse<MakeEntity>> updateMake(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody MakeEntity updatedMake) { // Change le nom du type d'entité
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<MakeEntity> existingMake = makeService.updateMake(id, updatedMake);

                    if (existingMake.isPresent()) {
                        return createResponseEntity(existingMake.get(), "Brand updated successfully");
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

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<ApiResponse<MakeEntity>> deleteMake(HttpServletRequest request, @PathVariable String id,
            String authorizationHeader) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<MakeEntity> existingMake = makeService.deleteMake(id);
                    // supprime recursivement les models associes
                    List<ModelEntity> models = modelService.getModelsByMake(id);
                    for (int i = 0; i < models.size(); i++) {
                        modelService.delete(models.get(i).getId());
                    }
                    if (existingMake.isPresent()) {

                        return createResponseEntity(existingMake.get(), "Brand deleted successfully");
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
