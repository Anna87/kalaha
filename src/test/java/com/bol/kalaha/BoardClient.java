package com.bol.kalaha;

import com.bol.kalaha.controller.BoardController;
import com.bol.kalaha.controller.response.BoardState;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

@UtilityClass
public class BoardClient {

    public static String initBoard() {
        return create().asString();
    }

    public static BoardState createBoard() {
        return create().as(BoardState.class);
    }

    private static ExtractableResponse<MockMvcResponse> create() {
        return RestAssuredMockMvc.given()
                                 .spec(new MockMvcRequestSpecBuilder().setBasePath(
                                         BoardController.API_BOARD).build())
                                 .accept(MediaType.APPLICATION_JSON_VALUE)
                                 .when()
                                 .post()
                                 .then()
                                 .statusCode(HttpStatus.CREATED.value())
                                 .extract();
    }

    public static String getBoard(final UUID id) {
        return RestAssuredMockMvc.given()
                                 .spec(new MockMvcRequestSpecBuilder().setBasePath(
                                         BoardController.API_BOARD).build())
                                 .accept(MediaType.APPLICATION_JSON_VALUE)
                                 .when()
                                 .get("/{id}", id)
                                 .then()
                                 .statusCode(HttpStatus.OK.value())
                                 .extract().asString();
    }

    public static String play(final String request, final UUID id) {
        return RestAssuredMockMvc.given()
                                 .spec(new MockMvcRequestSpecBuilder().setBasePath(
                                         BoardController.API_BOARD).build())
                                 .accept(MediaType.APPLICATION_JSON_VALUE)
                                 .contentType(MediaType.APPLICATION_JSON_VALUE)
                                 .body(request)
                                 .when()
                                 .put("/{id}", id)
                                 .then()
                                 .statusCode(HttpStatus.OK.value())
                                 .extract().asString();
    }

}
