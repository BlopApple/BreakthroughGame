import model.ModelManager;
import ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static final int BOARD_SIZE = 6;

    private static final double WINDOW_MIN_WIDTH = 540.0;
    private static final double WINDOW_MIN_HEIGHT = 420.0;

    @Override
    public void start(Stage stage) {
        // Setting the scene
        MainWindow mainWindow = new MainWindow(new ModelManager(BOARD_SIZE));
        
        stage.setTitle("Breakthrough Game");
        stage.setResizable(false);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);

        stage.setScene(new Scene(mainWindow));
        stage.show();
    }
}