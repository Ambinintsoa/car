package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;

public class Controller {
    public <T> ResponseEntity<ApiResponse<T>> createResponseEntity(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setStatus(new Status("ok", message));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
