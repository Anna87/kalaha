package com.bol.kalaha;

import com.bol.kalaha.controller.BoardController;
import com.bol.kalaha.controller.response.BoardState;
import com.bol.kalaha.repository.BoardRepository;
import com.bol.kalaha.repository.model.Board;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static com.bol.kalaha.BoardClient.*;
import static com.bol.kalaha.FileUtil.classpathFileToString;
import static org.hamcrest.Matchers.contains;

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
        validateSkipBoardId(actual, "/board/expected/new-board.json");
    }

    @Test
    @SneakyThrows
    void shouldPlaySimpleGame() {
        // given
        final BoardState board = createBoard();

        //when
        final String play = play(classpathFileToString("/board/request/first-1-request.json"), board.getBoardId());

        // then
        validateSkipBoardId(play, "/board/expected/should-play.json");
    }

    @Test
    @SneakyThrows
    void shouldSkipOpponentKalaha() {
        // given
        final Board boardSkipOpponentKalaha = objectMapper.readValue(classpathFileToString("/board/board-skip-opponent-kalaha.json"),
                                                   Board.class);
        final Board newBoard = boardRepository.create(boardSkipOpponentKalaha);

        //when
        final String play = play(classpathFileToString("/board/request/first-5-request.json"), newBoard.getId());

        // then
        validateSkipBoardId(play, "/board/expected/skip-opponent-kalaha-response.json");
    }

    @Test
    @SneakyThrows
    void whenLastStoneInOwnKalaha_then_extraRound_true() {
        // given
        final BoardState board = createBoard();

        //when
        final String play = play(classpathFileToString("/board/request/first-0-request.json"), board.getBoardId());

        // then
        validateSkipBoardId(play, "/board/expected/extra-round-response.json");
    }

    @Test
    @SneakyThrows
    void whenPutLastStoneInOwnEmptyPit_then_stealOpponentStones() {
        // given
        final Board board = objectMapper.readValue(classpathFileToString("/board/board-with-empty-pit.json"),
                                                   Board.class);
        final Board newBoard = boardRepository.create(board);

        //when
        final String play = play(classpathFileToString("/board/request/first-0-request.json"), newBoard.getId());

        // then
        validateSkipBoardId(play, "/board/expected/steal-stones-response.json");
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
        validateSkipBoardId(play, "/board/expected/end-game-response.json");
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
        validateSkipBoardId(play, "/board/expected/steal-opponent-end-game-response.json");
    }

    @Test
    void whenPitIndexOutOfRange() throws IOException {
        // given
        final BoardState board = createBoard();

        // when - then
        RestAssuredMockMvc.given()
                                                .spec(new MockMvcRequestSpecBuilder().setBasePath(
                                                        BoardController.API_BOARD).build())
                                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .body(classpathFileToString("/board/request/first-6-request.json"))
                                                .when()
                                                .put("/{id}", board.getBoardId())
                                                .then()
                                                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void validateSkipBoardId(String actual, String pathToExpectedResponse) throws JSONException, IOException {
        JSONAssert.assertEquals(classpathFileToString(pathToExpectedResponse), actual, new CustomComparator(
                JSONCompareMode.STRICT, new Customization("boardId", (o1, o2) -> true)));
    }
}
