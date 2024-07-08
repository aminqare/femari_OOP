package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import stronghold.controller.mainMenuController;
import stronghold.model.components.User;
import stronghold.model.UsersDB;
import stronghold.model.utils.Encryption;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PasswordRecoverController {

    private final static Map<Integer, String> numToQuestion = Map.of(
            1,"What is my father’s name?",
            2,"What was my first pet’s name?",
            3,"What is my mother’s last name?"
    );

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
    private Button changePasswordButton;

    @FXML
    private VBox changeBox;

    @FXML
    private TextField confirmField;

    @FXML
    private Button findUserButton;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField securityAnswerField;

    @FXML
    private Label securityQuestionLabel;

    @FXML
    private TextField usernameField;

    @FXML
    public void initialize(){
        changeBox.setVisible(false);
    }

    @FXML
    public void findUser(){
        String username = usernameField.getText();
        User user = UsersDB.usersDB.getUserByUsername(username);
        if(user == null){
            openErrorDialog("User doesn't exist!");
            return;
        }
        changeBox.setVisible(true);
        securityQuestionLabel.setText(numToQuestion.get(user.getPasswordRecoveryQuestion()));

    }

    @FXML
    public void changePassword(){
        String username = usernameField.getText();
        User user = UsersDB.usersDB.getUserByUsername(username);
        String newPassword = passwordField.getText();
        String confirmation = confirmField.getText();
        String answer = securityAnswerField.getText();
        if(!answer.equals(user.getPasswordRecoveryAnswer())){
            openErrorDialog("Incorrect answer to security question!");
            return;
        }
        if(!mainMenuController.passwordIsStrong(newPassword)){
            openErrorDialog("New password is weak\nTry using upper and lower case letters, numbers and symbols!");
            return;
        }
        if(!newPassword.equals(confirmation)){
            openErrorDialog("The password and its confirmation do not match!");
            return;
        }
        if(!GraphicalCaptchaController.generateCaptcha()){
            return;
        }
        String pass = Encryption.toSHA256(newPassword);
        user.setPassword(Encryption.toSHA256(pass));
        UsersDB.usersDB.update(user);
        try {
            UsersDB.usersDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        securityQuestionLabel.getParent().getScene().getWindow().hide();
    }
}