package stronghold.model.graphical;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import stronghold.model.components.Player;
import stronghold.model.components.gameBoard;
import stronghold.model.specialCards.specialCards;
import stronghold.view.GameMenuView;

import java.util.Objects;

public class cell extends Rectangle {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public cell(int i, int i1) {

        super(i, i1);
        setFill(Color.BLACK);
    }


    public static void updateCell(Player player){
        player.getGameBoard().UpdateType();
        gameBoard gameBoard=player.getGameBoard();
        for (int i = 0; i < 21; i++) {
            if(gameBoard.getType().get(i).equals("specialCards")){
                String specialCardName = gameBoard.getBoard().get(i);
                specialCards specialCard = specialCards.GetSpecialCardByName(player.getPlayerSpecialCardsDeck(), specialCardName);
                Image image = new Image(graphicalCards.class.getResource("/images/" + "specialCards" + "/" + specialCardName + ".png").toExternalForm(), 50, 80, false, true);
                player.getCells().get(i).setFill(new ImagePattern(image, 0, 0, 50, 80, false));
            }
            else if(gameBoard.getType().get(i).equals("cards")){
                System.out.println("hello");
                String cardName = gameBoard.getBoard().get(i);
                Image image = new Image(graphicalCards.class.getResource("/images/" + "cards" + "/" + cardName + ".png").toExternalForm(), 50, 80, false, true);
                player.getCells().get(i).setFill(new ImagePattern(image, 0, 0, 50, 80, false));
            }
            else if(gameBoard.getType().get(i).equals("hole")){
                Image image = new Image(graphicalCards.class.getResource("/images/" + "hole.png").toExternalForm(), 50, 80, false, true);
                player.getCells().get(i).setFill(new ImagePattern(image, 0, 0, 50, 80, false));
            }else if(gameBoard.getType().get(i).equals("empty")){
                player.getCells().get(i).setFill(Color.BLACK);
            }else if(gameBoard.getType().get(i).equals("unv")){
                player.getCells().get(i).setFill(Color.GRAY);
            }
        }
    }
}
