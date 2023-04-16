package com.bol.kalaha.service;

import com.bol.kalaha.controller.request.PlayRequest;
import com.bol.kalaha.exception.BoardNotFoundException;
import com.bol.kalaha.exception.EmptyCellException;
import com.bol.kalaha.exception.GameOverException;
import com.bol.kalaha.exception.PlayerOrderException;
import com.bol.kalaha.repository.BoardRepository;
import com.bol.kalaha.repository.model.Board;
import com.bol.kalaha.repository.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    private static final UUID BOARD_ID = UUID.fromString("d392b7e4-cbba-11ed-afa1-0242ac120002");

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Test
    void testGameOverException() {
        // given
        final Board board = new Board();
        board.setGameOver(true);

        when(boardRepository.findById(BOARD_ID))
               .thenReturn(Optional.of(board));

        PlayRequest playRequest = new PlayRequest(1, Player.ONE);

        // when - then
        assertThrows(GameOverException.class, () -> boardService.play(BOARD_ID, playRequest));
    }

    @Test
    void testBoardNotFoundException() {
        // given
        when(boardRepository.findById(BOARD_ID))
               .thenReturn(Optional.empty());

        PlayRequest playRequest = new PlayRequest(1, Player.ONE);

        // when - then
        assertThrows(BoardNotFoundException.class, () -> boardService.play(BOARD_ID, playRequest));
    }

    @Test
    void testEmptyCellException() {
        // given
        final Board board = new Board();
        board.getCell(1,1).setStones(0);

        when(boardRepository.findById(BOARD_ID))
                .thenReturn(Optional.of(board));

        final PlayRequest playRequest = new PlayRequest(1, Player.ONE);

        // when - then
        assertThrows(EmptyCellException.class, () -> boardService.play(BOARD_ID, playRequest));
    }

    @Test
    void testIncorrectPlayerOrderException() {
        // given
        final Board board = new Board();
        when(boardRepository.findById(BOARD_ID))
                .thenReturn(Optional.of(board));

        final PlayRequest playRequest1 = new PlayRequest(1, Player.TWO);

        // when - then
        assertThrows(PlayerOrderException.class, () -> boardService.play(BOARD_ID, playRequest1));
    }
}