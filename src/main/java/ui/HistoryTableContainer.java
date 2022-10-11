package ui;

import model.Model;
import model.MovementPair;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HistoryTableContainer extends TableView<MovementPair> {
    private static final double MIN_WIDTH = 160.0;
    private static final double MIN_HEIGHT = 280.0;

	private TableColumn<MovementPair, String> blackColumn;
	private TableColumn<MovementPair, String> whiteColumn;

    public HistoryTableContainer(Model model) {
        this.blackColumn = new TableColumn<>("Black");
        this.whiteColumn = new TableColumn<>("White");
		this.blackColumn.setSortable(false);
        this.whiteColumn.setSortable(false);
		this.blackColumn.setCellValueFactory(movementPair -> 
                new SimpleStringProperty(movementPair.getValue().getBlackMovement().toString()));
		this.whiteColumn.setCellValueFactory(movementPair -> 
                new SimpleStringProperty(movementPair.getValue().getWhiteMovement().toString()));

        this.getColumns().add(this.blackColumn);
        this.getColumns().add(this.whiteColumn);
        this.setItems(model.getMovementPairList());

        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
    }
}