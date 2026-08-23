package com.bayars.billing.controller;

import com.bayars.billing.dto.BoardRequest;
import com.bayars.billing.dto.BoardResponse;
import com.bayars.billing.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardResponse createBoard(
            @Valid @RequestBody BoardRequest request
    ) {
        return boardService.createBoard(request);
    }

    @GetMapping
    public List<BoardResponse> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public BoardResponse getBoard(
            @PathVariable Long id
    ) {
        return boardService.getBoard(id);
    }

    @PutMapping("/{id}")
    public BoardResponse updateBoard(
            @PathVariable Long id,
            @Valid @RequestBody BoardRequest request
    ) {
        return boardService.updateBoard(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(
            @PathVariable Long id
    ) {
        boardService.deleteBoard(id);
    }
}