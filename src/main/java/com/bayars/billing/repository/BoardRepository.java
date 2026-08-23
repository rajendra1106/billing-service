package com.bayars.billing.repository;

import com.bayars.billing.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository
        extends JpaRepository<Board, Long> {
}