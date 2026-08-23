package com.bayars.billing.repository;

import com.bayars.billing.model.Board;
import com.bayars.billing.model.BoardProduct;
import com.bayars.billing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardProductRepository
        extends JpaRepository<BoardProduct, Long> {

    List<BoardProduct> findByBoard(Board board);

    Optional<BoardProduct> findByBoardAndProduct(
            Board board,
            Product product
    );
}