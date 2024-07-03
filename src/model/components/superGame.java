package model.components;

import java.util.Date;

public class superGame {
    private  int Round;
    private Player playerOne;
    private Player playerTwo;
    private String winner;
    private String loser;
    private Date date;
    private Game currentGame;

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public boolean isBetting() {
        return IsBetting;
    }

    public void setBetting(boolean betting) {
        IsBetting = betting;
    }

    private boolean IsBetting;
    public int getRound() {
        return this.Round;
    }

    public  void setRound(int round) {
        this.Round = round;
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

    public superGame(Player playerOne, Player playerTwo,boolean IsBetting) {
        this.Round=1;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.IsBetting=IsBetting;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void roundChanger(superGame superGame){
        Game game = new Game(superGame);
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }
}
