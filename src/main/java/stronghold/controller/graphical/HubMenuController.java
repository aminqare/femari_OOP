package stronghold.controller.graphical;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.controller.profileMenuController;
import stronghold.model.components.User;
import stronghold.view.graphics.LoginView;
import stronghold.view.graphics.ProfileEditView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class HubMenuController {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }


    @FXML
    Button logOutButton, profileMenuButton;
    @FXML
    Label usernameLabel;

    public void logOut() {


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
            toBeWritten = toBeWritten.replace(currentUser.getUsername(), "!NULLUSER");
            FileWriter fileWriter = new FileWriter(pathToPrefs);
            fileWriter.write(toBeWritten);
            fileWriter.close();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
//        LoginController.setUser(currentUser);
        Pane root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginView.fxml")));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openErrorDialog(String error) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Notification!");
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

//    public void goToLobby() throws IOException, InterruptedException {
//        // TODO: wire up game menu
//        LobbyController.setUser(currentUser);
//        LobbyController.setClient(client);
//        Thread.sleep(200);
//        Stage primaryStage = new Stage();
//        Pane root;
//        LobbyController.setStage(primaryStage);
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LobbyMenu.fxml")));
//        Scene currentScene = new Scene(root);
//        primaryStage.setScene(currentScene);
//        primaryStage.show();
//    }

    public void openProfileEdit() {
        ProfileEditController.setCurrentUser(currentUser);

        // TODO: Make profile menu

        Pane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/profileEditView.fxml"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        usernameLabel.setText(currentUser.getUsername());
    }

    @FXML
    public void initialize() throws IOException {
//       String username = currentUser.getUsername();
//       usernameLabel.setText(username);
      if(currentUser!=null){
          String username=currentUser.getUsername();
          usernameLabel.setText(username);
      }else{
          System.out.println("user is null");
      }

    }
    public void updateuser(User user){
        Platform.runLater(()->{
            currentUser.set(user);
        });
    }

 //   @FXML
//    public void searchPlayers(ActionEvent actionEvent) {
//        Stage stage = new Stage();
//        Pane root = null;
//        SearchPlayersController.setClient(client);
//        SearchPlayersController.setLoggedInUser(currentUser);
//        try {
//            root = FXMLLoader.load(getClass().getResource("/searchPlayerView.fxml"));
//        } catch (
//                IOException e) {
//            throw new RuntimeException(e);
//        }
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
}