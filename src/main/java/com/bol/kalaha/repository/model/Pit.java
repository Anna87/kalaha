package com.bol.kalaha.repository.model;


import lombok.Data;

@Data
public class Pit {
    private static final int INIT_AMOUNT_OF_STONES = 6;

    private int stones;

    public Pit() {
        this.stones = INIT_AMOUNT_OF_STONES;
    }
}
