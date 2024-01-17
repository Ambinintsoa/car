package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import com.commercial.commerce.sale.entity.TestEntity;
import com.commercial.commerce.sale.service.TestService;

@RestController
@RequestMapping("/actu")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponseEntity(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tests")
    public ResponseEntity<ApiResponse<List<TestEntity>>> getAllCategories() {
        try {
            List<TestEntity> categories = testService.getAllEntity();
            return createResponseEntity(categories, "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

}
