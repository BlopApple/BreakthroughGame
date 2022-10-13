package ui;

import model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InputContainer extends GridPane {
    private static final int DEPTH_LIMIT = 6;

    private static final double MIN_WIDTH = 160.0;
    private static final double MIN_HEIGHT = 200.0;
    private static final double CONTAINER_INSET_VALUE = 20.0;
    private static final double CONTAINER_SPACING_VALUE = 5.0;

    private final Button newGameButton;
    private final Button undoMoveButton;
    private final Button redoMoveButton;
    private final Button saveGameButton;
    private final Button loadGameButton;

    private final ToggleGroup toggleGroup;

    public InputContainer() {
        this.toggleGroup = new ToggleGroup();

        VBox buttonBox = new VBox();
        this.newGameButton = new Button("New Game");
        this.undoMoveButton = new Button("Undo");
        this.redoMoveButton = new Button("Redo");
        this.saveGameButton = new Button("Save Game");
        this.loadGameButton = new Button("Load Game");
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(CONTAINER_SPACING_VALUE);

        buttonBox.getChildren().addAll(newGameButton, undoMoveButton, redoMoveButton, saveGameButton, loadGameButton);
        
        VBox radioButtonBox = new VBox();
        radioButtonBox.setAlignment(Pos.TOP_CENTER);
        radioButtonBox.getChildren().add(new Label("Depth"));
        for (int i = 0; i <= DEPTH_LIMIT; i++) {
            RadioButton radioButton = new RadioButton(String.valueOf(i));
            radioButton.setToggleGroup(this.toggleGroup);
            radioButtonBox.getChildren().add(radioButton);
        }
        
        this.add(buttonBox, 0, 0);
        this.add(radioButtonBox, 1, 0);
        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
        this.setPadding(new Insets(CONTAINER_INSET_VALUE));
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

    public int getToggledButton() {
        RadioButton toggledButton = (RadioButton) this.toggleGroup.getSelectedToggle();
        if (toggledButton == null) {
            return 0;
        }
        return Integer.parseInt(toggledButton.getText());
    }
}