package model.components;
import model.CardsDB;
import model.cards.cards;
import model.specialCards.specialCards;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class gameBoard {
    ArrayList<String> Board = new ArrayList<>();
    ArrayList<String> Type=new ArrayList<>(21);

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

    public void UpdateType(){
        for (int i = 0; i < this.Board.size(); i++) {
            if(Board.get(i).equals("0")){
                Type.set(i,"empty");
            }else if(Board.get(i).equals("1")){
                Type.set(i,"unv");
            }else if(Board.get(i).equals("Hole")){
                Type.set(i,"hole");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), Board.get(i));
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), Board.get(i));
                if(card==null){
                    Type.set(i,"specialCards");
                }else if(specialCard==null){
                    Type.set(i,"cards");
                }
            }
        }
    }
}
