package model.components;

import model.cards.cards;
import model.specialCards.specialCards;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Game {
    private Player playerOne;
    private Player playerTwo;

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    private Random random = new Random();

    public Game(superGame superGame) {
        this.playerOne = superGame.getPlayerOne();
        this.playerTwo = superGame.getPlayerTwo();
        addRandCards(playerOne);
        addRandCards(playerTwo);
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public void addRandCards(Player player) {
        ArrayList<cards> cards = player.getPlayerCards();
        ArrayList<specialCards> specialCards = player.getPlayerSpecialCards();
        for (int i = 0; i < 5; i++) {
            double randomIndex = random.nextDouble(1);
            if (randomIndex < 0.7) {
                int randomIndexForCards = random.nextInt(cards.size());
                player.getPlayerCardsDeck().add(cards.get(randomIndexForCards));
            } else {
                int randomIndexForSpecialCards = random.nextInt(specialCards.size());
                player.getPlayerSpecialCardsDeck().add(specialCards.get(randomIndexForSpecialCards));
            }
        }
    }
    public  Player getCurrentPlayer(){
        if(playerOne.isTurn()){
            return playerOne;
        }else{
            return playerTwo;
        }
    }
    public Player getCurrentEnemy(){
        if(playerOne.isTurn()){
            return playerTwo;
        }else {
            return playerOne;
        }
    }

}