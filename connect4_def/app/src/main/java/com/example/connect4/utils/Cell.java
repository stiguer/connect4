package com.example.connect4.utils;

public class Cell{
    private final int row;
    private final int col;
    private final Player player;

    public Cell(int row, int col, Player player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }

}
