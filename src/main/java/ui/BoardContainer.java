package ui;

import model.Model;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class BoardContainer extends GridPane {
    private static final double MIN_WIDTH = 420.0;
    private static final double MIN_HEIGHT = 420.0;

    private final Model model;
    
    private BoardPane boardPane;

    public BoardContainer(Model model) {
        this.model = model;
        this.boardPane = new BoardPane(model.getBoardSize());

        this.add(getRowLabels(model.getBoardSize()), 0, 0);
        this.add(getColumnLabels(model.getBoardSize()), 1, 1);
        this.add(boardPane, 1, 0);
        
        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
        this.setPrefWidth(MIN_WIDTH);
        this.setPrefHeight(MIN_HEIGHT);
    }

    private GridPane getRowLabels(int numOfRows) {
        GridPane rowLabels = new GridPane();
        rowLabels.setAlignment(Pos.CENTER);
        for (int i = 0; i < numOfRows; i++) {
            Label label = new Label(String.valueOf(i));
            label.setMinWidth(60);
            label.setMinHeight(60);
            rowLabels.addRow(i, label);
        }
        return rowLabels;
    }
    
    private GridPane getColumnLabels(int numOfColumns) {
        GridPane columnLabels = new GridPane();
        columnLabels.setAlignment(Pos.CENTER);
        for (int i = 0; i < numOfColumns; i++) {
            Label label = new Label(String.valueOf(i));
            label.setMinWidth(60);
            label.setMinHeight(60);
            columnLabels.addColumn(i, label);
        }
        return columnLabels;
    }
}