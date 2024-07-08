
package stronghold.view.graphics;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.controller.graphical.LobbyController;
import stronghold.model.components.User;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class LobbyMenu extends Application {
    public Pane root;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        LobbyController.setStage(primaryStage);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LobbyMenu.fxml")));
        Scene currentScene = new Scene(root);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

}
