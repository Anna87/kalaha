package com.bol.kalaha.service;

import com.bol.kalaha.controller.request.PlayRequest;
import com.bol.kalaha.repository.model.Board;
import com.bol.kalaha.repository.model.Pit;
import com.bol.kalaha.repository.model.Player;
import com.bol.kalaha.exception.EmptyPitException;
import com.bol.kalaha.exception.GameOverException;
import com.bol.kalaha.exception.PlayerOrderException;
import lombok.experimental.UtilityClass;

import static com.bol.kalaha.service.PitHelper.getCurrentPit;

@UtilityClass
public class RoundValidator {
    public static void validateBoardState(final Board board, final PlayRequest playRequest) {
        validateIsGameOver(board);
        validatePitHasStones(board, playRequest);
        validatePlayerOrder(board, playRequest);
    }

    public static void validateIsGameOver(final Board board) {
        if (board.isGameOver()) {
            throw new GameOverException("Game is over");
        }
    }

    public static void validatePitHasStones(final Board board, final PlayRequest playRequest) {
        final Pit currentPit = getCurrentPit(board, playRequest.getPitIndex());
        if (currentPit.getStones() == 0) {
            throw new EmptyPitException("Current pit is empty");
        }
    }

    public static void validatePlayerOrder(final Board board, final PlayRequest playRequest) {
        final Player nextPlayer = board.getNextPlayer();
        if (!nextPlayer.equals(playRequest.getPlayer())) {
            throw new PlayerOrderException("Incorrect player order, next is %s".formatted(nextPlayer));
        }
    }
}
