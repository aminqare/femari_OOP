package model.specialCards;

import controller.mainMenuController;
import model.components.Game;
import model.components.superGame;

//kam konandeh round
public class roundDefuser extends specialCards{
    public roundDefuser(){
        this.setDuration(0);
        this.setName("roundDefuser");
        this.setPrice(100);
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
        if(game.getCurrentEnemy().getRounds()>0)
        game.getCurrentEnemy().setRounds(game.getCurrentEnemy().getRounds()-1);
    }
}
