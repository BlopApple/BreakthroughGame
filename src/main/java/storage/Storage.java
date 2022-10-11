package storage;

import model.Board;
import model.BoardState;
import model.Model;
import model.Position;
import ui.BoardPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Storage {
    private static final String FILE_PATH = "breakthrough_save.txt";

    private final Model model;

    public Storage(Model model) {
        this.model = model;
    }

    public void saveGame() {
        try {
            File file = new File(FILE_PATH);
            if (!(file.exists())) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            Stack<BoardState> boardStates = this.model.getBoardStates();
            Stack<BoardState> flippedStack = new Stack<>();
            boardStates.forEach(state -> flippedStack.push(state));
            flippedStack.forEach(state -> {
                try {
                    fileWriter.write(state.getFormattedString());
                } catch (IOException exception) {
                    System.out.println(exception);
                }
            });

            fileWriter.close();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    public void loadGame(BoardPane boardPane) {
        try {
            File file = new File(FILE_PATH);
            if (!(file.exists())) {
                file.createNewFile();
            }
            Stack<BoardState> boardStates = new Stack<>();
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                BoardState boardState = processFormattedString(fileScanner.nextLine());
                boardStates.push(boardState);
            }
            model.loadBoardStates(boardStates, boardPane);
            fileScanner.close();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    private BoardState processFormattedString(String formattedString) {
        try {
            String[] arguments = formattedString.split("#");
            int boardSize = Integer.parseInt(arguments[0]);
            Board board = new Board(boardSize, arguments[1]);
            boolean isBlackTurn = arguments[2].equals("1");
            Position sourcePosition = new Position(arguments[3]);
            Position targetPosition = new Position(arguments[4]);

            return new BoardState(board, isBlackTurn, sourcePosition, targetPosition);

        } catch (Exception exception) {
            System.out.println(exception);
        }
        return new BoardState();
    }
}