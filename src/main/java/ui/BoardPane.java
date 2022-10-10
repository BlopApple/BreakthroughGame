package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardPane extends GridPane {
    private static final Color LIGHT_BLUE = Color.web("64B5F6");
    private static final Color DARK_BLUE = Color.web("0D47A1");

    public BoardPane(int boardSize, double tileSize) {
        this.add(getRowLabels(boardSize, tileSize), 0, 0);
        this.add(getColumnLabels(boardSize, tileSize), 1, 1);
        this.add(getGrid(boardSize, tileSize), 1, 0);
    }

    private GridPane getGrid(int boardSize, double tileSize) {
        GridPane grid = new GridPane();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Rectangle tile = new Rectangle();
                tile.setWidth(tileSize);
                tile.setHeight(tileSize);
                if ((row + col) % 2 == 0) {
                    tile.setFill(LIGHT_BLUE);
                } else {
                    tile.setFill(DARK_BLUE);
                }
                grid.add(tile, col, row);
            }
        }
        return grid;
    }

    private GridPane getRowLabels(int numOfRows, double tileSize) {
        GridPane rowLabels = new GridPane();
        rowLabels.setAlignment(Pos.CENTER);
        for (int i = 0; i < numOfRows; i++) {
            Label label = new Label(String.valueOf(i));
            label.setMinWidth(tileSize);
            label.setMinHeight(tileSize);
            label.setAlignment(Pos.CENTER);

            rowLabels.addRow(i, label);
        }
        return rowLabels;
    }
    
    private GridPane getColumnLabels(int numOfColumns, double tileSize) {
        GridPane columnLabels = new GridPane();
        columnLabels.setAlignment(Pos.CENTER);
        for (int i = 0; i < numOfColumns; i++) {
            Label label = new Label(String.valueOf(i));
            label.setMinWidth(tileSize);
            label.setMinHeight(tileSize);
            label.setAlignment(Pos.CENTER);

            columnLabels.addColumn(i, label);
        }
        return columnLabels;
    }
}