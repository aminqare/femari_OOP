package stronghold.model.components;
import stronghold.model.CardsDB;
import stronghold.model.cards.cards;
import stronghold.model.specialCards.specialCards;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class gameBoard {
    ArrayList<String> Board = new ArrayList<>();
    ArrayList<String> Type=new ArrayList<>();

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
    public void ShowBoard(){
        UpdateType();
        for (String s : this.Board) {
            if(s.equals("0")){
                System.out.print("\t|");
            }else if(s.equals("1")){
                System.out.print("\t|");
            }else if(s.equals("Hole")){
                System.out.print("\t|");
            }else if(s.equals("...")){
                System.out.print("\t|");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), s);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), s);
                if(card==null){
                    System.out.print("\t|");
                }else if(specialCard==null){
                    System.out.print(card.getDamage() + "|");
                }
            }
        }
        System.out.println();
        for (String s : this.Board) {
            if(s.equals("0")){
                System.out.print("\t|");
            }else if(s.equals("1")){
                System.out.print("unv|");
            }else if(s.equals("Hole")){
                System.out.print("hole|");
            }else if(s.equals("...")){
                System.out.print("...|");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(),s);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(),s);
                if(card==null){
                    System.out.print(specialCard.getName() + "|");
                }else if(specialCard==null){
                    System.out.print(card.getName() + "|");
                }
            }
        }
        System.out.println();
        for (String s : this.Board) {
            if(s.equals("0")){
                System.out.print("\t|");
            }else if(s.equals("1")){
                System.out.print("\t|");
            }else if(s.equals("Hole")){
                System.out.print("\t|");
            }else if(s.equals("...")){
                System.out.print("\t|");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), s);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), s);
                if(card==null){
                    System.out.print("\t|");
                }else if(specialCard==null){
                    System.out.print(card.getDefence() + " / "+ card.getAttack() + "|");
                }
            }
        }

    }

    public ArrayList<String> getType() {
        return Type;
    }

    public void setType(ArrayList<String> type) {
        Type = type;
    }

    public  void UpdateType(){
        ArrayList<String> TempType=new ArrayList<>();
        for (int i = 0; i < this.Board.size(); i++) {
            if(this.Board.get(i).equals("0")){
                TempType.add(i,"empty");
            }else if(this.Board.get(i).equals("1")){
                TempType.add(i,"unv");
            }else if(this.Board.get(i).equals("Hole")){
                TempType.add(i,"hole");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), Board.get(i));
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), Board.get(i));
                if(card==null){
                    TempType.add(i,"specialCards");
                }else if(specialCard==null){
                    TempType.add(i,"cards");
                }
            }
        }
        this.setType(TempType);
    }
}
