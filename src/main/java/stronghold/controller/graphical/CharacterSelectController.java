package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.view.graphics.CharacterSelect;

import java.io.IOException;

public class CharacterSelectController {
    public TextField firstPlayerCharacter;
    public TextField secondPlayerCharacter;

    public Button DoneButton;
    static Stage stage;

    public static void setStage(Stage Stage) {
        stage = Stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void openErrorDialog(String error) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Error!");
        Label label = new Label(error);
        dialog.setContentText(label.getText());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().getChildren().add(label);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();
    }

    @FXML
    public void authenticate() throws InterruptedException {
        String firstChar = firstPlayerCharacter.getText();
        String secondChar = secondPlayerCharacter.getText();
        if(firstChar.isEmpty() || secondChar.isEmpty()){
            openErrorDialog("Error!: Please select your Characters first");
        }
        GameMenuController.setCharacterOne(firstChar);
        GameMenuController.setCharacterTwo(secondChar);

        Stage currentStage = CharacterSelect.getPrimaryStage();

        // Use Platform.runLater to ensure it runs on the JavaFX application thread
        Platform.runLater(() -> {
            Pane root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/gameMenu.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);

            // Show the new stage and close the current stage
            newStage.show();
            currentStage.close();
        });

    }
}
