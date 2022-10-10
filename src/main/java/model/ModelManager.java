package model;

public class ModelManager implements Model {
    private final int boardSize;

    public ModelManager(int boardSize) {
        this.boardSize = boardSize;
    }

    @Override
    public int getBoardSize() {
        return this.boardSize;
    }
}