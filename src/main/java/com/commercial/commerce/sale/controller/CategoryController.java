package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.CategoryEntity;
import com.commercial.commerce.sale.service.CategoryService;

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
public class CategoryController extends Controller {

    private final CategoryService categoryService;
    private final RefreshTokenService refreshTokenService;

    public CategoryController(CategoryService categoryService, RefreshTokenService refreshTokenService) {
        this.categoryService = categoryService;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryEntity>>> getAllCategories() {
        try {
            List<CategoryEntity> categories = categoryService.getAllCategories();
            return createResponseEntity(categories, "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/pagination/categories")
    public ResponseEntity<ApiResponse<List<CategoryEntity>>> getAllTypesWithPagination(
            @RequestParam(name = "after_id") String id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<CategoryEntity> types = categoryService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Types retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> getCategoryById(@PathVariable String id) {
        try {
            Optional<CategoryEntity> category = categoryService.getCategoryById(id);
            return category.map(c -> createResponseEntity(c, "Category retrieved successfully for this id"))
                    .orElseGet(
                            () -> ResponseEntity.status(HttpStatus.OK)
                                    .body(new ApiResponse<>(null, new Status("error", "NOT FOUND"),
                                            LocalDateTime.now())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/categories")
    public ResponseEntity<ApiResponse<CategoryEntity>> createCategory(HttpServletRequest request,
            @Valid @RequestBody CategoryEntity category) {
        try {
            if (request.getHeader("Authorization") != null) {

                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    CategoryEntity createdCategory = categoryService.insertCustom(category);
                    return createResponseEntity(createdCategory, "Categories created successfully");
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

    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> updateCategory(HttpServletRequest request,
            @PathVariable String id,
            @Valid @RequestBody CategoryEntity updatedCategory) {
        try {
            if (request.getHeader("Authorization") != null) {

                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {

                    Optional<CategoryEntity> existingCategory = categoryService.updateCategory(id, updatedCategory);

                    if (existingCategory.isPresent()) {
                        return createResponseEntity(existingCategory.get(), "Category updated successfully");
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

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> deleteCategory(HttpServletRequest request,
            @PathVariable String id) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = refreshTokenService.splitToken(request.getHeader("Authorization"));
                if (refreshTokenService.verification(token)) {
                    Optional<CategoryEntity> existingCategory = categoryService.deleteCategory(id);
                    if (existingCategory.isPresent()) {
                        return createResponseEntity(existingCategory.get(), "Category updated successfully");
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
