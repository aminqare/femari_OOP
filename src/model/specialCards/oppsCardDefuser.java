package model.specialCards;

import model.cards.cards;
import model.components.Game;
import model.components.Player;

import java.util.ArrayList;

//taazif konandeh kart harif
public class oppsCardDefuser extends specialCards {

public oppsCardDefuser(){
    this.setDuration(0);
    this.setName("oppsCardDefuser");
    this.setPrice(110);
}
    @Override
    public void run(Object param) {
        if(param instanceof Game){
            run((Game)param);
        }else{
            System.out.println("ridi");
        }
    }
    public static void run(Game game){
        Player enemy=game.getCurrentEnemy();
        ArrayList<cards> enemyCards=enemy.getPlayerCardsDeck();
        int randomIndex1=game.getRandom().nextInt(enemyCards.size());
        int randomIndex2=game.getRandom().nextInt(enemyCards.size());
        cards card1=enemyCards.get(randomIndex1);
        card1.DeCreaseDamage();
         cards card2=enemyCards.get(randomIndex2);
        card2.DeCreasePower();

    }
}
