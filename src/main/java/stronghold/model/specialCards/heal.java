package stronghold.model.specialCards;

import stronghold.model.components.Game;
import stronghold.model.components.Player;
import stronghold.model.components.gameBoard;

import java.util.ArrayList;

public class heal extends specialCards{
public heal(){
    this.setDuration(1);
    this.setName("heal");
    this.setPrice(90);
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
        Player player=game.getCurrentPlayer();
        //player.IncreaseHP();
    }
}
