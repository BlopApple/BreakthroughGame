package ui;

import model.Position;
import model.PositionPair;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HistoryTableContainer extends TableView<PositionPair> {
    private static final double MIN_WIDTH = 160.0;
    private static final double MIN_HEIGHT = 280.0;

	private TableColumn<PositionPair, String> blackColumn;
	private TableColumn<PositionPair, String> whiteColumn;

    private final Model model;

    public HistoryTableContainer(Model model) {
        this.model = model;

        this.blackColumn = new TableColumn<>("Black");
        this.whiteColumn = new TableColumn<>("White");
		this.blackColumn.setSortable(false);
        this.whiteColumn.setSortable(false);
		this.blackColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().toString()));
		this.whiteColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().toString()));

        this.getColumns().add(this.blackColumn);
        this.getColumns().add(this.whiteColumn);

        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
    }

    public void update(boolean isBlackTurn, Position sourcePosition, Position targetPosition) {
        this.getItems().add(new PositionPair(sourcePosition, targetPosition));
    }
}