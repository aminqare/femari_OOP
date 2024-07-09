package stronghold.model.components;

import java.util.Comparator;
import java.util.Date;

public class gameHistory {
    private String opponentName;
    private String gameState;
    private int opponentLevel;
    private Date gameDate;

    public gameHistory(String opponentName, String gameState, int opponentLevel, Date gameDate) {
        this.opponentName = opponentName;
        this.gameState = gameState;
        this.opponentLevel = opponentLevel;
        this.gameDate = gameDate;
    }

    @Override
    public String toString() {
        return "opponentName='" + opponentName + '\'' +
                " opponentLevel=" + opponentLevel + "\n" +
                " gameState='" + gameState+"\'"+
                " gameDate=" + gameDate ;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public int getOpponentLevel() {
        return opponentLevel;
    }

    public void setOpponentLevel(int opponentLevel) {
        this.opponentLevel = opponentLevel;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }



}
