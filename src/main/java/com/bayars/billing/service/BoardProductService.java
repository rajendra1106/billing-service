package com.bayars.billing.service;

import com.bayars.billing.dto.BoardProductRequest;
import com.bayars.billing.dto.BoardProductResponse;
import com.bayars.billing.model.Board;
import com.bayars.billing.model.BoardProduct;
import com.bayars.billing.model.Product;
import com.bayars.billing.repository.BoardProductRepository;
import com.bayars.billing.repository.BoardRepository;
import com.bayars.billing.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardProductService {

    private final BoardProductRepository boardProductRepository;
    private final BoardRepository boardRepository;
    private final ProductRepository productRepository;

    @Transactional
    public BoardProductResponse addProductToBoard(
            Long boardId,
            BoardProductRequest request
    ) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Board not found: " + boardId
                        )
                );

        Product product = productRepository.findById(
                        request.productId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found: "
                                        + request.productId()
                        )
                );

        boardProductRepository
                .findByBoardAndProduct(board, product)
                .ifPresent(existing -> {
                    throw new RuntimeException(
                            "Product already exists in this board"
                    );
                });

        BoardProduct boardProduct = BoardProduct.builder()
                .board(board)
                .product(product)
                .price(request.price())
                .build();

        BoardProduct saved =
                boardProductRepository.save(boardProduct);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<BoardProductResponse> getBoardProducts(
            Long boardId
    ) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Board not found: " + boardId
                        )
                );

        return boardProductRepository
                .findByBoard(board)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public BoardProductResponse updatePrice(
            Long boardProductId,
            BoardProductRequest request
    ) {

        BoardProduct boardProduct =
                boardProductRepository.findById(
                        boardProductId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Board product not found: "
                                        + boardProductId
                        )
                );

        boardProduct.setPrice(request.price());

        BoardProduct updated =
                boardProductRepository.save(boardProduct);

        return mapToResponse(updated);
    }

    @Transactional
    public void removeProductFromBoard(
            Long boardProductId
    ) {

        if (!boardProductRepository.existsById(
                boardProductId
        )) {
            throw new RuntimeException(
                    "Board product not found: "
                            + boardProductId
            );
        }

        boardProductRepository.deleteById(
                boardProductId
        );
    }

    private BoardProductResponse mapToResponse(
            BoardProduct boardProduct
    ) {

        return new BoardProductResponse(
                boardProduct.getId(),
                boardProduct.getBoard().getId(),
                boardProduct.getProduct().getId(),
                boardProduct.getProduct().getName(),
                boardProduct.getPrice()
        );
    }
}