package com.bol.kalaha.repository.model;

import lombok.Data;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class Row {
    protected static final int INITIAL_AMOUNT_KALAHH_STONES = 0;

    List<Pit> pits;
    int kalahaStones;

    public Row() {
        this.pits = IntStream.range(0, 6).mapToObj(i -> new Pit()).toList();
        this.kalahaStones = INITIAL_AMOUNT_KALAHH_STONES;
    }
}
