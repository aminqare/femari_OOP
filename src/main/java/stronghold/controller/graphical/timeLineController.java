package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import stronghold.model.components.Player;
import stronghold.model.components.gameBoard;
import stronghold.model.graphical.cell;
import stronghold.model.graphical.graphicalCards;
import stronghold.model.specialCards.specialCards;

import java.util.Objects;

public class timeLineController {


    public GridPane upperGround;
    public GridPane lowerGround;
    @FXML
    public void initialize(){
        addCellsToGround(upperGround,GameMenuController.getPlayerTwo());
        addCellsToGround(lowerGround,GameMenuController.getPlayerOne());
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


}
