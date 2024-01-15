package com.commercial.commerce.response;

import java.time.LocalDateTime;

import com.commercial.commerce.sale.entity.CategoryEntity;

public class ApiResponse<T> {

    private T data;
    private Status status;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data, Status status, LocalDateTime timestamp) {
        this.data = data;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ApiResponse<CategoryEntity> createResponseEntity(CategoryEntity data, String message) {
        Status status = new Status("good", message);
        return new ApiResponse<>(data, status, LocalDateTime.now());
    }

    // Getters and setters

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
