package ui;

import model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InputContainer extends VBox {
    private static final double MIN_WIDTH = 120.0;
    private static final double MIN_HEIGHT = 180.0;
    private static final double CONTAINER_INSET_VALUE = 20.0;
    private static final double CONTAINER_SPACING_VALUE = 5.0;
    
    private final Button newGameButton;
    private final Button undoMoveButton;
    private final Button redoMoveButton;
    private final Button saveGameButton;
    private final Button loadGameButton;

    public InputContainer() {
        this.newGameButton = new Button("New Game");
        this.undoMoveButton = new Button("Undo");
        this.redoMoveButton = new Button("Redo");
        this.saveGameButton = new Button("Save Game");
        this.loadGameButton = new Button("Load Game");
        
        this.getChildren().addAll(newGameButton, undoMoveButton, redoMoveButton, saveGameButton, loadGameButton);

        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
        this.setPadding(new Insets(CONTAINER_INSET_VALUE));
        this.setSpacing(CONTAINER_SPACING_VALUE);
    }

    public void initializeEventHandlers(Model model, BoardContainer boardContainer, Stage stage) {
        this.newGameButton.setOnMouseClicked((event) -> {
            model.initializeBoard();
            boardContainer.initialize(stage);
        });

        this.undoMoveButton.setOnMouseClicked((event) -> {
            model.undoMove(boardContainer.getBoardPane());
        });

        this.redoMoveButton.setOnMouseClicked((event) -> {
            model.redoMove(boardContainer.getBoardPane());
        });

        this.saveGameButton.setOnMouseClicked((event) -> {
            model.getStorage().saveGame();
        });

        this.loadGameButton.setOnMouseClicked((event) -> {
            model.getStorage().loadGame(boardContainer.getBoardPane());
        });
    }
}