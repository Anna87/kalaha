package com.bol.kalaha.controller.response;

import com.bol.kalaha.repository.model.Board;
import com.bol.kalaha.repository.model.Player;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class BoardState {
    UUID boardId;
    Player nextPlayer;
    RowDetails rowOne;
    RowDetails rowTwo;
    boolean gameOver;
    Player winner;

    public static BoardState from(final Board board) {

        return BoardState.builder()
                         .boardId(board.getId())
                         .gameOver(board.isGameOver())
                         .nextPlayer(board.getNextPlayer())
                         .rowOne(RowDetails.from(board.getRowOne()))
                         .rowTwo(RowDetails.from(board.getRowTwo()))
                         .winner(board.getWinner().orElse(null))
                         .build();
    }
}
