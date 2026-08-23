package com.bayars.billing.dto;

import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}