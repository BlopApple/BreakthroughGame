package model;

import storage.Storage;
import ui.BoardPane;
import java.util.Stack;

public class ModelManager implements Model {
    private final Stack<BoardState> boardStates;
    private final Stack<BoardState> undoneBoardStates;

    private Board board;
    private boolean isBlackTurn;
    private Position sourcePosition;
    private Position targetPosition;

    private Storage storage;

    public ModelManager(int boardSize) {
        this.board = new Board(boardSize);
        this.isBlackTurn = true;
        this.sourcePosition = new Position();
        this.targetPosition = new Position();

        this.boardStates = new Stack<>();
        this.undoneBoardStates = new Stack<>();

        this.storage = new Storage(this);
    }

    @Override
    public void initializeBoard() {
        this.board.initialize();
        this.isBlackTurn = true;

        this.boardStates.clear();
        this.undoneBoardStates.clear();
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
    public boolean isGameEnd() {
        return this.board.isGameEnd();
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
                this.boardStates.push(new BoardState(this.board.copy(), this.isBlackTurn, this.sourcePosition, this.targetPosition));
                this.undoneBoardStates.clear();

                this.board.makeMove(sourcePosition, targetPosition);
                this.isBlackTurn = !this.isBlackTurn;

                boardPane.refreshGrid();
            } else {
                this.sourcePosition = new Position();
                this.targetPosition = new Position();
            }
        }
    }

    @Override
    public void undoMove(BoardPane boardPane) {
        if (this.boardStates.size() > 0) {
            BoardState currentBoardState = this.boardStates.pop();
            this.undoneBoardStates.push(new BoardState(this.board.copy(), this.isBlackTurn, this.sourcePosition, this.targetPosition));

            this.board = currentBoardState.getBoard().copy();
            this.isBlackTurn = currentBoardState.getTurn();
            this.sourcePosition = new Position();
            this.targetPosition = new Position();

            boardPane.refreshGrid();
        }
    }

    @Override
    public void redoMove(BoardPane boardPane) {
        if (this.undoneBoardStates.size() > 0) {
            BoardState currentBoardState = this.undoneBoardStates.pop();
            this.boardStates.push(new BoardState(this.board.copy(), this.isBlackTurn, this.sourcePosition, this.targetPosition));

            this.board = currentBoardState.getBoard().copy();
            this.isBlackTurn = currentBoardState.getTurn();
            this.sourcePosition = new Position();
            this.targetPosition = new Position();

            boardPane.refreshGrid();
        }
    }

    @Override
    public Storage getStorage() {
        return this.storage;
    }

    @Override
    public Stack<BoardState> getBoardStates() {
        Stack<BoardState> newBoardStates = new Stack<>();
        newBoardStates.addAll(this.boardStates);
        newBoardStates.push(new BoardState(this.board.copy(), this.isBlackTurn, new Position(), new Position()));
        return newBoardStates;
    }

    @Override
    public void loadBoardStates(Stack<BoardState> newBoardStates, BoardPane boardPane) {
        if (newBoardStates.size() > 0) {
            BoardState currentBoardState = newBoardStates.pop();

            this.board = currentBoardState.getBoard().copy();
            this.isBlackTurn = currentBoardState.getTurn();
            this.sourcePosition = new Position();
            this.targetPosition = new Position();

            this.boardStates.addAll(newBoardStates);
            boardPane.refreshGrid();
        }
    }
}