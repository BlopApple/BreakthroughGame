package ui;

import model.Model;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
/**
 * MainWindow is a GridPane that contains various containers for the main application.
 */
public class MainWindow extends GridPane {
    private Model model;

    private BoardContainer boardContainer;
    private InputContainer inputContainer;
    private HistoryTableContainer historyTableContainer;

    public MainWindow(Model model) {
        this.model = model;
        this.boardContainer = new BoardContainer(model);
        this.inputContainer = new InputContainer();
        this.historyTableContainer = new HistoryTableContainer();

        VBox sideBar = new VBox();
        sideBar.setAlignment(Pos.TOP_CENTER);
        sideBar.getChildren().addAll(inputContainer, historyTableContainer);

        this.addColumn(0, boardContainer);
        this.addColumn(1, sideBar);
    }
}