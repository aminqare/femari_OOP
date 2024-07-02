package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.AdminMenuController;
import controller.shopMenuController;
import model.CardsDB;
import model.cards.cards;
import model.components.User;
import model.specialCards.*;
import model.utils.SpecialCardsDB;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

import static controller.menuController.getJSONRegexMatcher;

public class shopMenuView {
    private static String pathToRegexJSON = "src/Regex/ShopMenuRegex.json";

    public static void run(Scanner scanner, User currentUser){
        boolean added = false;
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

            Matcher showCards =
                    getJSONRegexMatcher(input, "showCards", menuRegexPatternsObject);
            Matcher selectionUpdate =
                    getJSONRegexMatcher(input, "selectionUpdate" , menuRegexPatternsObject);
            Matcher selectionBuy =
                    getJSONRegexMatcher(input, "selectionBuy" , menuRegexPatternsObject);;
            if(showCards.find()){
                shopMenuController.ShowCards(CardsDB.cardsDB.getCards(), specialCards.getGameSpecialCards());
            }
            else if(selectionUpdate.find()){
                String name = selectionUpdate.group("name");
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), name);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), name);
                if(card == null){
                    if(User.GetSpecialCardByName(currentUser.getPlayerSpecialCards(), name) == null){
                        shopMenuView.output("cardNull");
                    }
                    else{
                        if(specialCard.getPrice() > currentUser.getGold()){
                            shopMenuView.output("notEnoughMoney");
                        }
                        else{
                            specialCard.setLevel(specialCard.getLevel()+1);
                            currentUser.setGold(currentUser.getGold() - specialCard.getPrice());
                            shopMenuView.output("successfulUpdate");
                        }
                    }
                }
                else if(specialCard == null){
                    if(User.GetCardByName(currentUser.getPlayerCards(), name) == null){
                        shopMenuView.output("cardNull");
                    }
                    else{
                        if(card.getUpgradeCost() > currentUser.getGold()){
                            shopMenuView.output("notEnoughMoney");
                        }
                        else{
                            card.powerUp();
                            card.setLevel(card.getLevel() + 1);
                            currentUser.setGold(currentUser.getGold() - card.getUpgradeCost());
                            shopMenuView.output("successfulUpdate");
                        }
                    }
                }
                else{
                    shopMenuView.output("invalid");
                }
            }
            else if(selectionBuy.find()){
                String name = selectionBuy.group("name");
                cards card = cards.GetCardByName(CardsDB.cardsDB.getCards(), name);
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), name);
                if(card == null){
                    if(User.GetSpecialCardByName(currentUser.getPlayerSpecialCards(), name) != null){
                        shopMenuView.output("cardOnDeck");
                    }
                    else{
                        if(specialCard.getPrice() > currentUser.getGold()){
                            shopMenuView.output("notEnoughMoney");
                        }
                        else{
                            currentUser.getPlayerSpecialCards().add(specialCard);
                            currentUser.setGold(currentUser.getGold() - specialCard.getPrice());
                            shopMenuView.output("successfulPurchase");
                        }
                    }
                }
                else if(specialCard == null){
                    if(User.GetCardByName(currentUser.getPlayerCards(), name) == null){
                        shopMenuView.output("cardOnDeck");
                    }
                    else{
                        if(card.getPrice() > currentUser.getGold()){
                            shopMenuView.output("notEnoughMoney");
                        }
                        else{
                            currentUser.getPlayerCards().add(card);
                            currentUser.setGold(currentUser.getGold() - card.getPrice());
                            shopMenuView.output("successfulPurchase");
                        }
                    }
                }
                else{
                    shopMenuView.output("invalid");
                }
            }
            else{
                shopMenuView.output("invalid");
            }
        }
    }
    public static void output(String code, Object... params){
        String pathToJSON = "src/response/ShopMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }
}
