package com.bayars.billing.dto;

import java.math.BigDecimal;

public record BoardProductResponse(
        Long id,
        Long boardId,
        Long productId,
        String productName,
        BigDecimal price
) {
}