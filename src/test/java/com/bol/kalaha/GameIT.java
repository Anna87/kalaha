package com.bol.kalaha;

import com.bol.kalaha.controller.response.BoardState;
import com.bol.kalaha.repository.BoardRepository;
import com.bol.kalaha.repository.model.Board;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;

import static com.bol.kalaha.BoardClient.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = {"itest"})
public class GameIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @SneakyThrows
    void shouldInitGame() {
        // when
        final String actual = initBoard();

        // then
        JSONAssert.assertEquals(classpathFileToString("/board/expected/new-board.json"), actual, new CustomComparator(
                JSONCompareMode.STRICT, new Customization("boardId", (o1, o2) -> true)));
    }

    @Test
    @SneakyThrows
    void shouldPlay() {
        // given
        final BoardState board = createBoard();

        //when
        final String play = play(classpathFileToString("/board/request/first-1-request.json"), board.getBoardId());

        // then
        JSONAssert.assertEquals(classpathFileToString("/board/expected/should-play.json"), play, new CustomComparator(
                JSONCompareMode.STRICT, new Customization("boardId", (o1, o2) -> true)));
    }

    @Test
    @SneakyThrows
    void whenLastStoneInOwnKalaha_then_extraRound_true() {
        // given
        final BoardState board = createBoard();

        //when
        final String play = play(classpathFileToString("/board/request/first-0-request.json"), board.getBoardId());

        // then
        JSONAssert.assertEquals(classpathFileToString("/board/expected/extra-round-response.json"),
                                play,
                                new CustomComparator(
                                        JSONCompareMode.STRICT, new Customization("boardId", (o1, o2) -> true)));
    }

    @Test
    @SneakyThrows
    void whenPutLastStoneInOwnEmptyCell_then_stealOpponentStones() {
        // given
        final Board board = objectMapper.readValue(classpathFileToString("/board/board-with-empty-cell.json"),
                                                   Board.class);
        final Board newBoard = boardRepository.create(board);

        //when
        final String play = play(classpathFileToString("/board/request/first-0-request.json"), newBoard.getId());

        // then
        JSONAssert.assertEquals(classpathFileToString("/board/expected/steal-stones-response.json"),
                                play,
                                new CustomComparator(
                                        JSONCompareMode.STRICT, new Customization("boardId", (o1, o2) -> true)));
    }

    @Test
    @SneakyThrows
    void whenPlayLastStoneInRow_then_gameOver() {
        // given
        final Board board = objectMapper.readValue(classpathFileToString("/board/board-last-round.json"),
                                                   Board.class);
        final Board newBoard = boardRepository.create(board);

        //when
        final String play = play(classpathFileToString("/board/request/first-5-request.json"), newBoard.getId());

        // then
        JSONAssert.assertEquals(classpathFileToString("/board/expected/end-game-response.json"),
                                play,
                                new CustomComparator(
                                        JSONCompareMode.STRICT, new Customization("boardId", (o1, o2) -> true)));
    }

    @Test
    @SneakyThrows
    void whenStealLastStonesFromOpponentRow_then_gameOver() {
        // given
        final Board board = objectMapper.readValue(classpathFileToString("/board/board-with-last-stones-in-opponent-row.json"),
                                                   Board.class);
        final Board newBoard = boardRepository.create(board);

        //when
        final String play = play(classpathFileToString("/board/request/first-3-request.json"), newBoard.getId());

        // then
        JSONAssert.assertEquals(classpathFileToString("/board/expected/steal-opponent-end-game-response.json"),
                                play,
                                new CustomComparator(
                                        JSONCompareMode.STRICT, new Customization("boardId", (o1, o2) -> true)));
    }


    public static String classpathFileToString(String path) throws IOException {
        return Files.readString(new ClassPathResource(path).getFile().toPath());
    }
}
