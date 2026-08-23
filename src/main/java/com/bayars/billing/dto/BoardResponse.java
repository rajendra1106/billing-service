package com.bayars.billing.dto;

import java.time.LocalDateTime;

public record BoardResponse(
        Long id,
        String name,
        String description,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}