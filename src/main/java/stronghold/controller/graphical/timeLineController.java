package stronghold.controller.graphical;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.model.cards.cards;
import stronghold.model.components.*;
import stronghold.model.graphical.cell;
import stronghold.view.endGameView;
import stronghold.view.mainMenuView;
import stronghold.view.timeLineMenu;

import java.io.IOException;

import static stronghold.controller.graphical.GameMenuController.characterOne;
import static stronghold.controller.graphical.GameMenuController.characterTwo;
import static stronghold.controller.graphical.battleSpecialCardsInfoController.openErrorDialog;


public class timeLineController {


    public GridPane upperGround;
    public GridPane lowerGround;
    public AnchorPane Base;
    public ImageView player2Image;
    public Label player2Name;
    public ProgressBar player2HP;
    public Label player2HPNum;
    public ImageView player1Image;
    public Label player1Name;
    public ProgressBar player1HP;
    public Label player1HPNum;
    private Rectangle line;
    private int index = 1;
    private static Stage stage;
    private Timeline timeline;


    public Stage getStage() {
        return stage;
    }

    public static void setStage(Stage Stage) {
        stage = Stage;
    }

    public superGame getCurrentSuperGame() {
        return currentSuperGame;
    }

    public static void setCurrentSuperGame(superGame CurrentSuperGame) {
        currentSuperGame = CurrentSuperGame;
    }

    private static superGame currentSuperGame;

