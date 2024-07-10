package stronghold.controller.graphical;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import stronghold.controller.mainMenuController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.UsersDB;
import stronghold.model.components.User;
import stronghold.model.utils.Encryption;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import static stronghold.controller.graphical.HubMenuController.updateDB;
import static stronghold.controller.menuController.usernameExists;

public class LoginController {

    public static User user;
    long currentTime = System.currentTimeMillis();
    int failedAttempts = 0;
    long nextAttemptTime = 0;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginController.user = user;
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
    public void enterRegisterMenu() throws IOException {
        RegisterController.setUser(user);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/registerView.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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

//        if (!userAuthenticated) {
//            if (currentTime < nextAttemptTime) {
//                long waitTime = (nextAttemptTime - currentTime) / 1000;
//                System.out.println("Try again in " + waitTime + " seconds");
//
//            } else {
//                failedAttempts++;
//                //signUpLoginView.output("unmatchingpassanduser");
//                openErrorDialog("Error!: Provided credentials are invalid!");
//                nextAttemptTime = System.currentTimeMillis() + (long) failedAttempts * 5 * 1000;
//                System.out.println("Try again in " + 5 * failedAttempts + " seconds");
//            }
//
//        }
//


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

//        if (!authenticate) {
//            openErrorDialog("Error!: Provided credentials are incorrect!");
//        }
        else if (GraphicalCaptchaController.generateCaptcha()) {
            if (stayLoggedInBox.isSelected()) {
                Thread.sleep(1000);
                JsonElement prefsElement;
                String pathToPrefs = "src/main/java/stronghold/database/preferences.json";
                try {
                    prefsElement = JsonParser.parseReader(
                            new FileReader(pathToPrefs));
                } catch (
                        FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                try {
                    String toBeWritten = prefsElement.toString();
                    toBeWritten = toBeWritten.replace("!NULLUSER", username);
                    FileWriter fileWriter = new FileWriter(pathToPrefs);
                    fileWriter.write(toBeWritten);
                    fileWriter.close();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
            }
            user=UsersDB.usersDB.getUserByUsername(username);
            HubMenuController.setCurrentUser(user);
            if(user.getLevel()==0){
                mainMenuController.StartPack(new Random(), user);
                user.setLevel(user.getLevel() + 1);
                updateDB(user);
                openMessageDialog("Congrats!\nstart pack is given to " + user.getUsername());

            }
            PauseTransition delay = new PauseTransition(Duration.millis(30));
            delay.setOnFinished(event -> {
                Parent root = null;
                try {
                    //root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/hubMenuView.fxml")));

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/hubMenuView.fxml"));
                    root = loader.load();
//                    URL fxmlLocation = getClass().getResource("/hubMenuView.fxml");
//                    System.out.println("FXML Location: " + fxmlLocation);
//                    if (loader == null) {
//                        System.out.println("FXML file not found. Check the path.");
//                    }
                } catch (
                        IOException ignored) {

                }

                Scene scene = new Scene(root);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                HubMenuController.setStage(stage);
                stage.setScene(scene);
                stage.show();
            });
            delay.play();


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