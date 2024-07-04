package view;

import model.components.Player;
import model.components.superGame;

import java.util.Scanner;

public class endGameView {
    private static superGame currentSuperGame;
    private static String pathToRegexJSON = "src/Regex/endGameMenuRegex.json";


    public static void run(superGame currentSuperGame, Scanner scanner, Player player){
        System.out.println("Game has ended");
        currentSuperGame.setWinner(player.getUsername());
    }
}
