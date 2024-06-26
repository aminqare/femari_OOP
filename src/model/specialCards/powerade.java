package model.specialCards;

import model.CardsDB;
import model.cards.cards;
import model.components.Game;
import model.components.Player;

import java.util.ArrayList;

//afzayesh dahandeh ghodrat
public class powerade extends specialCards {
    public powerade(){
        this.setDuration(0);
        this.setName("powerade");
        this.setPrice(100);
    }

    @Override
    void run(Object param) {
        if (param instanceof Game) {
            run((Game) param);
        } else {
            System.out.println("ridi");
        }
    }

    public void run(Game game) {
        Player player = game.getCurrentPlayer();
        ArrayList<cards> tempCards = new ArrayList<>();
        ArrayList<String> playerGameBoard = player.getGameBoard().getBoard();
        for (String s : playerGameBoard) {
            if (CardsDB.IsCardName(s)) {
                tempCards.add(CardsDB.getCardByName(s));
            }
        }
        int randomIndex = game.getRandom().nextInt(tempCards.size());
        cards card = tempCards.get(randomIndex);
        card.powerUp();
    }
}
