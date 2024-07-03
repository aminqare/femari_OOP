package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.components.Game;
import model.components.User;
import model.components.superGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static controller.menuController.getJSONRegexMatcher;

public class GameMenuView extends menuView {
    private static superGame currentSuperGame;
    private static String pathToRegexJSON = "src/Regex/GameMenuRegex.json";

    public static void run(superGame currentGame, Scanner scanner) {
        System.out.println("Hello");
        GameMenuView.currentSuperGame = currentGame;
        currentSuperGame.setCurrentGame(new Game(currentSuperGame));
        FirstTurn(currentSuperGame.getCurrentGame());


        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject GameMenuRegexObj = regexElement.getAsJsonObject();

        while (true) {
            String command = input(scanner).trim();
            Matcher showDeck = getJSONRegexMatcher(command, "showDeck", GameMenuRegexObj);
            Matcher cartSelection = getJSONRegexMatcher(command, "cartSelection", GameMenuRegexObj);
            Matcher PlaceCard = getJSONRegexMatcher(command, "PlaceCard", GameMenuRegexObj);


            if (command.matches("\\s*exit\\s*")) {
                Output("exit");
                System.exit(0);
            } else {
                Output("invalid");
            }
        }


    }

    public static String input(Scanner scanner) {
        if (!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }

    public static void Output(String code, Object... params) {
        String pathToJSON = "src/response/MainMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }

    public void ShowGround(Game game) {
if(game.getPlayerOne().isFirstPlayer()){

}
    }

    public static void FirstTurn(Game game) {
        Double random = game.getRandom().nextDouble(1);
        if (random < 0.7) {
            game.getPlayerOne().setTurn(true);
            game.getPlayerTwo().setTurn(false);
            game.getPlayerOne().setFirstPlayer(true);
            GameMenuView.output("Turn", game.getPlayerOne().getUsername());
        } else {
            game.getPlayerOne().setTurn(false);
            game.getPlayerTwo().setTurn(true);
            game.getPlayerTwo().setFirstPlayer(true);
            GameMenuView.output("Turn", game.getPlayerTwo().getUsername());
        }
    }

    public static void output(String code, Object... params) {
        String pathToJSON = "src/response/GameMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }
}
