package stronghold.controller.graphical;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import stronghold.model.components.Player;
import stronghold.model.components.superGame;
import stronghold.model.graphical.cell;

public class timeLineController {


    public GridPane upperGround;
    public GridPane lowerGround;
    public AnchorPane Base;
    private Rectangle line;



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

        line = new Rectangle(5, 165);
        line.setFill(Color.RED);
        line.setX(0);
        line.setY(170);
        Base.getChildren().add(line);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> move()));
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

    public void move() {
        line.setX(line.getX() + 2.5);
        if ((line.getX() - 60) % 55 == 0) {
            System.out.println("kirr");
        }
    }
}
