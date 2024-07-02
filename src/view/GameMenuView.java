package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.components.User;
import model.components.superGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameMenuView extends menuView {
    private static superGame currentGame;
    private static String pathToRegexJSON = "src/Regex/GameMenuRegex.json";

    public static void run(superGame currentGame, Scanner scanner) {
        System.out.println("Hello");
        GameMenuView.currentGame = currentGame;

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



            if (command.matches("\\s*exit\\s*")) {
                Output("exit");
                System.exit(0);
            }else {
                Output("invalid");
            }
        }



    }
    public static String input (Scanner scanner){
        if (!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }
    public static void Output(String code, Object... params) {
        String pathToJSON = "src/response/MainMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }
}
