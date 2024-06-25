package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.profileMenuController;
import controller.signUpMenuController;
import controller.mainMenuController;
import model.cards.cards;
import model.components.User;
import model.specialCards.builder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

import model.specialCards.builder;

import static controller.menuController.getJSONRegexMatcher;

public class mainMenuView extends menuView {

    private static String pathToRegexJSON = "src/Regex/MainMenuRegex.json";

    public static void run(User currentUser, Scanner scanner) {


        mainMenuController.welcome(currentUser);

        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject MainMenuRegexObj = regexElement.getAsJsonObject();

//        if (currentUser.getLevel() == 0) {
//            mainMenuController.StartPack(new Random(), currentUser);
//            System.out.println("start pack");
//            currentUser.setLevel(1);
//        }

        while (true) {
            String command = input(scanner).trim();

            Matcher startGameMatcher = getJSONRegexMatcher(command, "startNewGame", MainMenuRegexObj);
            Matcher loadGameMatcher = getJSONRegexMatcher(command, "loadGame", MainMenuRegexObj);
            Matcher profileMenuMatcher = getJSONRegexMatcher(command, "profileMenu", MainMenuRegexObj);

            if (command.matches("\\s*exit\\s*")) {
                mainMenuView.output("exit");
                System.exit(0);
            } else if (command.matches("user\\s+logout")) {

                mainMenuView.output("logout");
                JsonElement prefsElement;
                String pathToPrefs = "src/database/preferences.json";
                try {
                    prefsElement = JsonParser.parseReader(
                            new FileReader(pathToPrefs));
                } catch (
                        FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                try {
                    String toBeWritten = prefsElement.toString();
                    toBeWritten = toBeWritten.replace(currentUser.getUsername(), "!NULLUSER");
                    FileWriter fileWriter = new FileWriter(pathToPrefs);
                    fileWriter.write(toBeWritten);
                    fileWriter.close();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                signUpLoginView.run(scanner);
                break;

            }
//            else if ((startGameMatcher = getJSONRegexMatcher(command, "startNewGame", MainMenuRegexObj)).matches()){
//                int i=Integer.parseInt(startGameMatcher.group("opponent"));
//                int j=Integer.parseInt(startGameMatcher.group("rounds"));
//                Scanner scanner1=new Scanner(System.in);
//                System.out.println("enter map size:");
//                int mapSize=scanner1.nextInt();
//
//
//                gameMenuView.run( scanner, j,i,mapSize);
//
//            }
            else if (profileMenuMatcher.find()) {
                profileMenuView.run(scanner, currentUser);

            } else if (loadGameMatcher.find()) {


            } else {
                output("invalid");
            }
        }

    }

    public static void output(String code, Object... params) {
        String pathToJSON = "src/response/MainMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner) {
        if (!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }


}