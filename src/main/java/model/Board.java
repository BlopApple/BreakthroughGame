package model;

public class Board {
    private final int boardSize;
    private char[][] internalBoard;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.internalBoard = new char[boardSize][boardSize];
    }

    public void initialize() {
        for (int row = 0; row < this.boardSize; row ++) {
            for (int col = 0; col < this.boardSize; col++) {
                if (row == 0 || row == 1) {
                    this.internalBoard[row][col] = 'B';
                } else if (row == (this.boardSize - 2) || row == (this.boardSize - 1)) {
                    this.internalBoard[row][col] = 'W';
                } else {
                    this.internalBoard[row][col] = '-';
                }
            }
        }
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public char getPiece(Position position) {
        return this.internalBoard[position.getRow()][position.getColumn()];
    }

    public void setPiece(Position position, char piece) {
        this.internalBoard[position.getRow()][position.getColumn()] = piece;
    }

    public boolean checkValidMove(boolean isBlackTurn, Position sourcePosition, Position targetPosition) {
        if (isBlackTurn) {
            if (sourcePosition.getColumn() == targetPosition.getColumn()) {
                return ((targetPosition.getRow() - sourcePosition.getRow()) == 1) && (getPiece(targetPosition) == '-');
            } else if (Math.abs(sourcePosition.getColumn() - targetPosition.getColumn()) == 1) {
                return ((targetPosition.getRow() - sourcePosition.getRow()) == 1) && (getPiece(targetPosition) == 'W');
            } else {
                return false;
            }
        } else {
            if (sourcePosition.getColumn() == targetPosition.getColumn()) {
                return ((targetPosition.getRow() - sourcePosition.getRow()) == -1) && (getPiece(targetPosition) == '-');
            } else if (Math.abs(sourcePosition.getColumn() - targetPosition.getColumn()) == 1) {
                return ((targetPosition.getRow() - sourcePosition.getRow()) == -1) && (getPiece(targetPosition) == 'B');
            } else {
                return false;
            }
        }
    }

    public void makeMove(Position sourcePosition, Position targetPosition) {
        char tempPiece = getPiece(sourcePosition);
        setPiece(sourcePosition, '-');
        setPiece(targetPosition, tempPiece);
    }
}