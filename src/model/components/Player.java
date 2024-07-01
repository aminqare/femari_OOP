package model.components;

import model.cards.cards;
import model.specialCards.specialCards;

import java.util.ArrayList;

public class Player extends User{
    private double damage;
    private double HP;
    private String character;
    private int rounds;
    private ArrayList<cards> playerCardsDeck = new ArrayList<cards>();
    private ArrayList<specialCards> playerSpecialCardsDeck = new ArrayList<specialCards>();



    public Player(String username, String password, String nickname, String email, int passwordRecoveryQuestion, String passwordRecoveryAnswer) {
        super(username, password, nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer);
    }

    public Player(User user, String character) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getPasswordRecoveryQuestion(), user.getPasswordRecoveryAnswer());
        this.damage = 0;
        this.HP = 100;
        this.character = character;
        this.rounds = 4;
    }

    public ArrayList<cards> getPlayerCardsDeck() {
        return playerCardsDeck;
    }

    public void setPlayerCardsDeck(ArrayList<cards> playerCardsDeck) {
        this.playerCardsDeck = playerCardsDeck;
    }

    public ArrayList<specialCards> getPlayerSpecialCardsDeck() {
        return playerSpecialCardsDeck;
    }

    public void setPlayerSpecialCardsDeck(ArrayList<specialCards> playerSpecialCardsDeck) {
        this.playerSpecialCardsDeck = playerSpecialCardsDeck;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}
