package stronghold.model.specialCards;

import stronghold.model.components.Game;
import stronghold.model.components.Player;

//taamr konandeh
public class builder extends specialCards{
    public builder(){
        this.setDuration(0);
        this.setName("builder");
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
    public static void run(Game game,int Index){
        Player player=game.getCurrentPlayer();
        if(player.getGameBoard().getBoard().get(Index).equals("Hole")){
            player.getGameBoard().getBoard().set(Index,"0");
        }
    }
}
