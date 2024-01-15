package com.commercial.commerce.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class Status {
    private final String status;
    private final String details;

    public Status(String status, String details) {
        this.status = status;
        this.details = details;

    }
}
