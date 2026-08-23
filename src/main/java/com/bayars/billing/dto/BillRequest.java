package com.bayars.billing.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BillRequest(

        @NotNull(message = "Board ID is required")
        Long boardId,

        @NotEmpty(message = "Bill must contain at least one item")
        @Valid
        List<BillItemRequest> items

) {
}