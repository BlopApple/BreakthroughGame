package model;

import ui.BoardPane;

public interface Model {
    public void initializeBoard();
    public int getBoardSize();
    public char getPiece(int col, int row);
    public char getTurn();
    public boolean isGameEnd();
    public boolean hasMoveEvent();
    public void addMoveEvent(int col, int row);
    public void addIdleEvent(int col, int row, BoardPane boardPane);
}