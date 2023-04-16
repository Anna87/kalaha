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
    private int cellIndex;
    private boolean canDoExtraRound;

    public void shift() {
        if (isKalaha()) {
            swapRowIndex();
            resetCellIndex();
        } else {
            incrementCellIndex();
        }

        if (!isOpponentKalaha()) {
            this.stones--;
        }

    }

    public boolean isKalaha() {
        return this.cellIndex == KALAHA_INDEX;
    }

    public boolean putInCell() {
        return !isKalaha();
    }

    private boolean isOpponentKalaha() {
        return isKalaha() && !isOwnRow();
    }

    public boolean checkRuleIsOwnKalaha() {
        return isOwnRow() && isKalaha();
    }

    public boolean isOwnRow() {
        final int currentPlayerRowIndex = this.currentPlayer.equals(Player.FIRST) ? 1 : 2;
        return currentPlayerRowIndex == rowIndex;
    }

    public boolean isLastStone() {
        return this.stones == 1;
    }

    private void incrementCellIndex() {
        this.cellIndex++;
    }

    private void resetCellIndex() {
        this.cellIndex = 0;
    }

    private void swapRowIndex() {
        this.rowIndex = rowIndex == 1 ? 2 : 1;
    }

}
