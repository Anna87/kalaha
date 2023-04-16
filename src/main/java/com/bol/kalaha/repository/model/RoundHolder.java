package com.bol.kalaha.repository.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoundHolder {
    protected static final int KALAHA_INDEX = 6;

    private int stones;
    private Player currentPlayer;
    private int rowIndex;
    private int cellIndex;
    private boolean canDoExtraRound;

    public void shift() {
        this.stones--;

        if(this.cellIndex < KALAHA_INDEX) {
            this.cellIndex++;
        } else {
            this.rowIndex = rowIndex == 1 ? 2 : 1;
            this.cellIndex = 0;
        }
    }

    public boolean checkRuleIsOwnKalaha(){
        return isOwnRow() && isKalaha();
    }

    public boolean isOwnRow(){
        final int currentPlayerRowIndex = this.currentPlayer.equals(Player.FIRST) ? 1 : 2 ;
        return currentPlayerRowIndex == rowIndex;
    }

    public boolean isKalaha() {
        return this.cellIndex == KALAHA_INDEX;
    }

    public boolean isLastStone() {
        return this.stones  == 1;
    }

}
