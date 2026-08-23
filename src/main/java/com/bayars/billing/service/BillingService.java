package com.bayars.billing.service;

import com.bayars.billing.dto.BillItemResponse;
import com.bayars.billing.dto.BillRequest;
import com.bayars.billing.dto.BillResponse;
import com.bayars.billing.model.Board;
import com.bayars.billing.model.BoardProduct;
import com.bayars.billing.model.Product;
import com.bayars.billing.repository.BoardProductRepository;
import com.bayars.billing.repository.BoardRepository;
import com.bayars.billing.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BoardRepository boardRepository;
    private final ProductRepository productRepository;
    private final BoardProductRepository boardProductRepository;

    @Transactional(readOnly = true)
    public BillResponse calculateBill(BillRequest request) {

        Board board = boardRepository.findById(request.boardId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Board not found: " + request.boardId()
                        )
                );

        List<BillItemResponse> items = request.items()
                .stream()
                .map(item -> {

                    Product product = productRepository.findById(
                            item.productId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found: "
                                            + item.productId()
                            )
                    );

                    BoardProduct boardProduct =
                            boardProductRepository
                                    .findByBoardAndProduct(
                                            board,
                                            product
                                    )
                                    .orElseThrow(() ->
                                            new RuntimeException(
                                                    "Product is not available in this board: "
                                                            + product.getName()
                                            )
                                    );

                    BigDecimal total =
                            boardProduct.getPrice()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    item.quantity()
                                            )
                                    );

                    return BillItemResponse.builder()
                            .productId(product.getId())
                            .productName(product.getName())
                            .quantity(item.quantity())
                            .unitPrice(boardProduct.getPrice())
                            .total(total)
                            .build();
                })
                .toList();

        BigDecimal grandTotal = items.stream()
                .map(BillItemResponse::total)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        return BillResponse.builder()
                .items(items)
                .grandTotal(grandTotal)
                .build();
    }
}