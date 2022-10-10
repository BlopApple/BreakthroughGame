package ui;

import model.Model;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class BoardPane extends GridPane {
    private static final Color LIGHT_BLUE = Color.web("64B5F6");
    private static final Color DARK_BLUE = Color.web("0D47A1");

    private final Model model;
    private final double tileSize;

    private final GridPane rowLabels;
    private final GridPane columnLabels;

    public BoardPane(Model model, double tileSize) {
        this.model = model;
        this.tileSize = tileSize;
        this.rowLabels = getRowLabels();
        this.columnLabels = getColumnLabels();

        this.add(rowLabels, 0, 0);
        this.add(columnLabels, 1, 1);
        this.add(getGrid(), 1, 0);
    }

    private GridPane getGrid() {
        int boardSize = model.getBoardSize();
	    Rectangle highlightTile = new Rectangle(this.tileSize, this.tileSize, Color.WHEAT);
        highlightTile.setOpacity(0.4);

        GridPane grid = new GridPane();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Rectangle tile = new Rectangle(this.tileSize, this.tileSize);
                if ((row + col) % 2 == 0) {
                    tile.setFill(LIGHT_BLUE);
                } else {
                    tile.setFill(DARK_BLUE);
                }
                grid.add(tile, col, row);

                Circle piece = new Circle();
                piece.setRadius(this.tileSize / 2.5);

                final int currCol = col;
                final int currRow = row;
                if (model.getPiece(col, row) == 'B') {
                    piece.setFill(Color.BLACK);
                    if (model.getTurn() == 'B') {
                        piece.setOnMouseClicked((event) -> {
                            if (model.hasMoveEvent()) {                            
                                grid.getChildren().remove(highlightTile);
                            }
						    model.addMoveEvent(currCol, currRow);
                            grid.add(highlightTile, currCol, currRow);
					    });
                    } else {
                        piece.setOnMouseClicked((event) -> {
                            model.addIdleEvent(currCol, currRow, this);
                            grid.getChildren().remove(highlightTile);
					    });
                    }
                } else if (model.getPiece(col, row) == 'W') {
                    piece.setFill(Color.WHITE);
                    if (model.getTurn() == 'W') {
                        piece.setOnMouseClicked((event) -> {
                            if (model.hasMoveEvent()) {                            
                                grid.getChildren().remove(highlightTile);
                            }
						    model.addMoveEvent(currCol, currRow);
                            grid.add(highlightTile, currCol, currRow);
					    });
                    } else {
                        piece.setOnMouseClicked((event) -> {
                            model.addIdleEvent(currCol, currRow, this);
                            grid.getChildren().remove(highlightTile);
					    });
                    }
                } else {
                    tile.setOnMouseClicked((event) -> {
                        model.addIdleEvent(currCol, currRow, this);
                        grid.getChildren().remove(highlightTile);
					});
                    continue;
                }
                grid.add(piece, col, row);
                grid.setHalignment(piece, HPos.CENTER);
                grid.setValignment(piece, VPos.CENTER);
            }
        }
        return grid;
    }

    private GridPane getRowLabels() {
        int numOfRows = model.getBoardSize();
        GridPane newRowLabels = new GridPane();
        newRowLabels.setAlignment(Pos.CENTER);
        for (int i = 0; i < numOfRows; i++) {
            Label label = new Label(String.valueOf(i));
            label.setMinWidth(this.tileSize);
            label.setMinHeight(this.tileSize);
            label.setAlignment(Pos.CENTER);

            newRowLabels.addRow(i, label);
        }
        return newRowLabels;
    }
    
    private GridPane getColumnLabels() {
        int numOfColumns = model.getBoardSize();
        GridPane newColumnLabels = new GridPane();
        newColumnLabels.setAlignment(Pos.CENTER);
        for (int i = 0; i < numOfColumns; i++) {
            Label label = new Label(String.valueOf(i));
            label.setMinWidth(this.tileSize);
            label.setMinHeight(this.tileSize);
            label.setAlignment(Pos.CENTER);

            newColumnLabels.addColumn(i, label);
        }
        return newColumnLabels;
    }

    public void refreshGrid() {
        this.getChildren().clear();
        
        this.add(rowLabels, 0, 0);
        this.add(columnLabels, 1, 1);
        this.add(getGrid(), 1, 0);
    }
}