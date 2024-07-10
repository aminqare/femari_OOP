package stronghold.controller.graphical;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.components.Player;
import stronghold.model.components.User;
import stronghold.model.components.gameHistory;
import stronghold.model.components.superGame;
import stronghold.view.endGameView;

import java.io.IOException;

public class endGameController {
   private static User winner;
   private static User loser;
   private static  Player player;
   private static superGame currentSuperGame;
    public AnchorPane mainPane;
    public Label gameInfo;
    public static Stage stage;

    public static void setStage(Stage stage) {
        endGameController.stage = stage;
    }

    public static superGame getCurrentSuperGame() {
        return currentSuperGame;
    }

    public static void setCurrentSuperGame(superGame currentSuperGame) {
        endGameController.currentSuperGame = currentSuperGame;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        endGameController.player = player;
    }

    public User getWinner() {
        return winner;
    }

    public static void setWinner(User Winner) {
        winner = Winner;
    }

    public User getLoser() {
        return loser;
    }

    public static void setLoser(User Loser) {
        loser = Loser;

    }
    @FXML
    public void initialize(){
       // endGameView.Output("gameFinished", winner.getUsername(), loser.getUsername());

        double prizeIndex = ((player.getHP())*0.1 - ((player.getLevel()) - loser.getLevel()) * 0.5);
        int givenXP = (int)prizeIndex;
        int givenGold = (int)(prizeIndex * 10);
        int loserXP = 50;
        int loserGold = 20;
        winner.setXP(winner.getXP() + givenXP);
        winner.setGold(winner.getGold() + givenGold);
        loser.setGold(loser.getGold() + loserGold);
        loser.setXP(loser.getXP() + loserXP);
        if(currentSuperGame.isBetting()){
            winner.setGold(winner.getGold() + 50);
            loser.setGold(loser.getGold() - 50);
            openMessageDialog("loser have lost 50 gold for betting");
        }
        gameHistory gameHistoryWinner = new gameHistory(loser.getUsername(), "Won", loser.getLevel(), currentSuperGame.getDate());
        gameHistory gameHistoryLoser = new gameHistory(winner.getUsername(), "Lost", winner.getLevel(), currentSuperGame.getDate());
        winner.getGameHistory().add(gameHistoryWinner);
        loser.getGameHistory().add(gameHistoryLoser);
        levelUp(winner);
        levelUp(loser);
        endGameView.updateDB(loser);
        endGameView.updateDB(winner);
        gameInfo.setText("Winner is: " + winner.getUsername() + "\nLoser is: " + loser.getUsername() + "\n winner will be rewarded with " + givenGold +" amount of gold\nand " + givenXP + " amount of XP\n"
        + "Loser is also rewarded with 50 Gold\nand 20 XP\n" + "             thanks for playing!!");
    }
    public static void levelUp(User user){
        int prevLevel = user.getLevel();
        user.setMaxXP();
        while(user.getXP() > user.getMaxXP()){
            user.setMaxXP();
            user.setXP(user.getXP() - user.getMaxXP());
            user.setLevel(user.getLevel() + 1);
        }
        int finalLevel = user.getLevel();
        if(finalLevel > user.getLevel()){
            openMessageDialog("congrats!\n"+user.getUsername()+" have been level upped to "+String.valueOf(finalLevel));
        }
    }
    public static void openMessageDialog(String message) {
        Platform.runLater(() -> {
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
        });
    }

    public void backToHubMenu(ActionEvent actionEvent) {
        HubMenuController.setCurrentUser(currentSuperGame.getPlayerOne().getUser());
        PauseTransition delay = new PauseTransition(Duration.millis(30));
        delay.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/hubMenuView.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
//                URL url = getClass().getResource("/Styles.css");
//                if (url == null) {
//                    throw new RuntimeException("Unable to load style.css, make sure it exists in the resources directory.");
//                }
//                scene.getStylesheets().add(url.toExternalForm());
                stage.setScene(scene);
                timeLineController.setStage(stage);
                stage.show();
            } catch (IOException ignored) {
                System.out.println("Error: Unable to load the time line.");
            }
        });
        delay.play();
    }
}
