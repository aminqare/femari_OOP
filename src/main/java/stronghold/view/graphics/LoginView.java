package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginView extends Application {

    static Stage stage;

    public static void main(String[] args) {
        try {
            launch(args);
        }catch (Exception e){
            stage.show();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginView.fxml")));
        Scene currentScene = new Scene(root);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }
}