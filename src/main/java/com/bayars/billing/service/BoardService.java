package com.bayars.billing.service;

import com.bayars.billing.dto.BoardRequest;
import com.bayars.billing.dto.BoardResponse;
import com.bayars.billing.model.Board;
import com.bayars.billing.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponse createBoard(BoardRequest request) {

        Board board = Board.builder()
                .name(request.name())
                .description(request.description())
                .build();

        Board savedBoard = boardRepository.save(board);

        return mapToResponse(savedBoard);
    }

    public List<BoardResponse> getAllBoards() {

        return boardRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BoardResponse getBoard(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Board not found: " + id
                        )
                );

        return mapToResponse(board);
    }

    public BoardResponse updateBoard(
            Long id,
            BoardRequest request
    ) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Board not found: " + id
                        )
                );

        board.setName(request.name());
        board.setDescription(request.description());

        Board updatedBoard =
                boardRepository.save(board);

        return mapToResponse(updatedBoard);
    }

    public void deleteBoard(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Board not found: " + id
                        )
                );

        boardRepository.delete(board);
    }

    private BoardResponse mapToResponse(Board board) {

        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getDescription(),
                board.isActive(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }
}