package ui;

import model.Model;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BoardContainer extends GridPane {
    private static final double MIN_WIDTH = 420.0;
    private static final double MIN_HEIGHT = 420.0;
    private static final double CONTAINER_INSET_VALUE = 20.0;

    private final Model model;
    
    private BoardPane boardPane;

    public BoardContainer(Model model) {
        this.model = model;
        this.boardPane = new BoardPane(model, MIN_WIDTH / (model.getBoardSize() + 1));

        this.getChildren().add(boardPane);
        
        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
        this.setPadding(new Insets(CONTAINER_INSET_VALUE, 0.0, 0.0, 0.0));
    }

    public void initialize(Stage stage) {
        this.boardPane.setStage(stage);
        this.boardPane.refreshGrid();
    }
}