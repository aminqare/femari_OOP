package model.components;

import model.cards.cards;
import model.specialCards.specialCards;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private String winner;
    private String loser;
    private Player playerOne;
    private Player playerTwo;
    private gameBoard gameBoard;

    Random random = new Random();
    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        gameBoard = new gameBoard();
        addRandCards(playerOne);
        addRandCards(playerTwo);
    }
    public void addRandCards(Player player){
        ArrayList<cards> cards = player.getPlayerCards();
        ArrayList<specialCards> specialCards = player.getPlayerSpecialCards();
        for (int i = 0; i < 5; i++) {
            double randomIndex = random.nextDouble(1);
            if(randomIndex < 0.7){
                int randomIndexForCards = random.nextInt(cards.size());
                player.getPlayerCardsDeck().add(cards.get(randomIndexForCards));
            }
            else{
                int randomIndexForSpecialCards = random.nextInt(specialCards.size());
                player.getPlayerSpecialCardsDeck().add(specialCards.get(randomIndexForSpecialCards));
            }
        }
    }
}
