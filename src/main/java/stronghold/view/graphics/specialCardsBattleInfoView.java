package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class specialCardsBattleInfoView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/specialCardsBattleInfo.fxml"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
