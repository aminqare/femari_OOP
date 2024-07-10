package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class gameMenuView extends Application {


    @Override
    public  void start(Stage primaryStage) throws Exception {
       // primaryStage.close();
        Stage newStage=new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/gameMenu.fmxl"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public gameMenuView() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