    @FXML
    public void initialize() {
        addCellsToGround(upperGround, GameMenuController.getPlayerTwo());
        addCellsToGround(lowerGround, GameMenuController.getPlayerOne());
        Platform.runLater(() -> {
            setPlayer2(currentSuperGame.getPlayerTwo().getUsername(), new Image(String.valueOf(getClass().getResource("/images/" + characterTwo + ".jpeg"))), 100);
            setPlayer1(currentSuperGame.getPlayerOne().getUsername(), new Image(String.valueOf(getClass().getResource("/images/" + characterOne + ".jpeg"))), 100);
            player2HPNum.setText(String.valueOf(currentSuperGame.getPlayerTwo().getHP()));
            player1HPNum.setText(String.valueOf(currentSuperGame.getPlayerOne().getHP()));
            setPlayerHP(player1HP, 100);
            setPlayerHP(player2HP, 100);
        });

        line = new Rectangle(5, 165);
        line.setFill(Color.RED);
        line.setX(0);
        line.setY(170);
        Base.getChildren().add(line);
        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> move()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public static void addCellsToGround(GridPane pane, Player player) {
        player.getGameBoard().UpdateType();
        pane.getChildren().clear(); // Clear existing cells if any

        for (int i = 0; i < 21; i++) {
            cell cell = player.getCells().get(i);
            if (pane != null) {
                player.getCells().add(cell);
                pane.add(cell, i % 21, i / 21);
            } else {
                System.out.println("Pane is null");
            }
        }

    }

    public void setPlayer1(String name, Image image, double hp) {
        player1Name.setText(name);
        player1Image.setImage(image);
        setPlayerHP(player1HP, hp);
    }

    public void setPlayer2(String name, Image image, double hp) {
        player2Name.setText(name);
        player2Image.setImage(image);
        setPlayerHP(player2HP, hp);
    }

    private static void setPlayerHP(ProgressBar progressBar, double hp) {
        progressBar.setProgress(hp / 100.0);
        if (hp > 70) {
            progressBar.setStyle("-fx-accent: green;");
        } else if (hp > 30) {
            progressBar.setStyle("-fx-accent: yellow;");
        } else {
            progressBar.setStyle("-fx-accent: red;");
        }
    }

    public void move() {
        line.setX(line.getX() + 2.5);
        if (currentSuperGame.getPlayerOne().getHP() <= 0 || currentSuperGame.getPlayerTwo().getHP() <= 0) {
            timeline.stop();
            Player player=null;
            if (currentSuperGame.getPlayerOne().getHP() <= 0) {
                player=currentSuperGame.getPlayerTwo();
            } else if (currentSuperGame.getPlayerTwo().getHP() <= 0) {
               player=currentSuperGame.getPlayerOne();
            }
            currentSuperGame.setWinner(player.getUser().getUsername());
            User winner = player.getUser();
            User loser;
            if(winner == currentSuperGame.getPlayerOne().getUser()){
                loser = currentSuperGame.getPlayerTwo().getUser();
            }
            else{
                loser = currentSuperGame.getPlayerOne().getUser();
            }

            endGameController.setWinner(winner);
            endGameController.setLoser(loser);
            endGameController.setPlayer(player);
            endGameController.setCurrentSuperGame(currentSuperGame);

            PauseTransition delay = new PauseTransition(Duration.millis(30));
            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/endGame.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    GameMenuController.setStage(stage);
                    stage.show();
                } catch (IOException ignored) {
                    openErrorDialog("Error: Unable to load the game menu.");
                }
            });


        } else if (index == 23) {
            timeline.stop();
            openMessageDialog("new round");
            Player playerOne = currentSuperGame.getPlayerOne();
            Player playerTwo = currentSuperGame.getPlayerTwo();
            playerOne.getSpecialCardsCell().clear();
            playerOne.getPlayerCardsDeck().clear();
            playerTwo.getSpecialCardsCell().clear();
            playerTwo.getPlayerCardsDeck().clear();
            playerOne.getCardsCell().clear();
            playerOne.getPlayerCardsDeck().clear();
            playerTwo.getCardsCell().clear();
            playerTwo.getPlayerCardsDeck().clear();
            Game game = new Game(currentSuperGame);
            currentSuperGame.setCurrentGame(game);
            GameMenuController.setPlayerOne(playerOne);
            GameMenuController.setPlayerTwo(playerTwo);
            GameMenuController.setGame(game);
            GameMenuController.setSuperGame(currentSuperGame);

            // چک کردن مقدار null
            if (currentSuperGame.getCurrentGame() == null) {
                openErrorDialog("Error: Current game is null.");
                return; // اضافه کردن این خط تا اجرای کد ادامه نداشته باشد
            }

            PauseTransition delay = new PauseTransition(Duration.millis(30));
            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/gameMenu.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    GameMenuController.setStage(stage);
                    stage.show();
                } catch (IOException ignored) {
                    openErrorDialog("Error: Unable to load the game menu.");
                }
            });
        } else if ((line.getX() - 60) % 55 == 0) {
            Player playerOne = currentSuperGame.getPlayerOne();
            Player playerTwo = currentSuperGame.getPlayerTwo();
            System.out.println("kirr");
            if (currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getType().get(index - 1).equals("cards") && ISnull(currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getType().get(index - 1))) {
                cards card = cards.GetCardByName(playerOne.getPlayerCards(),
                        currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getBoard().get(index - 1));
                playerTwo.setHP(playerTwo.getHP() - (card.getDamage() / card.getDuration()));
                setPlayerHP(player2HP, playerTwo.getHP());
                player2HPNum.setText(String.valueOf(currentSuperGame.getPlayerTwo().getHP()));
                player1HPNum.setText(String.valueOf(currentSuperGame.getPlayerOne().getHP()));
                timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                        playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                timeLineMenu.Output("cards", card.getName(), "null");
            } else if (currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getType().get(index - 1).equals("cards") &&
                    isHeal(playerTwo, index - 1)) {
                cards card = cards.GetCardByName(playerOne.getPlayerCards(),
                        currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getBoard().get(index - 1));
                playerTwo.setHP(playerTwo.getHP() - (card.getDamage() / card.getDuration()));
                playerTwo.IncreaseHP();
                setPlayerHP(player2HP, playerTwo.getHP());
                player2HPNum.setText(String.valueOf(currentSuperGame.getPlayerTwo().getHP()));
                player1HPNum.setText(String.valueOf(currentSuperGame.getPlayerOne().getHP()));
                timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                        playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                timeLineMenu.Output("cards", card.getName(), "Heal");
            } else if (currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getType().get(index - 1).equals("cards") &&
                    ISnull(currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getType().get(index - 1))) {
                cards card = cards.GetCardByName(playerTwo.getPlayerCards(),
                        currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getBoard().get(index - 1));
                playerOne.setHP(playerOne.getHP() - (card.getDamage() / card.getDuration()));
                setPlayerHP(player1HP, playerOne.getHP());
                player2HPNum.setText(String.valueOf(currentSuperGame.getPlayerTwo().getHP()));
                player1HPNum.setText(String.valueOf(currentSuperGame.getPlayerOne().getHP()));
                timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                        playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                timeLineMenu.Output("cards", "null", card.getName());
            } else if (currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getType().get(index - 1).equals("cards") &&
                    isHeal(playerOne, index - 1)) {
                cards card = cards.GetCardByName(playerTwo.getPlayerCards(),
                        currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getBoard().get(index - 1));
                playerOne.setHP(playerOne.getHP() - (card.getDamage() / card.getDuration()));
                playerOne.IncreaseHP();
                setPlayerHP(player1HP, playerOne.getHP());
                player2HPNum.setText(String.valueOf(currentSuperGame.getPlayerTwo().getHP()));
                player1HPNum.setText(String.valueOf(currentSuperGame.getPlayerOne().getHP()));
                timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                        playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                timeLineMenu.Output("cards", "Heal", card.getName());
            } else {
                String specialCard = "specialCards";
                String card = "cards";
                String cardOne;
                String cardTwo;
                if (playerOne.getGameBoard().getType().get(index - 1).equals(specialCard) && playerTwo.getGameBoard().getType().get(index - 1).equals(specialCard)) {
                    if (playerOne.getGameBoard().getType().get(index - 1).equals("heal")) {
                        playerOne.IncreaseHP();
                        cardOne = "Heal";
                        cardTwo = playerTwo.getGameBoard().getBoard().get(index - 1);
                    }
                    if (playerTwo.getGameBoard().getType().get(index - 1).equals("heal")) {
                        playerTwo.IncreaseHP();
                        cardTwo = "Heal";
                        cardOne = playerOne.getGameBoard().getBoard().get(index - 1);
                    } else {
                        cardTwo = playerTwo.getGameBoard().getBoard().get(index - 1);
                        cardOne = playerOne.getGameBoard().getBoard().get(index - 1);
                    }
                    setPlayerHP(player2HP, playerTwo.getHP());
                    setPlayerHP(player1HP, playerOne.getHP());
                    player2HPNum.setText(String.valueOf(currentSuperGame.getPlayerTwo().getHP()));
                    player1HPNum.setText(String.valueOf(currentSuperGame.getPlayerOne().getHP()));
                    timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                            playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                    timeLineMenu.Output("cards", cardOne, cardTwo);
                } else if (playerOne.getGameBoard().getType().get(index - 1).equals(specialCard) && ISnull(playerTwo.getGameBoard().getType().get(index - 1))) {
                    if (playerOne.getGameBoard().getType().get(index - 1).equals("heal")) {
                        playerOne.IncreaseHP();
                        cardOne = "Heal";
                        cardTwo = "null";
                    } else {
                        cardTwo = "null";
                        cardOne = playerOne.getGameBoard().getBoard().get(index - 1);
                    }
                    setPlayerHP(player2HP, playerTwo.getHP());
                    setPlayerHP(player1HP, playerOne.getHP());
                    player2HPNum.setText(String.valueOf(currentSuperGame.getPlayerTwo().getHP()));
                    player1HPNum.setText(String.valueOf(currentSuperGame.getPlayerOne().getHP()));
                    timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                            playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                    timeLineMenu.Output("cards", cardOne, cardTwo);
                } else if (playerTwo.getGameBoard().getType().get(index - 1).equals(specialCard) && ISnull(playerOne.getGameBoard().getType().get(index - 1))) {
                    if (playerTwo.getGameBoard().getType().get(index - 1).equals("heal")) {
                        playerTwo.IncreaseHP();
                        cardTwo = "Heal";
                        cardOne = "null";
                    } else {
                        cardTwo = playerTwo.getGameBoard().getBoard().get(index - 1);
                        cardOne = "null";
                    }
                    setPlayerHP(player2HP, playerTwo.getHP());
                    setPlayerHP(player1HP, playerOne.getHP());
                    player2HPNum.setText(String.valueOf(currentSuperGame.getPlayerTwo().getHP()));
                    player1HPNum.setText(String.valueOf(currentSuperGame.getPlayerOne().getHP()));
                    timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                            playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                    timeLineMenu.Output("cards", cardOne, cardTwo);
                }

            }
            index++;
        }

    }

    public static boolean isHeal(Player player, int index) {
        if (player.getGameBoard().getBoard().get(index).equals("heal")) {
            return true;
        }
        return false;


    }

    public static boolean ISnull(String s) {
        if (s.equals("empty")) {
            return true;
        } else if (s.equals("unv")) {
            return true;
        } else if (s.equals("hole")) {
            return true;
        } else {
            return false;
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
}
