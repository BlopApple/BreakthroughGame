package model;

import storage.Storage;
import ui.BoardPane;
import java.util.Stack;
import java.util.function.Supplier;

import javafx.collections.ObservableList;

public interface Model {
    public void initializeBoard();
    public int getBoardSize();
    public char getPiece(int col, int row);
    public char getTurn();
    public boolean isGameEnd();
    public boolean hasMoveEvent();
    public void addMoveEvent(int col, int row);
    public void addIdleEvent(int col, int row, BoardPane boardPane);

    public void undoMove(BoardPane boardPane);
    public void redoMove(BoardPane boardPane);

    public Storage getStorage();
    public Stack<BoardState> getBoardStates();
    public void loadBoardStates(Stack<BoardState> newBoardStates, BoardPane boardPane);

    public ObservableList<MovementPair> getMovementPairList();

    public void setToggleButtonSupplier(Supplier<Integer> toggleButtonSupplier);
}