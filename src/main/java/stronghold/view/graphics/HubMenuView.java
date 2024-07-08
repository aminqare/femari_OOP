package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stronghold.controller.graphical.HubMenuController;

import java.io.IOException;
import java.util.Objects;

public class HubMenuView extends Application {
    static Pane root;
    public void changeUsernameLabel(){
        VBox vBox = (VBox) root.getChildren().get(0);
        HBox hBox = (HBox) vBox.getChildren().get(1);
        Label usernameLabel = (Label) hBox.getChildren().get(1);
        usernameLabel.setText(HubMenuController.getCurrentUser().getUsername());
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/hubMenuView.fxml")));
        Scene scene = new Scene(root);
        changeUsernameLabel();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}