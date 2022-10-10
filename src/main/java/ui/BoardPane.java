package ui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardPane extends GridPane {
    private static final Color LIGHT_BLUE = Color.web("64B5F6");
    private static final Color DARK_BLUE = Color.web("0D47A1");

    public BoardPane(int boardSize) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Rectangle tile = new Rectangle();
                tile.setWidth(60);
                tile.setHeight(60);
                if ((row + col) % 2 == 0) {
                    tile.setFill(LIGHT_BLUE);
                } else {
                    tile.setFill(DARK_BLUE);
                }

                this.add(tile, col, row);
            }
        }
    }
}