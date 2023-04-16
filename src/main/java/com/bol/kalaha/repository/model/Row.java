package com.bol.kalaha.repository.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Data
public class Row {
    protected static final int INITIAL_AMOUNT_KALAHH_STONES = 0;

    List<Cell> cells;
    int kalahaStones;

    public Row() {
        this.cells = IntStream.range(0,6).mapToObj( i -> new Cell()).toList();
        this.kalahaStones = INITIAL_AMOUNT_KALAHH_STONES;
    }
}
