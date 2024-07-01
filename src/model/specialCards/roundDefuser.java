package model.specialCards;

import controller.mainMenuController;
import model.components.Game;

//kam konandeh round
public class roundDefuser extends specialCards{
    public roundDefuser(){
        this.setDuration(0);
        this.setName("roundDefuser");
        this.setPrice(100);
    }

    @Override
    void run(Object param) {
        if(param instanceof Game){
            run((Game)param);
        }else{
            System.out.println("ridi");
        }
    }
    public void run(Game game){
        if(mainMenuController.getRound()>0)
        mainMenuController.setRound(mainMenuController.getRound()-1);
    }
}
