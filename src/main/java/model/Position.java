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

    public Position(String positionString) throws Exception {
        positionString = positionString.substring(1, positionString.length() - 1);
        String[] positionValues = positionString.split(",");
        this.row = Integer.parseInt(positionValues[0]);
        this.col = Integer.parseInt(positionValues[1]);
        this.isEmpty = ((this.row == -1) && (this.col == -1));
    }

    public Position(Position position) {
        this.col = position.getColumn();
        this.row = position.getRow();
        this.isEmpty = ((this.row == -1) && (this.col == -1));
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