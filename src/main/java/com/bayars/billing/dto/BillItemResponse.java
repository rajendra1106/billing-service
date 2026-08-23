package com.bayars.billing.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BillItemResponse(

        Long productId,

        String productName,

        Integer quantity,

        BigDecimal unitPrice,

        BigDecimal total

) {
}
