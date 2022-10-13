package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PlayerAi {
    private static final double MAX_VALUE = 1000000.0;
    private static final double MIN_VALUE = -MAX_VALUE;
    public PlayerAi() {
    }

    private int[] compressBoard(boolean isBlackTurn, Board board) {
        int boardSize = board.getBoardSize();
        int[] compressedBoard = new int[boardSize * boardSize];
        for (int row = 0; row < boardSize;  row++) {
            for (int col = 0; col < boardSize; col++) {
                if (isBlackTurn) {
                    if (board.getPiece(new Position(col, row)) == 'B') {
                        compressedBoard[row * boardSize + col] = 1;
                    } else if (board.getPiece(new Position(col, row)) == 'W') {
                        compressedBoard[row * boardSize + col] = -1;
                    } else {
                        compressedBoard[row * boardSize + col] = 0;
                    }
                } else {
                    if (board.getPiece(new Position(col, row)) == 'W') {
                        compressedBoard[(boardSize * boardSize - 1) - (row * boardSize + col)] = 1;
                    } else if (board.getPiece(new Position(col, row)) == 'B') {
                        compressedBoard[(boardSize * boardSize - 1) - (row * boardSize + col)] = -1;
                    } else {
                        compressedBoard[(boardSize * boardSize - 1) - (row * boardSize + col)] = 0;
                    }
                }
            }
        }
        return compressedBoard;
    }

    private double getUtilityScore(int[] board) {
        return 0.0;
    }

    private ScoreMovementPair maxAlphaBeta(int depth, Node node, double alpha, double beta) {
        if (node.isTerminal() || depth == 0) {
            return new ScoreMovementPair(getUtilityScore(node.getBoard()), node.getMovement());
        } else {
            List<Node> children = node.getChildren(true);
            double currentScore = MIN_VALUE;
            Movement currentMove = children.get(0).getMovement();
            
            System.out.println("in max: " + currentMove);

            for (Node childNode : children) {
                ScoreMovementPair scoreMovementPair = minAlphaBeta(depth - 1, childNode, alpha, beta);
                double childScore = scoreMovementPair.getScore();
                if (childScore > currentScore) {
                    currentScore = childScore;
                    currentMove = childNode.getMovement();
                }

                if (currentScore >= beta) {
                    return new ScoreMovementPair(currentScore, currentMove);
                } else {
                    if (currentScore > alpha) {
                        alpha = currentScore;
                    }
                }
            }
            return new ScoreMovementPair(currentScore, currentMove);
        }
    }

    private ScoreMovementPair minAlphaBeta(int depth, Node node, double alpha, double beta) {
        if (node.isTerminal() || depth == 0) {
            return new ScoreMovementPair(getUtilityScore(node.getBoard()), node.getMovement());
        } else {
            List<Node> children = node.getChildren(false);
            double currentScore = MAX_VALUE;
            Movement currentMove = children.get(0).getMovement();
            for (Node childNode : children) {
                ScoreMovementPair scoreMovementPair = maxAlphaBeta(depth - 1, childNode, alpha, beta);
                double childScore = scoreMovementPair.getScore();
                if (childScore < currentScore) {
                    currentScore = childScore;
                    currentMove = childNode.getMovement();
                }

                if (currentScore <= alpha) {
                    return new ScoreMovementPair(currentScore, currentMove);
                } else {
                    if (currentScore < beta) {
                        beta = currentScore;
                    }
                }
            }
            return new ScoreMovementPair(currentScore, currentMove);
        }
    }

    private Movement minimaxAlphaBeta(Node node, int depth) {
        return maxAlphaBeta(depth, node, MIN_VALUE, MAX_VALUE).getMovement();
    }

    public Movement makeMove(boolean isBlackTurn, Board board, int depth) {
        return minimaxAlphaBeta(new Node(compressBoard(isBlackTurn, board), new Movement()), depth);
    }

    private class ScoreMovementPair {
        private final double score;
        private final Movement movement;

        ScoreMovementPair(double score, Movement movement) {
            this.score = score;
            this.movement = movement;
        }

        private double getScore() {
            return this.score;
        }

        private Movement getMovement() {
            return this.movement;
        }
    }

    private class Node {
        private final int[] board;
        private final Movement movement;

        Node(int[] board, Movement movement) {
            this.board = board;
            this.movement = movement;
        }

        private int[] getBoard() {
            int[] newBoard = new int[this.board.length];
            for (int i = 0; i < this.board.length; i++) {
                newBoard[i] = this.board[i];
            }
            return newBoard;
        }

        private Movement getMovement() {
            return new Movement(this.movement);
        }

        private boolean isTerminal() {
            int nRow = 6;
            int nCol = 6;
            boolean flag = IntStream.range(0, nCol).filter(i -> ((this.board[(nRow - 1) * nCol + i] == 1) || (board[i] == -1))).findAny().isPresent();

            int wCount = 0;
            int bCount = 0;
            for (int i = 0; i < this.board.length; i++) {
                if (this.board[i] == 1) {
                    bCount++;
                } else if (this.board[i] ==  -1) {
                    wCount++;
                }
            }
                
            if (wCount == 0 || bCount == 0) {
                flag = true;
            }
            return flag;
        }

        private List<Node> getChildren(boolean isBlackTurn) {
            List<Node> children = new ArrayList<>();
            int nCol = 6;
            for (int i = 0; i < this.board.length; i++) {
                if ((this.board[i] == 1) && isBlackTurn) {
                    int spaceForward = i + nCol;
                    int spaceDiagonalLeft = i + nCol - 1;
                    int spaceDiagonalRight = i + nCol + 1;
                    // forward move
                    if ((spaceForward < this.board.length) && (this.board[spaceForward] == 0)) {
                        int[] newBoard = getBoard();
                        newBoard[spaceForward] = 1;
                        newBoard[i] = 0;
                        children.add(new Node(newBoard, new Movement(new Position(i % nCol, i / nCol), new Position(spaceForward % nCol, spaceForward / nCol))));
                    }
                    // diagonal move left
                    if ((spaceDiagonalLeft < this.board.length) && (this.board[spaceDiagonalLeft] != 1) && ((spaceDiagonalLeft / nCol) == (i / nCol + 1))) {
                        int[] newBoard = getBoard();
                        newBoard[spaceDiagonalLeft] = 1;
                        newBoard[i] = 0;
                        children.add(new Node(newBoard, new Movement(new Position(i % nCol, i / nCol), new Position(spaceDiagonalLeft % nCol, spaceDiagonalLeft / nCol))));
                    }
                    // diagonal move right
                    if ((spaceDiagonalRight < this.board.length) && (this.board[spaceDiagonalRight] != 1) && ((spaceDiagonalRight / nCol) == (i / nCol + 1))) {
                        int[] newBoard = getBoard();
                        newBoard[spaceDiagonalRight] = 1;
                        newBoard[i] = 0;
                        children.add(new Node(newBoard, new Movement(new Position(i % nCol, i / nCol), new Position(spaceDiagonalRight % nCol, spaceDiagonalRight / nCol))));
                    }
                } 

                if ((this.board[i] == -1) && !isBlackTurn) {
                    int spaceForward = i - nCol;
                    int spaceDiagonalLeft = i - nCol - 1;
                    int spaceDiagonalRight = i - nCol + 1;
                    // forward move
                    if ((spaceForward >= 0) && (this.board[spaceForward] == 0)) {
                        int[] newBoard = getBoard();
                        newBoard[spaceForward] = -1;
                        newBoard[i] = 0;
                        children.add(new Node(newBoard, new Movement(new Position(i % nCol, i / nCol), new Position(spaceForward % nCol, spaceForward / nCol))));
                    }
                    // diagonal move left
                    if ((spaceDiagonalLeft >= 0) && (this.board[spaceDiagonalLeft] != -1) && ((spaceDiagonalLeft / nCol) == (i / nCol - 1))) {
                        int[] newBoard = getBoard();
                        newBoard[spaceDiagonalLeft] = -1;
                        newBoard[i] = 0;
                        children.add(new Node(newBoard, new Movement(new Position(i % nCol, i / nCol), new Position(spaceDiagonalLeft % nCol, spaceDiagonalLeft / nCol))));
                    }
                    // diagonal move right
                    if ((spaceDiagonalRight >= 0) && (this.board[spaceDiagonalRight] != -1) && ((spaceDiagonalRight / nCol) == (i / nCol - 1))) {
                        int[] newBoard = getBoard();
                        newBoard[spaceDiagonalRight] = -1;
                        newBoard[i] = 0;
                        children.add(new Node(newBoard, new Movement(new Position(i % nCol, i / nCol), new Position(spaceDiagonalRight % nCol, spaceDiagonalRight / nCol))));
                    }
                }
            }
            return children;
        }
    }
}
