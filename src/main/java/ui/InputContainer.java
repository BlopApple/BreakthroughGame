package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class InputContainer extends VBox {
    private static final double MIN_WIDTH = 120.0;
    private static final double MIN_HEIGHT = 140.0;
    
    public InputContainer() {
        for (int i = 0; i < 5; i++) {
            this.getChildren().add(new Button("Dummy"));
        }
        
        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
    }
}