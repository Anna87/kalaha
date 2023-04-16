package com.bol.kalaha.repository;

import com.bol.kalaha.repository.model.Board;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface BoardRepository {

    Board create(Board  board);

    Optional<Board> findById(UUID id);
}
