package model.components;

import model.cards.cards;
import model.specialCards.builder;
import model.specialCards.specialCards;
import model.utils.SpecialCardsDB;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private String password;
    private String nickname;
    private int level;
    private double gold;
    private String email;
    private int passwordRecoveryQuestion;
    private String passwordRecoveryAnswer;
    private ArrayList<cards> playerCards;

    public ArrayList<cards> getPlayerCards() {
        return playerCards;
    }

   private  SpecialCardsDB specialCardsDB;
    private List<specialCards> playerSpecialCards;

    public SpecialCardsDB getSpecialCardsDB() {
        return specialCardsDB;
    }

    private int score;

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }



    public User(String username, String password, String nickname, String email, int passwordRecoveryQuestion, String passwordRecoveryAnswer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
        this.gold=80;
        this.level=0;
        this.specialCardsDB=new SpecialCardsDB();
        this.playerSpecialCards=specialCardsDB.getCards();

    }
    public List<specialCards> getPlayerSpecialCards() {
        return playerSpecialCards;
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

    public int getRank(){
        //TODO: Get user's rank
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    public void set(User user){
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setNickname(user.getNickname());
        this.setEmail(user.getEmail());
        this.setScore(user.getScore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public User clone(){
        User cloneUser = new User(this.getUsername(),this.getPassword(),this.getNickname(),this.getEmail(),
                this.getPasswordRecoveryQuestion(),this.getPasswordRecoveryAnswer());
        cloneUser.setScore(this.getScore());
        return cloneUser;
    }
    public static void addSpecialCards(specialCards specialCards){
        SpecialCardsDB.specialCardsDB.addCards(specialCards);
        try {
            SpecialCardsDB.specialCardsDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


}
