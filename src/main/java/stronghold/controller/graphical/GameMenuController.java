package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import stronghold.model.components.Game;
import stronghold.model.components.Player;
import stronghold.model.components.User;
import stronghold.model.components.superGame;

import java.util.Objects;

import static stronghold.view.mainMenuView.IsBetting;

public class GameMenuController {
    static User user;
    static User secondUser;
    private Player playerOne;
    private Player playerTwo;
    static String characterOne;
    static String characterTwo;

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
    private GridPane upperGround;

    @FXML
    private GridPane lowerGround;

    @FXML
    public void initialize() {
        System.out.println("Initializing GameMenuController");
        playerOne = chooseCharacter(characterOne, user);
        playerTwo = chooseCharacter(characterTwo, secondUser);
        superGame superGame = new superGame(playerOne, playerTwo, IsBetting);
        Game game = new Game(superGame);
        System.out.println("Adding cells to upperGround");
        addCellsToGround(upperGround, playerTwo);
        System.out.println("Adding cells to lowerGround");
        addCellsToGround(lowerGround, playerOne);
    }

    public static Player chooseCharacter(String character, User currentUser) {
        return new Player(currentUser, character);
    }

    private void addCellsToGround(GridPane ground, Player player) {
        for (int i = 0; i < 21; i++) {
            Pane cell = new Pane();
            if (Objects.equals(player.getGameBoard().getBoard().get(i), "1")) {
                cell.setStyle("-fx-border-color: black; -fx-background-color: black;");
            } else {
                cell.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");
            }
            cell.setPrefSize(50.0, 80.0);
            ground.add(cell, i, 0);
        }
    }
}
