package stronghold.model.components;

import stronghold.model.cards.cards;
import stronghold.model.specialCards.specialCards;

import java.util.ArrayList;

public class Player extends User{
    private double damage;
    private boolean IsFirstPlayer=false;

    public boolean isFirstPlayer() {
        return IsFirstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        IsFirstPlayer = firstPlayer;
    }

    private double HP;
    private String character;
    private int rounds;
    private User user;
    private ArrayList<cards> playerCardsDeck;
    private ArrayList<specialCards> playerSpecialCardsDeck;
    private boolean IsTurn=false;
    private gameBoard gameBoard;

    public boolean isHasMehradHidden() {
        return hasMehradHidden;
    }

    public void setHasMehradHidden(boolean hasMehradHidden) {
        this.hasMehradHidden = hasMehradHidden;
    }

    private boolean hasMehradHidden=false;

    public boolean isTurn() {
        return IsTurn;
    }

    public stronghold.model.components.gameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(stronghold.model.components.gameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setTurn(boolean turn) {
        IsTurn = turn;
    }

    public Player(String username, String password, String nickname, String email, int passwordRecoveryQuestion, String passwordRecoveryAnswer) {
        super(username, password, nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer);
    }

    public Player(User user, String character) {
        super(user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getPasswordRecoveryQuestion(), user.getPasswordRecoveryAnswer());
        this.damage = 0;
        this.HP = 20;
        this.character = character;
        this.rounds = 2;
        this.user = user;
        this.setPlayerCards(user.getPlayerCards());
        this.setPlayerSpecialCards(user.getPlayerSpecialCards());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    public void IncreaseHP(){
        this.setHP(this.getHP()*1.5);
    }

}
