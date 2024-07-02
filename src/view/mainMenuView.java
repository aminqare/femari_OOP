package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.profileMenuController;
import controller.signUpMenuController;
import controller.mainMenuController;
import model.UsersDB;
import model.cards.cards;
import model.components.Player;
import model.components.User;
import model.components.superGame;
import model.specialCards.builder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

import model.specialCards.builder;
import model.utils.StringParser;

import static controller.menuController.getJSONRegexMatcher;
import static controller.menuController.usernameExists;
import static controller.signUpMenuController.authenticate;
import static controller.signUpMenuController.setCurrentUser;

public class mainMenuView extends menuView {

    private static String pathToRegexJSON = "src/Regex/MainMenuRegex.json";

    public static void run(User currentUser, Scanner scanner) {
        int failedAttempts = 0;
        long nextAttemptTime = 0;

        mainMenuController.welcome(currentUser);

        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject MainMenuRegexObj = regexElement.getAsJsonObject();

        if (currentUser.getLevel() == 0) {
            mainMenuController.StartPack(new Random(), currentUser);
            currentUser.setLevel(currentUser.getLevel() + 1);
            updateDB(currentUser);
            System.out.println("start pack");
        }

        while (true) {
            long currentTime = System.currentTimeMillis();
            String command = input(scanner).trim();


            Matcher loadGameMatcher = getJSONRegexMatcher(command, "loadGame", MainMenuRegexObj);
            Matcher profileMenuMatcher = getJSONRegexMatcher(command, "profileMenu", MainMenuRegexObj);
            Matcher ShowCardsMatcher = getJSONRegexMatcher(command, "showCards", MainMenuRegexObj);
            Matcher startNewGame = getJSONRegexMatcher(command, "enterGameMenu", MainMenuRegexObj);
            Matcher enterShopMenu = getJSONRegexMatcher(command, "enterShopMenu", MainMenuRegexObj);

            if (command.matches("\\s*exit\\s*")) {
                Output("exit");
                System.exit(0);
            } else if (command.matches("user\\s+logout")) {

                mainMenuView.Output("logout");
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

            } else if (profileMenuMatcher.find()) {
                profileMenuView.run(scanner, currentUser);

            } else if (loadGameMatcher.find()) {


            } else if (ShowCardsMatcher.find()) {
                Output("ShowCards", currentUser.getPlayerCards().toString(), currentUser.getPlayerSpecialCards().toString());

            } else if (startNewGame.find()) {

                System.out.println("riiiidiii");


                String username = StringParser.removeQuotes(startNewGame.group("username"));
                String password = StringParser.removeQuotes(startNewGame.group("pass"));

                if (username == null) {
                    signUpLoginView.output("registerusername404");
                    continue;
                } else if (password == null) {
                    signUpLoginView.output("registerpassword404");
                    continue;
                } else if (!usernameExists(username)) {
                    signUpLoginView.output("usernotfound");
                } else if(currentUser.getUsername().equals(username)){
                    Output("sameUser");
                }else {

                    boolean userAuthenticated = authenticate(username, password);

                    if (!userAuthenticated) {
                        if (currentTime < nextAttemptTime) {
                            long waitTime = (nextAttemptTime - currentTime) / 1000;
                            System.out.println("Try again in " + waitTime + " seconds");

                        } else {
                            failedAttempts++;
                            signUpLoginView.output("unmatchingpassanduser");
                            nextAttemptTime = System.currentTimeMillis() + (long) failedAttempts * 5 * 1000;
                            System.out.println("Try again in " + 5 * failedAttempts + " seconds");
                        }
                        continue;

                    } else {
                        User otherUser = UsersDB.usersDB.getUserByUsername(username);
                        Player playerOne;
                        Player otherPlayer;
                        mainMenuView.Output("OtherUserLogin");
                      playerOne= chooseCharacter("One",scanner,currentUser);
                     otherPlayer=  chooseCharacter("Two",scanner,otherUser);
                        superGame superGame = new superGame(playerOne, otherPlayer);
                        GameMenuView.run(superGame, scanner);

                    }
                }

            } else {
                Output("invalid");
            }
        }

    }

    public static void Output(String code, Object... params) {
        String pathToJSON = "src/response/MainMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner) {
        if (!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }
    public static Player chooseCharacter(String PlayerNumber,Scanner scanner,User currentUser){
        while(true){
            Output("ChoseCharacter"+PlayerNumber);
            String character = scanner.nextLine();
            if (character.equals("Amin") || character.equals("Mobina") || character.equals("Moca") || character.equals("Nane kian")) {
                return new Player(currentUser, character);
            } else {
                Output("InvalidCharacter");
                continue;
            }
        }
    }

    public static void updateDB(User currentUser) {
        UsersDB.usersDB.update(currentUser);
        try {
            UsersDB.usersDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


}