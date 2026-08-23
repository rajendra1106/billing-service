package com.bayars.billing.controller;

import com.bayars.billing.dto.BoardProductRequest;
import com.bayars.billing.dto.BoardProductResponse;
import com.bayars.billing.service.BoardProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardId}/products")
@RequiredArgsConstructor
public class BoardProductController {

    private final BoardProductService boardProductService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardProductResponse addProduct(
            @PathVariable Long boardId,
            @Valid @RequestBody BoardProductRequest request
    ) {
        return boardProductService.addProductToBoard(
                boardId,
                request
        );
    }

    @GetMapping
    public List<BoardProductResponse> getProducts(
            @PathVariable Long boardId
    ) {
        return boardProductService.getBoardProducts(
                boardId
        );
    }

    @PutMapping("/{boardProductId}")
    public BoardProductResponse updatePrice(
            @PathVariable Long boardProductId,
            @Valid @RequestBody BoardProductRequest request
    ) {
        return boardProductService.updatePrice(
                boardProductId,
                request
        );
    }

    @DeleteMapping("/{boardProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(
            @PathVariable Long boardProductId
    ) {
        boardProductService.removeProductFromBoard(
                boardProductId
        );
    }
}
