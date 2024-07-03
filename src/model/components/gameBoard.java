package model.components;
import model.CardsDB;
import model.cards.cards;
import model.specialCards.specialCards;

import java.util.ArrayList;
import java.util.Random;

public class gameBoard {
    ArrayList<String> Board = new ArrayList<>();

    public gameBoard() {
        Random random = new Random();
        Integer playerRandomIndex = random.nextInt(21);
        for (int i = 0; i < 21; i++) {
            if(i != playerRandomIndex){
            Board.add( "0");
            }else {
                Board.add("1");
            }
        }

    }

    public ArrayList<String> getBoard() {
        return Board;
    }

    public void setBoard(ArrayList<String> playerGameBoard) {
        Board = playerGameBoard;
    }
    public StringBuilder ShowBoard(){
        StringBuilder output=new StringBuilder();
        String Temp;
        Temp="|\n|\n|";
        output.append(Temp );
        for (String s : this.Board) {
            if(s.equals("0")){
                output.append("\t|\n\t|\n\t|");
            }else if(s.equals("1")){
                output.append("\t|\nunavailable spot|\n\t|");
            }else if(s.equals("Hole")){
                output.append("\t|\nHole|\n\t|");
            }else if(s.equals("...")){
                output.append("\t|\n\t|\n\t|");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), s);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), s);
                if(card==null){
                    output.append("\t|\n"+specialCard.getName()+"|\n\t|");
                }else if(specialCard==null){
                    output.app
                }
            }
        }
    }
}
