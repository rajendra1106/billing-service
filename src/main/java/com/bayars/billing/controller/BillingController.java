package com.bayars.billing.controller;

import com.bayars.billing.dto.BillRequest;
import com.bayars.billing.dto.BillResponse;
import com.bayars.billing.service.BillingService;
import com.bayars.billing.service.PdfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;
    private final PdfService pdfService;

    @PostMapping("/calculate")
    public BillResponse calculateBill(
            @Valid @RequestBody BillRequest request
    ) {
        return billingService.calculateBill(request);
    }

    @PostMapping(
            value = "/generate",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> generateBill(
            @Valid @RequestBody BillRequest request
    ) {

        BillResponse bill = billingService.calculateBill(request);

        byte[] pdf = pdfService.generateBillPdf(bill);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=bill.pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}