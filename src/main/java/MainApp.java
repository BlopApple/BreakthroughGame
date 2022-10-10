import model.ModelManager;
import ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static final int BOARD_SIZE = 6;

    private static final double WINDOW_MIN_WIDTH = 560.0;
    private static final double WINDOW_MIN_HEIGHT = 440.0;
    private static final double STAGE_WIDTH_OFFSET = 15.0;
    private static final double STAGE_HEIGHT_OFFSET = 60.0;

    @Override
    public void start(Stage stage) {
        // Setting the scene
        MainWindow mainWindow = new MainWindow(new ModelManager(BOARD_SIZE));
        mainWindow.setMinSize(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT);

        stage.setTitle("Breakthrough Game");
        stage.setResizable(false);
        stage.setMinWidth(WINDOW_MIN_WIDTH + STAGE_WIDTH_OFFSET);
        stage.setMinHeight(WINDOW_MIN_HEIGHT + STAGE_HEIGHT_OFFSET);

        stage.setScene(new Scene(mainWindow));
        stage.show();
    }
}