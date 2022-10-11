package model;

import ui.BoardPane;

public class BoardState {
    private final Board board;
    private final boolean isBlackTurn;
    private final Position sourcePosition;
    private final Position targetPosition;

    public BoardState(Board board, boolean isBlackTurn, Position sourcePosition, Position targetPosition) {
        this.board = board.copy();
        this.isBlackTurn = isBlackTurn;
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
    }

    public Board getBoard() {
        return this.board;
    }

    public boolean getTurn() {
        return this.isBlackTurn;
    }

    public Position getSourcePosition() {
        return this.sourcePosition;
    }

    public Position getTargetPosition() {
        return this.targetPosition;
    }
}