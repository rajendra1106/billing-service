package com.bayars.billing.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(

        @NotBlank(message = "Product name is required")
        String name,

        String description

) {
}