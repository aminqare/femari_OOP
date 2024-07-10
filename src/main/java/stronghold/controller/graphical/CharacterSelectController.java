package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.Game;
import stronghold.model.components.Player;
import stronghold.model.components.superGame;

import java.io.IOException;
import java.net.URL;

import static stronghold.view.mainMenuView.IsBetting;
import static stronghold.controller.graphical.GameMenuController.chooseCharacter;

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
        Dialog<String> dialog = new Dialog<>();
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
    public void authenticate() throws Exception {
        String firstChar = firstPlayerCharacter.getText();
        String secondChar = secondPlayerCharacter.getText();
        if (firstChar.isEmpty() || secondChar.isEmpty()) {
            openErrorDialog("Error!: Please select your Characters first");
            return;
        }

        GameMenuController.setCharacterOne(firstChar);
        GameMenuController.setCharacterTwo(secondChar);
        Player playerOne = chooseCharacter(firstChar, GameMenuController.user);
        Player playerTwo = chooseCharacter(secondChar, GameMenuController.secondUser);
        superGame currentSuperGame = new superGame(playerOne, playerTwo, IsBetting);
        Game game = new Game(currentSuperGame);
        currentSuperGame.setCurrentGame(game);
        GameMenuController.setPlayerOne(playerOne);
        GameMenuController.setPlayerTwo(playerTwo);
        GameMenuController.setGame(game);
        GameMenuController.setSuperGame(currentSuperGame);

        // چک کردن مقدار null
        if (currentSuperGame.getCurrentGame() == null) {
            System.out.println("Error: Current game is null.");
            return; // اضافه کردن این خط تا اجرای کد ادامه نداشته باشد
        }

        PauseTransition delay = new PauseTransition(Duration.millis(30));
        delay.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/gameMenu.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                GameMenuController.setStage(stage);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: Unable to load the game menu.");
            }
        });
        delay.play();
    }



    public static void openMessageDialog(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Message");
        Label label = new Label(message);
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
}
