package model.specialCards;

import model.components.Game;
import model.components.Player;

import java.util.ArrayList;

//taaghir dahandeh makan hofreh
public class mole extends specialCards{
public mole(){
    this.setDuration(1);
    this.setName("mole");
    this.setPrice(50);
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
       ChangeHolePlace(game.getPlayerOne().getGameBoard().getBoard());
       ChangeHolePlace(game.getPlayerTwo().getGameBoard().getBoard());
    }
    public static void ChangeHolePlace(ArrayList<String> gameBoard){
        for(int i=0;i<gameBoard.size();i++){
            if(gameBoard.get(i).equals("Hole")){
                for(int j=0;j<gameBoard.size();j++){
                    if(gameBoard.get(j).equals("0")){
                        gameBoard.set(j,"Hole");
                        gameBoard.set(i,"0");
                        break;
                    }
                }
            }
        }
    }
}
