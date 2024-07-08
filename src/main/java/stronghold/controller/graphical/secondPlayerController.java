package stronghold.controller.graphical;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.controller.mainMenuController;
import stronghold.model.UsersDB;
import stronghold.model.components.User;
import stronghold.model.utils.Encryption;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static stronghold.controller.graphical.HubMenuController.updateDB;
import static stronghold.controller.menuController.usernameExists;

public class secondPlayerController {
    public static User currentUser;
    private User secondUser;
    long currentTime = System.currentTimeMillis();
    int failedAttempts = 0;
    long nextAttemptTime = 0;

    public static User getUser() {
        return currentUser;
    }

    public static void setUser(User user) {
        currentUser = user;
    }

    @FXML
    public CheckBox stayLoggedInBox;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton, registerButton, recoverPasswordButton;

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
    public void openMessageDialog(String error) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Message");
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
    public boolean checkStates() {
        if (!mainMenuController.usernameFormatCorrect(usernameField.getText())) {
            openErrorDialog("Error!: Username format incorrect!");
            return false;
        }
        return true;
    }

    @FXML
    public void authenticate() throws InterruptedException {
        if (!checkStates()) {
            return;
        }
        String username = usernameField.getText();
        String password = Encryption.toSHA256(passwordField.getText());

        String token = Encryption.toSHA256(username + password);
        boolean authenticate = authenticate(username, password);


        if (!authenticate) {
            openErrorDialog("Error!: Provided credentials are invalid!");
            if (currentTime < nextAttemptTime) {
                long waitTime = (nextAttemptTime - currentTime) / 1000;
                System.out.println("Try again in " + waitTime + " seconds");

            } else {
                failedAttempts++;
                //signUpLoginView.output("unmatchingpassanduser");
                openErrorDialog("Error!: Provided credentials are invalid!");
                nextAttemptTime = System.currentTimeMillis() + (long) failedAttempts * 5 * 1000;
                System.out.println("Try again in " + 5 * failedAttempts + " seconds");
            }
            return;
        }
        else if(LobbyController.isBetting() && UsersDB.usersDB.getUserByUsername(username).getGold() < 50){
            openErrorDialog("Error!: you don't have enough gold");
        }
        else if (GraphicalCaptchaController.generateCaptcha()) {
            User secondUser = UsersDB.usersDB.getUserByUsername(username);
            LobbyController.stage.close();
            GameMenuController.setUser(currentUser);
            GameMenuController.setSecondUser(secondUser);
            if(secondUser.getLevel()==0){
                mainMenuController.StartPack(new Random(), secondUser);
                secondUser.setLevel(secondUser.getLevel() + 1);
                updateDB(secondUser);
                openMessageDialog("Congrats!\nstart pack is given to " + secondUser.getUsername());

            }
            PauseTransition delay = new PauseTransition(Duration.millis(30));
            Platform.runLater(() -> {
                Pane root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/characterSelect.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                CharacterSelectController.setStage(stage);
                stage.setScene(scene);
                stage.showAndWait();
            });


        }

    }

    public static boolean authenticate(String username, String password) {
        if (!usernameExists(username))
            return false;
        User user = UsersDB.usersDB.getUserByUsername(username);
        if (!user.getPassword().equals(Encryption.toSHA256(password))) {
            return false;
        }
        return true;
    }

    @FXML
    public void openPasswordRecoverMenu() throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/passwordRecoverView.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
