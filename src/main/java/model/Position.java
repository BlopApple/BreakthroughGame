package model;

public class Position {
    private final int col;
    private final int row;
    private final boolean isEmpty;

    public Position() {
        this.col = -1;
        this.row = -1;
        this.isEmpty = true;
    }

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
        this.isEmpty = false;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public int getColumn() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    @Override
    public String toString() {
        return "(" + this.row + "," + this.col + ")";
    }
}