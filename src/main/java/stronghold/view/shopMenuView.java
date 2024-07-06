package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.controller.AdminMenuController;
import stronghold.controller.shopMenuController;
import stronghold.model.CardsDB;
import stronghold.model.UsersDB;
import stronghold.model.cards.cards;
import stronghold.model.components.User;
import stronghold.model.specialCards.*;
import stronghold.model.utils.SpecialCardsDB;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.menuController.getJSONRegexMatcher;

public class shopMenuView {
    private static String pathToRegexJSON = "src/main/java/stronghold/Regex/ShopMenuRegex.json";

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
            if(Objects.equals(input, "back")){
                mainMenuView.run(currentUser,scanner);
            }
            else if(showCards.find()){
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
                            User.GetSpecialCardByName(currentUser.getPlayerSpecialCards(),name)
                                    .setLevel(specialCard.getLevel()+1);
                            currentUser.setGold(currentUser.getGold() - specialCard.getPrice());
                            updateDB(currentUser);
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
                            User.GetCardByName(currentUser.getPlayerCards(), name).powerUp();
                            User.GetCardByName(currentUser.getPlayerCards(), name).setLevel(card.getLevel() + 1);
                            currentUser.setGold(currentUser.getGold() - card.getUpgradeCost());
                            updateDB(currentUser);
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
                            updateDB(currentUser);
                            shopMenuView.output("successfulPurchase");
                        }
                    }
                }
                else if(specialCard == null){
                    if(User.GetCardByName(currentUser.getPlayerCards(), name) != null){
                        shopMenuView.output("cardOnDeck");
                    }
                    else{
                        if(card.getPrice() > currentUser.getGold()){
                            shopMenuView.output("notEnoughMoney");
                        }
                        else{
                            currentUser.getPlayerCards().add(card);
                            currentUser.setGold(currentUser.getGold() - card.getPrice());
                            updateDB(currentUser);
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
        String pathToJSON = "src/main/java/stronghold/response/ShopMenuResponses.json";
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
