import javafx.application.Application;
import javafx.stage.Stage;


public class mainYanis extends Application {

    @Override
    public void start(Stage primaryStage) {
        new pageAdmin(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
