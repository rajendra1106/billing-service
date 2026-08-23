package com.bayars.billing.dto;

import jakarta.validation.constraints.NotBlank;

public record BoardRequest(

        @NotBlank(message = "Board name is required")
        String name,

        String description

) {
}