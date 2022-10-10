package ui;

import javafx.scene.control.TableView;

public class HistoryTableContainer extends TableView {
    private static final double MIN_WIDTH = 120.0;
    private static final double MIN_HEIGHT = 280.0;

    public HistoryTableContainer() {
        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
    }
}