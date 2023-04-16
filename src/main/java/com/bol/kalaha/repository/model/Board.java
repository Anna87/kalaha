package com.bol.kalaha.repository.model;

import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class Board {

    private UUID id;
    private Row rowOne;
    private Row rowTwo;
    private boolean gameOver;
    private Player nextPlayer;

    public Board() {
        this.rowOne = new Row();
        this.rowTwo = new Row();
        this.nextPlayer = Player.FIRST;
    }

    public Row getRowByIndex(final int rowIndex) {
        return rowIndex == 1 ? this.rowOne : this.rowTwo;
    }

    public Cell getCell(final int rowIndex, final int cellIndex) {
        return getRowByIndex(rowIndex).getCells().get(cellIndex);
    }

    public boolean isGameOver() {
        return isRowEmpty(this.rowOne) || isRowEmpty(this.rowTwo);
    }

    public Optional<Player> getWinner() {
        return isGameOver()
                ? Optional.of(getWinnerPlayer())
                : Optional.empty();

    }

    private Player getWinnerPlayer() {
        return this.rowOne.getKalahaStones() > this.rowTwo.getKalahaStones()
                ? Player.FIRST
                : Player.SECOND;
    }

    private boolean isRowEmpty(final Row row) {
        return row.getCells().stream().allMatch(cell -> cell.getStones() == 0);
    }

}
