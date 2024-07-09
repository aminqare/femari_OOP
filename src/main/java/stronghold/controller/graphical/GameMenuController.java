package stronghold.controller.graphical;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import stronghold.model.UsersDB;
import stronghold.model.cards.cards;
import stronghold.model.components.Game;
import stronghold.model.components.Player;
import stronghold.model.components.User;
import stronghold.model.components.superGame;
import stronghold.model.graphical.cell;
import stronghold.model.graphical.graphicalCards;
import stronghold.model.specialCards.specialCards;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static stronghold.model.graphical.cell.updateCell;
import static stronghold.view.mainMenuView.IsBetting;

public class GameMenuController {
    static User user;
    static User secondUser;
    public ScrollPane topLeftDeck;
    public ScrollPane topRightDeck;
    public ScrollPane bottomLeftDeck;
    public ScrollPane bottomRightDeck;
    public HBox specialCardsBarTwo;
    public HBox cardsBarTwo;
    public HBox specialCardsBarOne;
    public HBox cardsBarOne;
    private static Player playerOne;
    private static Player playerTwo;
    static String characterOne;
    static String characterTwo;
    static Stage stage;
    public static Game game;
    public static superGame superGame;
    public ImageView player2Image;
    public Label player2Name;
    public ProgressBar player2HP;
    public ImageView player1Image;
    public Label player1Name;
    public ProgressBar player1HP;

    public HBox getSpecialCardsBarTwo() {
        return specialCardsBarTwo;
    }

    public void setSpecialCardsBarTwo(HBox specialCardsBarTwo) {
        this.specialCardsBarTwo = specialCardsBarTwo;
    }

    public void setCardsBarTwo(HBox CardsBarTwo) {
        cardsBarTwo = CardsBarTwo;
    }

    public HBox getSpecialCardsBarOne() {
        return specialCardsBarOne;
    }

    public void setSpecialCardsBarOne(HBox specialCardsBarOne) {
        this.specialCardsBarOne = specialCardsBarOne;
    }

    public void setCardsBarOne(HBox CardsBarOne) {
        cardsBarOne = CardsBarOne;
    }

    public Stage getStage() {
        return stage;
    }

    public static void setStage(Stage Stage) {
        stage = Stage;
    }

    public static void setCharacterOne(String character) {
        characterOne = character;
    }

    public static void setCharacterTwo(String character) {
        characterTwo = character;
    }

    public static void setSecondUser(User secondUser) {
        GameMenuController.secondUser = secondUser;
    }

    public static void setUser(User currentUser) {
        user = currentUser;
    }

    @FXML
    public GridPane upperGround;

    @FXML
    public GridPane lowerGround;

