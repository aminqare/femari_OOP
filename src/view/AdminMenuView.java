package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.AdminMenuController;
import model.CardsDB;
import model.components.User;
import model.utils.StringParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import static controller.AdminMenuController.nameExists;
import model.cards.cards;
import static controller.menuController.getJSONRegexMatcher;

public class AdminMenuView {
    private static String pathToRegexJSON = "src/Regex/AdminMenuRegex.json";

    public static void run(Scanner scanner){

        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();
        while(true){
        String input = signUpLoginView.input(scanner);
            Matcher back =
                    getJSONRegexMatcher(input, "back", menuRegexPatternsObject);
            Matcher addCard =
                    getJSONRegexMatcher(input, "addCard" , menuRegexPatternsObject);
            Matcher editCards =
                    getJSONRegexMatcher(input, "editCards" , menuRegexPatternsObject);;
            Matcher removeCards =
                    getJSONRegexMatcher(input, "removeCards" , menuRegexPatternsObject);;
            Matcher showPlayers =
                    getJSONRegexMatcher(input, "showPlayers" , menuRegexPatternsObject);;
            Matcher confirm =
                    getJSONRegexMatcher(input, "confirm" , menuRegexPatternsObject);;
            Matcher disconfirm =
                    getJSONRegexMatcher(input, "disconfirm" , menuRegexPatternsObject);;
            Matcher selection =
                    getJSONRegexMatcher(input, "selection" , menuRegexPatternsObject);;
            System.out.println("LICK LICK LICK MY BALLS");
            if(back.find()){
                signUpLoginView.run(scanner);
            }
            else if(addCard.find()){
                String name = StringParser.removeQuotes(addCard.group("name"));
                String cardDefence = StringParser.removeQuotes(addCard.group("cardDefence"));
                String cardAttack = StringParser.removeQuotes(addCard.group("cardAttack"));
                String duration = StringParser.removeQuotes(addCard.group("duration"));
                String damage = StringParser.removeQuotes(addCard.group("damage"));
                String upgradeLevel = StringParser.removeQuotes(addCard.group("upgradeLevel"));
                String upgradeCost = StringParser.removeQuotes(addCard.group("upCost"));
                System.out.println("AW GEEZ RICK");
                if(nameExists(name)){
                    AdminMenuView.output("cardNameExits");
                    continue;
                }
                if (Integer.parseInt(cardDefence) > 100 || Integer.parseInt(cardDefence) < 10){
                    AdminMenuView.output("errorHandling");
                    continue;
                }
                if (Integer.parseInt(cardAttack) > 100 || Integer.parseInt(cardAttack) < 10){
                    AdminMenuView.output("errorHandling");
                    continue;
                }
                if (Integer.parseInt(duration) > 5 || Integer.parseInt(duration) < 1){
                    AdminMenuView.output("errorHandling");
                    continue;
                }
                if (Integer.parseInt(damage) > 50 || Integer.parseInt(damage) < 10){
                    AdminMenuView.output("errorHandling");
                    continue;
                }
                AdminMenuController.addCards(name, Integer.parseInt(cardDefence), Double.parseDouble(cardAttack),
                        Integer.parseInt(duration), Double.parseDouble(damage),
                        Integer.parseInt(upgradeLevel), Double.parseDouble(upgradeCost));
                AdminMenuView.output("cardAdded");
            }
            else if(editCards.find()){
                for (int i = 0; i < CardsDB.cardsDB.getCards().size(); i++) {
                    String cardName = CardsDB.cardsDB.getCards().get(i).getName();
                    System.out.println(i+1 + ". " + cardName);
                }
            }
            else if(selection.find()){
                String selectionNum = StringParser.removeQuotes(selection.group("selection"));
                cards card = CardsDB.cardsDB.getCards().get(Integer.parseInt(selectionNum));
                AdminMenuView.output("showCards", card.getName(), String.valueOf(card.getDefence()),
                        String.valueOf(card.getAttack()), String.valueOf(card.getDuration())
                , String.valueOf(card.getDamage()), String.valueOf(card.getLevel()), String.valueOf(card.getUpgradeCost()));
            }
        }
    }
    public static void output(String code, Object... params){
        String pathToJSON = "src/response/AdminMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }


}
