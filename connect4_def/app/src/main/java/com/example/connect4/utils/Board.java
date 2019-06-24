package com.example.connect4.utils;

public class Board{

    private final int size;
    private final Cell[][] cells;
    private int moves;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.moves = 0;
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getTurn() {
        return (this.moves / 2) + 1;
    }

    public Position occupyCell (int column, Player player) {
        int row = firstEmptyRow(column);
        Cell c = new Cell(row,column,player);
        cells[row][column] = c;
        Position position = new Position(row,column);
        this.moves++;
        return position;
    }

    public boolean hasValidMoves() {
        for(int col = 0; col < size; col++) {
            if (firstEmptyRow(col) != -1) {
                return true;
            }
        }
        return false;
    }

    public int firstEmptyRow(int column) {
        int row;
        for(row = 0;row < this.size; row++) {
            if (cells[row][column] != null) {
                break;
            }
        }
        row--;
        return row;
    }

    public int maxConnected(Position position) {

        Player player = cells[position.getRow()][position.getColumn()].getPlayer();
        Position pos = position;

        int max = 0;
        for (Direction dir : Direction.ALL) {
            pos = pos.move(dir);
            int length = 0;
            while (pos.getRow() < size && pos.getColumn() < size &&
                    pos.getRow() >= 0 && pos.getColumn() >= 0 &&
                    length < 4 &&cells[pos.getRow()][pos.getColumn()] != null &&
                    player.getColour() == cells[pos.getRow()][pos.getColumn()].getPlayer().getColour()) {

                length = Position.pathLength(position,pos);
                if (length > max){
                    max = length;
                }
                pos = pos.move(dir);
            }
        }
        return max;
    }

    public Player winner() {
        for (int row = 0 ; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (cells[row][col] != null) {
                    Position position = new Position(row,col);
                    Position pos = position;
                    int length = 0;
                    for (Direction dir : Direction.ALL) {
                        pos =  position.move(dir);
                        while (pos.getRow() < size && pos.getColumn() < size &&
                                pos.getRow() >= 0 && pos.getColumn() >= 0 &&
                                length < 4 && cells[pos.getRow()][pos.getColumn()] != null &&
                                cells[pos.getRow()][pos.getColumn()].getPlayer().getColour() ==
                                        cells[position.getRow()][position.getColumn()].getPlayer().getColour()) {
                            length = Position.pathLength(position,pos);
                            pos = pos.move(dir);
                        }
                        if (length == 4) {
                            return (cells[position.getRow()][position.getColumn()].getPlayer());
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean draw() {
        return winner() == null & this.moves == (size * size);
    }
}