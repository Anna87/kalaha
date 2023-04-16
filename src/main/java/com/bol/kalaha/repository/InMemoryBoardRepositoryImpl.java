package com.bol.kalaha.repository;

import com.bol.kalaha.repository.model.Board;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryBoardRepositoryImpl implements BoardRepository {

    private final Map<UUID, Board> boards = new ConcurrentHashMap<>();

    @Override
    public Board create(Board board) {
        final UUID id = UUID.randomUUID();
        board.setId(id);
        boards.put(id, board);

        return board;
    }

    @Override
    public Optional<Board> findById(UUID id) {
        return Optional.ofNullable(boards.get(id));
    }
}
