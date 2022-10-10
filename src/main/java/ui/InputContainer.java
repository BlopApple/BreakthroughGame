package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class InputContainer extends VBox {
    private static final double MIN_WIDTH = 120.0;
    private static final double MIN_HEIGHT = 180.0;
    private static final double CONTAINER_INSET_VALUE = 20.0;
    private static final double CONTAINER_SPACING_VALUE = 5.0;
    
    public InputContainer() {
        for (int i = 0; i < 5; i++) {
            this.getChildren().add(new Button("Dummy"));
        }
        
        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(MIN_WIDTH);
        this.setMinHeight(MIN_HEIGHT);
        this.setMaxWidth(MIN_WIDTH);
        this.setMaxHeight(MIN_HEIGHT);
        this.setPadding(new Insets(CONTAINER_INSET_VALUE));
        this.setSpacing(CONTAINER_SPACING_VALUE);
    }
}