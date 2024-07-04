package model.specialCards;

import model.cards.cards;
import model.components.Game;
import model.components.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

//copy konandeh
public class copyCat extends specialCards{
    public copyCat(){
        this.setDuration(0);
        this.setName("copyCat");
        this.setPrice(130);
    }

    @Override
    public void run(Object param) {
        if (param instanceof Game) {
            run((Game) param);
        } else {
            System.out.println("ridi");
        }
    }
    public static void run(Game game){
        Player player= game.getCurrentPlayer();
        ArrayList<cards> PlayerCards=player.getPlayerCardsDeck();
        ArrayList<specialCards> PlayerSpecialCards=player.getPlayerSpecialCardsDeck();

        System.out.println("Enter your desired card Index\n");
      cards.ShowCards(PlayerCards,PlayerSpecialCards);
        Scanner scanner=new Scanner(System.in);
        int ChosenCard=scanner.nextInt();
        if(ChosenCard <PlayerCards.size()){
            player.getPlayerCardsDeck().add(PlayerCards.get(ChosenCard-1));
        }else{
            int chosenIndex=ChosenCard-PlayerCards.size();
            player.getPlayerSpecialCardsDeck().add(PlayerSpecialCards.get(chosenIndex-1));
        }
    }
}
