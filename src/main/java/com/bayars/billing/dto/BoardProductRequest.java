package com.bayars.billing.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BoardProductRequest(

        @NotNull(message = "Product ID is required")
        Long productId,

        @NotNull(message = "Price is required")
        @DecimalMin(
                value = "0.01",
                message = "Price must be greater than 0"
        )
        BigDecimal price

) {
}
