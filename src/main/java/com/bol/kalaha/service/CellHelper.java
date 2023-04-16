package com.bol.kalaha.service;

import com.bol.kalaha.repository.model.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CellHelper {

    private static final int MAX_INDEX_OF_CELL = 5;

    public static Cell getCurrentCell(Board board, int cellIndex) {
        final Player currentPlayer = board.getNextPlayer();
        return currentPlayer.equals(Player.FIRST) ?
                board.getRowOne().getCells().get(cellIndex) :
                board.getRowTwo().getCells().get(cellIndex);
    }

    public static int pollStones(Board board, int rowIndex, int cellIndex) {
        final Cell cell = getCell(board, rowIndex, cellIndex);
        final int stones = cell.getStones();
        cell.setStones(0);
        return stones;
    }

    public static void putOneStoneInCell(Board board, int rowIndex, int cellIndex) {
        final Cell cell = getCell(board, rowIndex, cellIndex);
        cell.setStones(cell.getStones() + 1);
    }

    public static void putOneStoneInKalaha(Board board, int rowIndex) {
        putStonesInKalaha(board, rowIndex, 1);
    }

    public static void putStonesInKalaha(Board board, int rowIndex, int stones) {
        addStonesToKalaha(stones, board.getRowByIndex(rowIndex));
    }

    public static boolean checkRuleEmptyOwnCell(Board board, RoundHolder roundHolder) {
        return roundHolder.isOwnRow()
                && getCell(board, roundHolder.getRowIndex(), roundHolder.getCellIndex()).getStones() == 0;
    }

    public static int getReverseCellIndex(final int cellIndex) {
        return (MAX_INDEX_OF_CELL - cellIndex);
    }

    private static Cell getCell(Board board, int rowIndex, int cellIndex) {
        return board.getCell(rowIndex, cellIndex);
    }

    private static void addStonesToKalaha(int stones, Row row) {
        row.setKalahaStones(row.getKalahaStones() + stones);
    }

    public static void moveRemainingStonesToKalaha(Board board) {
        moveStonesToKalaha(board.getRowOne());
        moveStonesToKalaha(board.getRowTwo());
    }

    private static void moveStonesToKalaha(final Row row) {
        row.getCells()
           .forEach(cell -> {
               addStonesToKalaha(cell.getStones(), row);
               cell.setStones(0);
           });
    }

}
