package com.bol.kalaha.repository.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoundHolder {
    private static final int KALAHA_INDEX = 6;

    private int stones;
    private Player currentPlayer;
    private int rowIndex;
    private int pitIndex;
    private boolean canDoExtraRound;

    public void shift() {
        if (isPit() || isOwnKalaha()) {
            this.stones--;
        }

        if (isPit()) {
            incrementPitIndex();
        } else {
            swapRowIndex();
            resetPitIndex();
        }
    }

    public boolean isPit() {
        return this.pitIndex != KALAHA_INDEX;
    }

    public boolean isOwnKalaha() {
        return isOwnRow() && !isPit();
    }

    public boolean isOwnRow() {
        final int currentPlayerRowIndex = this.currentPlayer.equals(Player.ONE) ? 1 : 2;
        return currentPlayerRowIndex == rowIndex;
    }

    public boolean isLastStone() {
        return this.stones == 1;
    }

    private void incrementPitIndex() {
        this.pitIndex++;
    }

    private void resetPitIndex() {
        this.pitIndex = 0;
    }

    private void swapRowIndex() {
        this.rowIndex = rowIndex == 1 ? 2 : 1;
    }

}
