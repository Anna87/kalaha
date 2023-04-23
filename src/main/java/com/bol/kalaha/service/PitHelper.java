package com.bol.kalaha.service;

import com.bol.kalaha.repository.model.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PitHelper {

    private static final int MAX_INDEX_OF_PIT = 5;

    public static Pit getCurrentPit(Board board, int pitIndex) {
        final Player currentPlayer = board.getNextPlayer();
        return currentPlayer.equals(Player.ONE) ?
                board.getRowOne().getPits().get(pitIndex) :
                board.getRowTwo().getPits().get(pitIndex);
    }

    public static int pollStones(Board board, int rowIndex, int pitIndex) {
        final Pit pit = getPit(board, rowIndex, pitIndex);
        final int stones = pit.getStones();
        pit.setStones(0);
        return stones;
    }

    public static void putOneStoneInPit(Board board, int rowIndex, int pitIndex) {
        final Pit pit = getPit(board, rowIndex, pitIndex);
        pit.setStones(pit.getStones() + 1);
    }

    public static void putOneStoneInKalaha(Board board, int rowIndex) {
        putStonesInKalaha(board, rowIndex, 1);
    }

    public static void putStonesInKalaha(Board board, int rowIndex, int stones) {
        addStonesToKalaha(stones, board.getRowByIndex(rowIndex));
    }

    public static boolean isEmptyOwnPit(Board board, RoundHolder roundHolder) {
        return roundHolder.isOwnRow()
                && getPit(board, roundHolder.getRowIndex(), roundHolder.getPitIndex()).getStones() == 0;
    }

    public static int getReversePitIndex(final int pitIndex) {
        return (MAX_INDEX_OF_PIT - pitIndex);
    }

    private static Pit getPit(Board board, int rowIndex, int pitIndex) {
        return board.getPit(rowIndex, pitIndex);
    }

    private static void addStonesToKalaha(int stones, Row row) {
        row.setKalahaStones(row.getKalahaStones() + stones);
    }

    public static void moveRemainingStonesToKalaha(Board board) {
        moveStonesToKalaha(board.getRowOne());
        moveStonesToKalaha(board.getRowTwo());
    }

    private static void moveStonesToKalaha(final Row row) {
        row.getPits()
           .forEach(pit -> {
               addStonesToKalaha(pit.getStones(), row);
               pit.setStones(0);
           });
    }

}
