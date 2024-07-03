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
    public void ShowBoard(){
        for (String s : this.Board) {
            if(s.equals("0")){
                System.out.print("\t| ");
            }else if(s.equals("1")){
                System.out.print("\t| ");
            }else if(s.equals("Hole")){
                System.out.print("\t| ");
            }else if(s.equals("...")){
                System.out.print("\t| ");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), s);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), s);
                if(card==null){
                    System.out.print("\t| ");
                }else if(specialCard==null){
                    System.out.print(card.getDamage() + "| ");
                }
            }
        }
        System.out.println();
        for (String s : this.Board) {
            if(s.equals("0")){
                System.out.print("empty slot| ");
            }else if(s.equals("1")){
                System.out.print("unavailable slot| ");
            }else if(s.equals("Hole")){
                System.out.print("hole| ");
            }else if(s.equals("...")){
                System.out.print("filled with prev card| ");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), s);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), s);
                if(card==null){
                    System.out.print(specialCard.getName() + "| ");
                }else if(specialCard==null){
                    System.out.print(card.getName() + "| ");
                }
            }
        }
        System.out.println();
        for (String s : this.Board) {
            if(s.equals("0")){
                System.out.print("\t| ");
            }else if(s.equals("1")){
                System.out.print("\t| ");
            }else if(s.equals("Hole")){
                System.out.print("\t| ");
            }else if(s.equals("...")){
                System.out.print("\t| ");
            }else{
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), s);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), s);
                if(card==null){
                    System.out.print("\t| ");
                }else if(specialCard==null){
                    System.out.print(card.getDefence() + " / "+ card.getAttack() + "| ");
                }
            }
        }
    }
}
