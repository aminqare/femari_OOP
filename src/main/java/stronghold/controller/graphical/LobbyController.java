
package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LobbyController {
    private static User currentUser;
    public Button DuoMode;
    public Button BettingMode;
    public static boolean isBetting;

    public static boolean isBetting() {
        return isBetting;
    }

    public void setBetting(boolean betting) {
        isBetting = betting;
    }

    public static User getUser() {
        return currentUser;
    }

    public static void setUser(User user) {
        LobbyController.currentUser = user;
    }

    private String currentGame;

    @FXML
    Button duoMode;
    @FXML
    Button bettingMode;
    @FXML
    Label startGameLabel;
    @FXML
    TextField usernameField;
    @FXML
    Pane games;
    public static Stage stage;

    public static void setStage(Stage stage) {
        LobbyController.stage = stage;
    }

    @FXML
    public void initialize() throws IOException {
        System.out.println(currentUser.getUsername());
    }
    @FXML
    public void duoModeStart() throws IOException, InterruptedException {
        isBetting = false;
        secondPlayerController.setUser(currentUser);
        Thread.sleep(200);
        stage.close();
        Stage primaryStage = new Stage();
        Pane root;
        LobbyController.setStage(primaryStage);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/secondPlayer.fxml")));
        Scene currentScene = new Scene(root);
        URL url = getClass().getResource("/style.css");
        if (url == null) {
            throw new RuntimeException("Unable to load style.css, make sure it exists in the resources directory.");
        }
        currentScene.getStylesheets().add(url.toExternalForm());
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }
    @FXML
    public void bettingModeStart() throws IOException, InterruptedException {
        isBetting = true;
        secondPlayerController.setUser(currentUser);
        Thread.sleep(200);
        stage.close();
        Stage primaryStage = new Stage();
        Pane root;
        LobbyController.setStage(primaryStage);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/secondPlayer.fxml")));
        Scene currentScene = new Scene(root);
        URL url = getClass().getResource("/style.css");
        if (url == null) {
            throw new RuntimeException("Unable to load style.css, make sure it exists in the resources directory.");
        }
        currentScene.getStylesheets().add(url.toExternalForm());
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }
}
