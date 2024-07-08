package stronghold.view.graphics;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stronghold.controller.signUpMenuController;
import stronghold.controller.graphical.RegisterController;

import java.io.Serializable;
import java.util.Objects;

public class RegisterView extends Application implements Serializable {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/registerView.fxml")));
        Scene currentScene = new Scene(root);

        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public void launchLoginView(){
        LoginView.launch(null);
    }
}