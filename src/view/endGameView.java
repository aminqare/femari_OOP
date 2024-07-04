package view;

import model.UsersDB;
import model.components.*;

import java.io.IOException;
import java.util.Scanner;

public class endGameView {
    private static superGame currentSuperGame;
    private static String pathToRegexJSON = "src/Regex/endGameMenuRegex.json";


    public static void run(superGame currentSuperGame, Scanner scanner, Player player){
        currentSuperGame.setWinner(player.getUsername());
        User winner = player.getUser();
        User loser;
        if(winner == currentSuperGame.getPlayerOne().getUser()){
            loser = currentSuperGame.getPlayerTwo().getUser();
        }
        else{
            loser = currentSuperGame.getPlayerOne().getUser();
        }
        endGameView.Output("gameFinished", winner.getUsername(), loser.getUsername());
        double prizeIndex = ((player.getHP())*0.1 - ((player.getLevel()) - loser.getLevel()) * 0.5);
        int givenXP = (int)prizeIndex;
        int givenGold = (int)(prizeIndex * 10);
        int loserXP = 50;
        int loserGold = 20;
        winner.setXP(winner.getXP() + givenXP);
        winner.setGold(winner.getGold() + givenGold);
        loser.setGold(loser.getGold() + loserGold);
        loser.setXP(loser.getXP() + loserXP);
        if(currentSuperGame.isBetting()){
            winner.setGold(winner.getGold() + 50);
            loser.setGold(loser.getGold() - 50);
        }
        gameHistory gameHistoryWinner = new gameHistory(loser.getUsername(), "Won", loser.getLevel(), currentSuperGame.getDate());
        gameHistory gameHistoryLoser = new gameHistory(winner.getUsername(), "Lost", winner.getLevel(), currentSuperGame.getDate());
        winner.getGameHistory().add(gameHistoryWinner);
        loser.getGameHistory().add(gameHistoryLoser);
        levelUp(winner);
        levelUp(loser);
        updateDB(loser);
        updateDB(winner);
        mainMenuView.run(currentSuperGame.getPlayerOne().getUser(), scanner);
    }
    public static void levelUp(User user){
        int prevLevel = user.getLevel();
        user.setMaxXP();
        while(user.getXP() > user.getMaxXP()){
            user.setMaxXP();
            user.setXP(user.getXP() - user.getMaxXP());
            user.setLevel(user.getLevel() + 1);
        }
        int finalLevel = user.getLevel();
        if(finalLevel > user.getLevel()){
            endGameView.Output("levelUp", user.getUsername(), String.valueOf(finalLevel));
        }
    }
    public static void Output(String code, Object... params) {
        String pathToJSON = "src/response/endGameMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }
    public static void updateDB (User currentUser){
        UsersDB.usersDB.update(currentUser);
        try {
            UsersDB.usersDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

}
