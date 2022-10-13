package model;

import storage.Storage;
import ui.BoardPane;
import java.util.Stack;
import java.util.function.Supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelManager implements Model {
    private final Stack<BoardState> boardStates;
    private final Stack<BoardState> undoneBoardStates;
    private final ObservableList<MovementPair> movementPairList;

    private Board board;
    private boolean isBlackTurn;
    private Position sourcePosition;
    private Position targetPosition;

    private Storage storage;
    private PlayerAi ai;

    private Supplier<Integer> toggleButtonSupplier;

    public ModelManager(int boardSize) {
        this.board = new Board(boardSize);
        this.isBlackTurn = true;
        this.sourcePosition = new Position();
        this.targetPosition = new Position();

        this.boardStates = new Stack<>();
        this.undoneBoardStates = new Stack<>();
        this.movementPairList = FXCollections.observableArrayList();

        this.storage = new Storage(this);
        this.ai = new PlayerAi();

        this.toggleButtonSupplier = (() -> 0);
    }

    @Override
    public void initializeBoard() {
        this.board.initialize();
        this.isBlackTurn = true;

        this.boardStates.clear();
        this.undoneBoardStates.clear();
        this.movementPairList.clear();
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
        this.undoneBoardStates.clear();
    }

    @Override
    public void addIdleEvent(int col, int row, BoardPane boardPane) {
        if (!this.sourcePosition.isEmpty()) {
            this.targetPosition = new Position(col, row);
            if (this.board.checkValidMove(this.isBlackTurn, this.sourcePosition, this.targetPosition)) {
                this.boardStates.push(new BoardState(this.board, this.isBlackTurn, this.sourcePosition, this.targetPosition));
                this.undoneBoardStates.clear();
                if (this.isBlackTurn) {
                    this.movementPairList.add(new MovementPair(new Movement(this.sourcePosition, this.targetPosition), new Movement()));
                } else {
                    MovementPair movementPair = this.movementPairList.remove(this.movementPairList.size() - 1);
                    this.movementPairList.add(new MovementPair(movementPair.getBlackMovement(), new Movement(this.sourcePosition, this.targetPosition)));
                }

                this.board.makeMove(this.sourcePosition, this.targetPosition);
                this.isBlackTurn = !this.isBlackTurn;
            } else {
                this.sourcePosition = new Position();
                this.targetPosition = new Position();
            }
        }

        if ((!isBlackTurn) && toggleButtonSupplier.get() != 0 && !(this.board.isGameEnd())) {
            Movement movement = ai.makeMove(isBlackTurn, this.board, toggleButtonSupplier.get());

            Position flippedSourcePosition = movement.getSourcePosition();
            Position flippedTargetPosition = movement.getTargetPosition();
            this.sourcePosition = new Position((this.getBoardSize() - 1) - flippedSourcePosition.getColumn(), (this.getBoardSize() - 1) - flippedSourcePosition.getRow());
            addIdleEvent((this.getBoardSize() - 1) - flippedTargetPosition.getColumn(), (this.getBoardSize() - 1) - flippedTargetPosition.getRow(), boardPane);
        }
        
        boardPane.refreshGrid();
    }

    @Override
    public void undoMove(BoardPane boardPane) {
        if (this.boardStates.size() > 0) {
            BoardState currentBoardState = this.boardStates.pop();
            this.undoneBoardStates.push(new BoardState(this.board.copy(), this.isBlackTurn, currentBoardState.getSourcePosition(), currentBoardState.getTargetPosition()));

            this.board = currentBoardState.getBoard().copy();
            this.isBlackTurn = currentBoardState.getTurn();
            this.sourcePosition = new Position();
            this.targetPosition = new Position();

            if (this.isBlackTurn) {
                this.movementPairList.remove(this.movementPairList.size() - 1);
            } else {
                MovementPair movementPair = this.movementPairList.remove(this.movementPairList.size() - 1);
                this.movementPairList.add(new MovementPair(movementPair.getBlackMovement(), new Movement()));
            }        
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
            this.sourcePosition = currentBoardState.getSourcePosition();
            this.targetPosition = currentBoardState.getTargetPosition();

            if (!this.isBlackTurn) {
                this.movementPairList.add(new MovementPair(new Movement(this.sourcePosition, this.targetPosition), new Movement()));
            } else {
                MovementPair movementPair = this.movementPairList.remove(this.movementPairList.size() - 1);
                this.movementPairList.add(new MovementPair(movementPair.getBlackMovement(), new Movement(this.sourcePosition, this.targetPosition)));
            }

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
            Stack<BoardState> flippedStack = new Stack<>();
            while (newBoardStates.size() != 0) {
                flippedStack.push(newBoardStates.pop());
            }
            while (flippedStack.size() != 0) {
                BoardState tempBoardState = flippedStack.pop();
                if (tempBoardState.getTurn()) {
                    this.movementPairList.add(new MovementPair(new Movement(tempBoardState.getSourcePosition(), tempBoardState.getTargetPosition()), new Movement()));
                } else {
                    MovementPair movementPair = this.movementPairList.remove(this.movementPairList.size() - 1);
                    this.movementPairList.add(new MovementPair(movementPair.getBlackMovement(), new Movement(tempBoardState.getSourcePosition(), tempBoardState.getTargetPosition())));
                }
            }
            boardPane.refreshGrid();
        }
    }

    @Override
    public ObservableList<MovementPair> getMovementPairList() {
        return this.movementPairList;
    }

    @Override
    public void setToggleButtonSupplier(Supplier<Integer> toggleButtoSupplier) {
        this.toggleButtonSupplier = toggleButtoSupplier;
    }
}