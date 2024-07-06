package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.controller.AdminMenuController;
import stronghold.model.CardsDB;
import stronghold.model.UsersDB;
import stronghold.model.components.User;
import stronghold.model.utils.StringParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.AdminMenuController.nameExists;
import stronghold.model.cards.cards;

import static stronghold.controller.AdminMenuController.updateDB;
import static stronghold.controller.menuController.getJSONRegexMatcher;
import static java.lang.String.valueOf;

public class AdminMenuView {
    private static String pathToRegexJSON = "src/main/java/stronghold/Regex/AdminMenuRegex.json";

    public static void run(Scanner scanner){
        int selected = -1;
        boolean nameB = false;
        String nameS = null;
        boolean defence = false;
        String defenceS = null;
        boolean attack = false;
        String attackS = null;
        boolean durationB = false;
        String durationS = null;
        boolean damageB = false;
        String damageS = null;
        boolean upLevel = false;
        String upLevelS = null;
        boolean upCost = false;
        String upCostS = null;
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
            Matcher confirmEdit =
                    getJSONRegexMatcher(input, "confirmEdit" , menuRegexPatternsObject);;
            Matcher confirmRemove =
                    getJSONRegexMatcher(input, "confirmRemove", menuRegexPatternsObject);;
            Matcher disconfirm =
                    getJSONRegexMatcher(input, "disconfirm" , menuRegexPatternsObject);;
            Matcher selectionEdit =
                    getJSONRegexMatcher(input, "selectionEdit" , menuRegexPatternsObject);;
            Matcher selectionRemove =
                    getJSONRegexMatcher(input, "selectionRemove" , menuRegexPatternsObject);;
            Matcher changeNameMatcher =
                    getJSONRegexMatcher(input, "nameChange", menuRegexPatternsObject);
            Matcher changeDefenceMatcher =
                    getJSONRegexMatcher(input, "defenceChange" , menuRegexPatternsObject);
            Matcher changeAttackMatcher =
                    getJSONRegexMatcher(input, "attackChange" , menuRegexPatternsObject);;
            Matcher changeDurationMatcher =
                    getJSONRegexMatcher(input, "durationChange" , menuRegexPatternsObject);;
            Matcher changeDamageMatcher =
                    getJSONRegexMatcher(input, "damageChange" , menuRegexPatternsObject);;
            Matcher changeUpgradeLevelMatcher =
                    getJSONRegexMatcher(input, "upgradeLevelChange" , menuRegexPatternsObject);;
            Matcher changeUpCostMatcher =
                    getJSONRegexMatcher(input, "upCostChange" , menuRegexPatternsObject);;
            // System.out.println("LICK LICK LICK MY BALLS");
            if(back.find()){
                signUpLoginView.run(scanner);
                break;
            }
            else if(addCard.find()){
                System.out.println(0);
                String name = StringParser.removeQuotes(addCard.group("name"));
                String cardDefence = StringParser.removeQuotes(addCard.group("cardDefence"));
                String cardAttack = StringParser.removeQuotes(addCard.group("cardAttack"));
                String duration = StringParser.removeQuotes(addCard.group("duration"));
                String damage = StringParser.removeQuotes(addCard.group("damage"));
                String upgradeLevel = StringParser.removeQuotes(addCard.group("upgradeLevel"));
                String upgradeCost = StringParser.removeQuotes(addCard.group("upCost"));
                //System.out.println("AW GEEZ RICK");
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
            else if(selectionRemove.find()){
                AdminMenuView.output("deleteCard");
                selected = Integer.parseInt(selectionRemove.group("selection")) - 1;
            }
            else if(confirmRemove.find()){
                cards card = CardsDB.cardsDB.getCards().get(selected);
                CardsDB.cardsDB.removeCardsByName(card.getName());
                updateDB(card);
                selected = -1;
            }
            else if(selectionEdit.find()){
                String selectionNum = StringParser.removeQuotes(selectionEdit.group("selection"));
                cards card = CardsDB.cardsDB.getCards().get(Integer.parseInt(selectionNum) - 1);
                selected = Integer.parseInt(selectionNum) - 1;
                AdminMenuView.output("showCards", card.getName(), valueOf(card.getDefence()),
                        valueOf(card.getAttack()), valueOf(card.getDuration())
                        , valueOf(card.getDamage()), valueOf(card.getLevel()), valueOf(card.getUpgradeCost()));
            }
            else if(changeNameMatcher.find() && selected != -1){
                nameS = changeNameMatcher.group("name");
                nameB = true;
                AdminMenuView.output("editCard");
            }
            else if(changeDefenceMatcher.find() && selected != -1){
                defenceS = changeDefenceMatcher.group("cardDefence");
                defence = true;
                AdminMenuView.output("editCard");
            }
            else if(changeAttackMatcher.find() && selected != -1){
                attackS = changeAttackMatcher.group("cardAttack");
                attack = true;
                AdminMenuView.output("editCard");
            }
            else if(changeDurationMatcher.find() && selected != -1){
                durationS = changeDurationMatcher.group("duration");
                durationB = true;
                AdminMenuView.output("editCard");
            }
            else if(changeDamageMatcher.find() && selected != -1){
                damageS = changeDamageMatcher.group("damage");
                damageB = true;
                AdminMenuView.output("editCard");
            }
            else if(changeUpgradeLevelMatcher.find() && selected != -1){
                upLevelS = changeUpgradeLevelMatcher.group("upgradeLevel");
                upLevel = true;
                AdminMenuView.output("editCard");
            }
            else if(changeUpCostMatcher.find() && selected != -1){
                upCostS = changeUpCostMatcher.group("upCost");
                upCost = true;
                AdminMenuView.output("editCard");
            }
            else if(disconfirm.find()){
                selected = -1; // reset selected index
                nameB = false;
                defence = false;
                attack = false;
                durationB = false;
                damageB = false;
                upLevel = false;
                upCost = false;
                continue;
            }
            else if(confirmEdit.find()){
                System.out.println(1);
                if (selected == -1) {
                    AdminMenuView.output("errorHandling");
                    continue;
                }
                cards card = CardsDB.cardsDB.getCards().get(selected);
                if(nameB){
                    if(nameExists(nameS)){
                        AdminMenuView.output("cardNameExits");
                        continue;
                    }
                    card.setName(nameS);
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    nameB = false;
                }
                if(defence){
                    if (Double.parseDouble(defenceS) > 100 || Double.parseDouble(defenceS) < 10){
                        AdminMenuView.output("errorHandling");
                        continue;
                    }
                    card.setDefence(Double.parseDouble(defenceS));
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    defence = false;
                }
                if(attack){
                    if (Double.parseDouble(attackS) > 100 || Double.parseDouble(attackS) < 10){
                        AdminMenuView.output("errorHandling");
                        continue;
                    }
                    card.setAttack(Double.parseDouble(attackS));
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    attack = false;
                }
                if(durationB){
                    if (Integer.parseInt(durationS) > 5 || Integer.parseInt(durationS) < 1){
                        AdminMenuView.output("errorHandling");
                        continue;
                    }
                    card.setDuration(Integer.parseInt(durationS));
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    durationB = false;
                }
                if(damageB){
                    if (Integer.parseInt(damageS) > 50 || Integer.parseInt(damageS) < 10){
                        AdminMenuView.output("errorHandling");
                        continue;
                    }
                    card.setDamage(Double.parseDouble(damageS));
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    damageB = false;
                }
                if(upLevel){
                    card.setLevel(Integer.parseInt(upLevelS));
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    upLevel = false;
                }
                if(upCost){
                    card.setUpgradeCost(Double.parseDouble(upCostS));
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    upCost = false;
                }
                selected = -1; // reset selected index after editing
            }
            else if(removeCards.find()){
                for (int i = 0; i < CardsDB.cardsDB.getCards().size(); i++) {
                    String cardName = CardsDB.cardsDB.getCards().get(i).getName();
                    System.out.println(i+1 + ". " + cardName);
                }
            }
            else if(showPlayers.find()){
                for (int i = 0; i < UsersDB.usersDB.getUsers().size(); i++) {
                    AdminMenuView.output("show", UsersDB.usersDB.getUsers().get(i).getUsername(),
                            valueOf(UsersDB.usersDB.getUsers().get(i).getLevel()),
                            valueOf(UsersDB.usersDB.getUsers().get(i).getGold()));
                }

            }
        }
    }

    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/response/AdminMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }
}
