package model;

import ui.BoardPane;

public class ModelManager implements Model {
    private final Board board;
    private boolean isBlackTurn;
    private Position sourcePosition;
    private Position targetPosition;

    public ModelManager(int boardSize) {
        this.board = new Board(boardSize);
        this.isBlackTurn = true;
        this.sourcePosition = new Position();
        this.targetPosition = new Position();
        
        this.board.initialize();
    }

    @Override
    public int getBoardSize() {
        return this.board.getBoardSize();
    }
    
    @Override 
    public char getPiece(int col, int row) {
        return this.board.getPiece(new Position(col, row));
    }

    @Override
    public char getTurn() {
        return this.isBlackTurn ? 'B' : 'W';
    }

    @Override
    public boolean hasMoveEvent() {
        return !(this.sourcePosition.isEmpty());
    }

    @Override
    public void addMoveEvent(int col, int row) {
        this.sourcePosition = new Position(col, row);
    }

    @Override
    public void addIdleEvent(int col, int row, BoardPane boardPane) {
        if (!this.sourcePosition.isEmpty()) {
            this.targetPosition = new Position(col, row);
            if (this.board.checkValidMove(this.isBlackTurn, sourcePosition, targetPosition)) {
                this.board.makeMove(sourcePosition, targetPosition);
                this.isBlackTurn = !this.isBlackTurn;
                boardPane.refreshGrid();
            } else {
                this.sourcePosition = new Position();
                this.targetPosition = new Position();
            }
        }
    }
}