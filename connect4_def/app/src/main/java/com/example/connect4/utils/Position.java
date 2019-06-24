package com.example.connect4.utils;

public class Position {
    private final int row;
    private final int column;

    Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int getRow() {
        return this.row;
    }

    int getColumn() {
        return this.column;
    }

    Position move(Direction direction) {
        int row = this.row + direction.getChangeInRow();
        int column = this.column + direction.getChangeInColumn();
        return new Position(row, column);
    }

    boolean isEqualTo(Position other) {
        return this.row == other.row && this.column == other.column;
    }

    static int pathLength(Position pos1, Position pos2) {
        int len = 0;
        if(pos1.row == pos2.row){
            len = Math.abs(pos1.column - pos2.column); // guarda el valor absolut
        }else if(pos1.column == pos2.column){
            len = Math.abs(pos1.row - pos2.row);
        }else{
            len = Math.abs(pos1.row - pos2.row);
        }
        len++;
        return len;
    }
}

