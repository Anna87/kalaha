package com.bol.kalaha.controller;

import com.bol.kalaha.controller.request.PlayRequest;
import com.bol.kalaha.controller.response.BoardState;
import com.bol.kalaha.service.BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(BoardController.API_BOARD)
public class BoardController {
    public static final String API_BOARD = "/api/boards";

    private final BoardService boardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardState init() {
        return boardService.create();
    }

    @PutMapping(path = "/{id}")
    public BoardState play(@RequestBody @Valid final PlayRequest playRequest, @PathVariable("id") final UUID id){
        return boardService.play(id, playRequest);
    }

    @GetMapping(path = "/{id}")
    public BoardState getBoard(@PathVariable("id") final UUID id){
        return boardService.getBoard(id);
    }
}
