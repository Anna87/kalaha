package com.bol.kalaha.repository.model;


import lombok.Data;

@Data
public class Cell {
    private static final int INIT_AMOUNT_OF_STONES = 6;

    private int stones;

    public Cell() {
        this.stones = INIT_AMOUNT_OF_STONES;
    }
}
