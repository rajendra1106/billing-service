package com.bayars.billing.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record BillResponse(

        List<BillItemResponse> items,

        BigDecimal grandTotal

) {
}