    @FXML
    public void initialize() {
        if (playerOne == null || playerTwo == null || game == null || superGame == null) {
            System.out.println("One of the necessary objects is null");
            return;
        }

        playerOne.getGameBoard().UpdateType();
        playerTwo.getGameBoard().UpdateType();
        updateDeck();
        addCellsToGround(upperGround, playerTwo);
        addCellsToGround(lowerGround, playerOne);
        URL url = getClass().getResource("/images/" + characterTwo + ".jpeg");
        if (url == null) {
            System.out.println("Resource not found.");
        } else {
            Image image = new Image(url.toExternalForm());
        }
        Platform.runLater(() -> {
            setPlayer2(playerTwo.getUsername(), new Image(String.valueOf(getClass().getResource("/images/" + characterTwo + ".jpeg"))), 100);
            setPlayer1(playerOne.getUsername(), new Image(String.valueOf(getClass().getResource("/images/" + characterOne + ".jpeg"))), 100);
            setPlayerHP(player1HP, 100);
            setPlayerHP(player2HP, 100);
        });

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

    public void updateDeck() {
        cardsBarOne.getChildren().clear();
        cardsBarTwo.getChildren().clear();
        specialCardsBarOne.getChildren().clear();
        specialCardsBarTwo.getChildren().clear();
        for (cell cell : playerOne.getCardsCell()) {
            cardsBarTwo.getChildren().add(cell);
            cell.getStyleClass().add("card");
            cell.setOnMouseEntered(event -> cell.setScaleX(1.1));
            cell.setOnMouseExited(event -> cell.setScaleX(1.0));
            cell.setOnMouseClicked(event -> openCardsInfo(cell, playerOne.getUser()));
        }
        for (cell cell : playerTwo.getCardsCell()) {
            cardsBarOne.getChildren().add(cell);
            cell.setOnMouseEntered(event -> cell.setScaleX(1.1));
            cell.setOnMouseExited(event -> cell.setScaleX(1.0));
            cell.setOnMouseClicked(event -> openCardsInfo(cell, playerTwo.getUser()));
        }
        for (cell cell : playerOne.getSpecialCardsCell()) {
            specialCardsBarTwo.getChildren().add(cell);
            cell.setOnMouseEntered(event -> cell.setScaleX(1.1));
            cell.setOnMouseExited(event -> cell.setScaleX(1.0));
            cell.setOnMouseClicked(event -> openSpecialCardInfo(cell, playerOne.getUser()));
        }
        for (cell cell : playerTwo.getSpecialCardsCell()) {
            specialCardsBarOne.getChildren().add(cell);
            cell.setOnMouseEntered(event -> cell.setScaleX(1.1));
            cell.setOnMouseExited(event -> cell.setScaleX(1.0));
            cell.setOnMouseClicked(event -> openSpecialCardInfo(cell, playerTwo.getUser()));
        }
    }

    public static Player getPlayerOne() {
        return playerOne;
    }

    public static void setPlayerOne(Player PlayerOne) {
        playerOne = PlayerOne;
    }

    public static Player getPlayerTwo() {
        return playerTwo;
    }

    public static void setPlayerTwo(Player PlayerTwo) {
        playerTwo = PlayerTwo;
    }

    public Game getGame() {
        return game;
    }

    public static void setGame(Game Game) {
        game = Game;
    }

    public stronghold.model.components.superGame getSuperGame() {
        return superGame;
    }

    public static void setSuperGame(superGame SuperGame) {
        superGame = SuperGame;
    }

    public static Player chooseCharacter(String character, User currentUser) {
        if (character == null || currentUser == null) {
            System.out.println("Character or currentUser is null");
            return null;
        }
        return new Player(currentUser, character);
    }

    public static void setPlayerDeck(Player player, cards card) {
        cell cardCell = new cell(90, 90);
        cardCell.setName(card.getName());
        String cardName = card.getName();
        Image image = new Image(graphicalCards.class.getResource("/images/cards/" + cardName + ".png").toExternalForm(), 90, 90, false, true);
        cardCell.setFill(new ImagePattern(image, 0, 0, 90, 90, false));
        player.getCardsCell().add(cardCell);
    }

    public static void setPlayerSpecialDeck(Player player, specialCards card) {
        cell cardCell = new cell(90, 90);
        cardCell.setName(card.getName());
        String cardName = card.getName();
        Image image = new Image(graphicalCards.class.getResource("/images/specialCards/" + cardName + ".png").toExternalForm(), 90, 90, false, true);
        cardCell.setFill(new ImagePattern(image, 0, 0, 90, 90, false));
        player.getSpecialCardsCell().add(cardCell);
    }

    public static void addCellsToGround(GridPane pane, Player player) {
        pane.getChildren().clear(); // Clear existing cells if any
        for (int i = 0; i < 21; i++) {
            cell cell = new cell(50, 80);
            cell.setName("ground");
            if (Objects.equals(player.getGameBoard().getBoard().get(i), "1")) {
                cell.setFill(Color.GRAY);
            } else {
                cell.setFill(Color.BLACK);
            }
            if (pane != null) {
                player.getCells().add(cell);
                pane.add(cell, i % 21, i / 21);
            } else {
                System.out.println("Pane is null");
            }
        }
    }

    public static void switchTurns(Game game) {
        Player player = game.getCurrentPlayer();
        Player enemy = game.getCurrentEnemy();
        ArrayList<cards> cards = player.getPlayerCards();
        ArrayList<specialCards> specialCards = player.getPlayerSpecialCards();
        double randomIndex = Math.abs(game.getRandom().nextDouble());
        if (randomIndex < 0.7) {
            int randomIndexForCards = Math.abs(game.getRandom().nextInt(cards.size()));
            player.getPlayerCardsDeck().add(cards.get(randomIndexForCards));
            setPlayerDeck(player, cards.get(randomIndexForCards));
        } else {
            int randomIndexForSpecialCards = Math.abs(game.getRandom().nextInt(specialCards.size()));
            player.getPlayerSpecialCardsDeck().add(specialCards.get(randomIndexForSpecialCards));
            setPlayerSpecialDeck(player, specialCards.get(randomIndexForSpecialCards));
        }
        player.setTurn(false);
        enemy.setTurn(true);
        GameMenuController.openMessageDialog(enemy.getUsername() + "'s turn");
    }
    public static void openMessageDialog(String error) {
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

    private void openSpecialCardInfo(cell cell, User user) {
        if(user==superGame.getCurrentGame().getCurrentPlayer().getUser()) {
            battleSpecialCardsInfoController.setCurrentUser(user);
            battleSpecialCardsInfoController.setCell(cell);
            Pane root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/specialCardsBattleInfo.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> {
                updateDeck();
                updateCell(playerOne);
                updateCell(playerTwo);
                checkRounds();
            });
            stage.showAndWait();
        }else{
            openErrorDialog("its not your turn");
        }
    }

    private void openCardsInfo(cell cell, User user) {
        if(user==superGame.getCurrentGame().getCurrentPlayer().getUser()) {
            battleCardInfoController.setCurrentUser(user);
            battleCardInfoController.setCell(cell);
            battleCardInfoController.setCurrentSuperGame(superGame);
            Pane root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cardsBattleInfo.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> {
                updateDeck();
                updateCell(playerOne);
                updateCell(playerTwo);
                checkRounds();
            });
            stage.showAndWait();
        }else{
            openErrorDialog("its not your turn");

        }
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
    public void checkRounds() {
        if (playerOne.getRounds() == 0 && playerTwo.getRounds() == 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/timeLine.fxml"));
                AnchorPane root = loader.load();
                Scene scene = new Scene(root);
                Stage timelineStage = new Stage();
                timelineStage.setScene(scene);
                timelineStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
