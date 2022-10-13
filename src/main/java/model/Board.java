package model;

public class Board {
    private final int boardSize;
    private char[][] internalBoard;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.internalBoard = new char[boardSize][boardSize];
    }

    public Board(int boardSize, String boardString) {
        this.boardSize = boardSize;
        this.internalBoard = new char[boardSize][boardSize];
        
        for (int row = 0; row < this.boardSize; row ++) {
            for (int col = 0; col < this.boardSize; col++) {
                this.internalBoard[row][col] = boardString.charAt(row * boardSize + col);
            }
        }
    }
    
    private Board(Board board) {
        this.boardSize = board.boardSize;
        this.internalBoard = new char[board.boardSize][board.boardSize];

        for (int row = 0; row < this.boardSize; row ++) {
            for (int col = 0; col < this.boardSize; col++) {
                this.internalBoard[row][col] = board.internalBoard[row][col];
            }
        }
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

    public Board copy() {
        return new Board(this);
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
                return ((targetPosition.getRow() - sourcePosition.getRow()) == 1) && (getPiece(targetPosition) != 'B');
            } else {
                return false;
            }
        } else {
            if (sourcePosition.getColumn() == targetPosition.getColumn()) {
                return ((targetPosition.getRow() - sourcePosition.getRow()) == -1) && (getPiece(targetPosition) == '-');
            } else if (Math.abs(sourcePosition.getColumn() - targetPosition.getColumn()) == 1) {
                return ((targetPosition.getRow() - sourcePosition.getRow()) == -1) && (getPiece(targetPosition) != 'W');
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

    public boolean isGameEnd() {
        for (int col = 0; col < this.boardSize; col++) {
            if (this.internalBoard[0][col] == 'W' || this.internalBoard[this.boardSize - 1][col] == 'B') {
                return true;
            }
        }
        return false;
    }

    public String getFormattedString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.valueOf(this.boardSize)).append("#");
        for (int row = 0; row < this.boardSize; row ++) {
            for (int col = 0; col < this.boardSize; col++) {
                stringBuilder.append(String.valueOf(this.internalBoard[row][col]));
            }
        }
        return stringBuilder.toString();
    }
}