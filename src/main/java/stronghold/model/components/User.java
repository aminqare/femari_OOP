package stronghold.model.components;

import stronghold.model.cards.cards;
import stronghold.model.specialCards.specialCards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class User implements Serializable {
    protected String username;
    protected String password;
    protected String nickname;
    protected int level;
    protected double gold;
    protected String email;
    protected int passwordRecoveryQuestion;
    protected String passwordRecoveryAnswer;
    protected ArrayList<cards> playerCards;
    protected ArrayList<specialCards> playerSpecialCards;
    protected ArrayList<gameHistory> gameHistory;
    protected int score;
    protected int XP;
    protected int maxXP;

    public User(String username, String password, String nickname, String email, int passwordRecoveryQuestion, String passwordRecoveryAnswer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
        this.playerCards = new ArrayList<>();
        this.playerSpecialCards = new ArrayList<>();
        this.gold = 80;
        this.level = 0;
        this.score = 0;
        this.XP = 0;
        this.setGameHistory(new ArrayList<>());
    }

    public ArrayList<cards> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(ArrayList<cards> playerCards) {
        this.playerCards = playerCards;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getMaxXP() {
        return maxXP;
    }

    public void setMaxXP() {
        this.maxXP = this.level * 120;
    }

    public ArrayList<specialCards> getPlayerSpecialCards() {
        return playerSpecialCards;
    }

    public void setPlayerSpecialCards(ArrayList<specialCards> playerSpecialCards) {
        this.playerSpecialCards = playerSpecialCards;
    }
    public static cards GetCardByName(ArrayList<cards> cards, String name){
        for (cards card : cards) {
            if(card.getName().equals(name)){
                return card;
            }
        }
        return null;

    }
    public static specialCards GetSpecialCardByName(ArrayList<specialCards> cards, String name){
        for (specialCards card : cards) {
            if(card.getName().equals(name)){
                return card;
            }
        }
        return null;

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPasswordRecoveryQuestion() {
        return passwordRecoveryQuestion;
    }

    public void setPasswordRecoveryQuestion(int passwordRecoveryQuestion) {
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
    }

    public String getPasswordRecoveryAnswer() {
        return passwordRecoveryAnswer;
    }

    public void setPasswordRecoveryAnswer(String passwordRecoveryAnswer) {
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        //TODO: Get user's rank
        return -1;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public User clone() {
        User cloneUser = new User(this.getUsername(), this.getPassword(), this.getNickname(), this.getEmail(),
                this.getPasswordRecoveryQuestion(), this.getPasswordRecoveryAnswer());
        cloneUser.setScore(this.getScore());
        return cloneUser;
    }

    public ArrayList<stronghold.model.components.gameHistory> getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(ArrayList<stronghold.model.components.gameHistory> gameHistory) {
        this.gameHistory = gameHistory;
    }

    public void set(User user){
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setNickname(user.getNickname());
        this.setEmail(user.getEmail());
        this.setScore(user.getScore());
        this.setPlayerSpecialCards(user.getPlayerSpecialCards());
    }
}
