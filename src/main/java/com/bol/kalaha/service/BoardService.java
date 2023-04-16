package com.bol.kalaha.service;

import com.bol.kalaha.controller.request.PlayRequest;
import com.bol.kalaha.controller.response.BoardState;
import com.bol.kalaha.repository.model.*;
import com.bol.kalaha.repository.BoardRepository;
import com.bol.kalaha.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.bol.kalaha.repository.model.Player.*;
import static com.bol.kalaha.service.CellHelper.*;
import static com.bol.kalaha.service.RoundValidator.validateBoardState;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardState create() {
        final Board board = boardRepository.create(new Board());
        return BoardState.from(board);
    }

    public BoardState play(final UUID boardId, final PlayRequest playRequest) {
        final Board board = findBoard(boardId);

        validateBoardState(board, playRequest);

        final RoundHolder roundHolder = buildRoundHolder(playRequest, board);

        moveStones(board, roundHolder);

        completeRound(board, roundHolder);

        return BoardState.from(board);
    }

    public BoardState getBoard(final UUID id) {
        return BoardState.from(findBoard(id));
    }

    private RoundHolder buildRoundHolder(final PlayRequest playRequest, final Board board) {
        final int cellIndex = playRequest.getCellIndex();
        final int currentPlayerRowIndex = getCurrentPlayerRowIndex(playRequest.getPlayer());
        final int stones = pollStones(board, currentPlayerRowIndex, cellIndex);

        return RoundHolder.builder()
                          .stones(stones)
                          .currentPlayer(playRequest.getPlayer())
                          .rowIndex(currentPlayerRowIndex)
                          .cellIndex(cellIndex + 1)
                          .build();
    }

    private void moveStones(final Board board, final RoundHolder roundHolder) {
        do {
            moveOneStone(board, roundHolder);
            roundHolder.shift();
        } while (roundHolder.getStones() > 0);
    }

    private Board findBoard(final UUID id) {
        return boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("Board not found"));
    }

    private void moveOneStone(final Board board, final RoundHolder roundHolder) {
        if (roundHolder.putInCell()) {
            putInCell(board, roundHolder);
        } else if (roundHolder.checkRuleIsOwnKalaha()) {
            putInOwnKalaha(board, roundHolder);
        }
    }

    private void putInOwnKalaha(Board board, RoundHolder roundHolder) {
        putOneStoneInKalaha(board, roundHolder.getRowIndex());

        // last stone comes in own kalaha
        if (roundHolder.isLastStone()) {
            roundHolder.setCanDoExtraRound(true);
        }
    }

    private void putInCell(Board board, RoundHolder roundHolder) {
        if (checkRuleEmptyOwnCell(board, roundHolder) && roundHolder.isLastStone()) {
            stealStones(board, roundHolder);
        } else {
            putOneStoneInCell(board, roundHolder.getRowIndex(), roundHolder.getCellIndex());
        }
    }

    private void stealStones(final Board board, final RoundHolder roundHolder) {
        final int rowIndex = roundHolder.getRowIndex();
        putOneStoneInKalaha(board, rowIndex);

        final int reverseCellIndex = getReverseCellIndex(roundHolder.getCellIndex());
        final int reverseRowIndex = getReverseRowIndex(rowIndex);
        final int stones = pollStones(board, reverseRowIndex, reverseCellIndex);

        putStonesInKalaha(board, rowIndex, stones);
    }

    private void completeRound(final Board board, final RoundHolder roundHolder) {
        if (board.isGameOver()) {
            moveRemainingStonesToKalaha(board);
            board.setGameOver(true);
        } else if (!roundHolder.isCanDoExtraRound()) {
            setNextPlayer(board);
        }
    }

    private void setNextPlayer(final Board board) {
        final Player nextPlayer = FIRST.equals(board.getNextPlayer()) ? SECOND : FIRST;
        board.setNextPlayer(nextPlayer);
    }

    private int getReverseRowIndex(int rowIndex) {
        return rowIndex == 1 ? 2 : 1;
    }

    private int getCurrentPlayerRowIndex(Player player) {
        return FIRST.equals(player) ? 1 : 2;
    }

}
