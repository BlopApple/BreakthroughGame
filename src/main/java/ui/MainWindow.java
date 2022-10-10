package ui;

import model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
/**
 * MainWindow is a GridPane that contains various containers for the main application.
 */
public class MainWindow extends GridPane {
    private static final double SIDEBAR_INSET_VALUE = 20.0;

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
        sideBar.setPadding(new Insets(0.0, 0.0, 0.0, SIDEBAR_INSET_VALUE));

        this.setAlignment(Pos.CENTER_LEFT);
        this.addColumn(0, boardContainer);
        this.addColumn(1, sideBar);
    }
}