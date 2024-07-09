package stronghold.view.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class timeLineView extends Application {
    public  void start(Stage primaryStage) throws Exception {
        // primaryStage.close();
        Stage newStage=new Stage();
        Pane root = FXMLLoader.load(getClass().getResource("/timeLine.fmxl"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
