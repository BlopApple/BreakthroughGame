package model;

import ui.BoardPane;

public class BoardState {
    private final Board board;
    private final boolean isBlackTurn;
    private final Position sourcePosition;
    private final Position targetPosition;

    public BoardState() {
        this.board = new Board(0);
        this.isBlackTurn = false;
        this.sourcePosition = new Position();
        this.targetPosition = new Position();
    }

    public BoardState(Board board, boolean isBlackTurn, Position sourcePosition, Position targetPosition) {
        this.board = board.copy();
        this.isBlackTurn = isBlackTurn;
        this.sourcePosition = new Position(sourcePosition);
        this.targetPosition = new Position(targetPosition);
    }

    public Board getBoard() {
        return this.board;
    }

    public boolean getTurn() {
        return this.isBlackTurn;
    }

    public Position getSourcePosition() {
        return new Position(this.sourcePosition);
    }

    public Position getTargetPosition() {
        return new Position(this.targetPosition);
    }

    public String getFormattedString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.board.getFormattedString()).append("#");
        stringBuilder.append(isBlackTurn ? "1" : "0").append("#");
        stringBuilder.append(this.sourcePosition.toString()).append("#");
        stringBuilder.append(this.targetPosition.toString()).append("\n");

        return stringBuilder.toString();
    }
}