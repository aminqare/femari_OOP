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

import static controller.AdminMenuController.updateDB;
import static controller.menuController.getJSONRegexMatcher;

public class AdminMenuView {
    private static String pathToRegexJSON = "src/Regex/AdminMenuRegex.json";


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
        boolean delete = false;
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
            else if(selection.find() && delete){
                System.out.println("kir");
                AdminMenuView.output("deleteCard");
                selected = Integer.parseInt(selection.group("selection")) - 1;
            }
            else if(delete && confirm.find()){
                cards card = CardsDB.cardsDB.getCards().get(selected);
                CardsDB.cardsDB.removeCardsByName(card.getName());
                updateDB(card);
                delete = false;
            }
            else if(selection.find() && !delete){
                String selectionNum = StringParser.removeQuotes(selection.group("selection"));
                cards card = CardsDB.cardsDB.getCards().get(Integer.parseInt(selectionNum) - 1);
                selected = Integer.parseInt(selectionNum) - 1;
                AdminMenuView.output("showCards", card.getName(), String.valueOf(card.getDefence()),
                        String.valueOf(card.getAttack()), String.valueOf(card.getDuration())
                , String.valueOf(card.getDamage()), String.valueOf(card.getLevel()), String.valueOf(card.getUpgradeCost()));
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
                selected = 0;
                nameB = false;
                defence = false;
                attack = false;
                durationB = false;
                damageB = false;
                upLevel = false;
                upCost = false;
                delete = false;
                continue;
            }
            else if(confirm.find() && !delete){
                cards card = CardsDB.cardsDB.getCards().get(selected);
                if(nameB){
                    //System.out.println("kit");
                    if(nameExists(nameS)){
                        AdminMenuView.output("cardNameExits");
                        continue;
                    }
                    card.setName(nameS);
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    nameB = false;
                    selected = -1;
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
                    selected = -1;
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
                    selected = -1;
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
                    selected = -1;
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
                    selected = -1;
                }
                if(upLevel){
                    card.setLevel(Integer.parseInt(upLevelS));
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    upLevel = false;
                    selected = -1;
                }
                if(upCost){
                    card.setUpgradeCost(Double.parseDouble(upCostS));
                    updateDB(card);
                    AdminMenuView.output("cardEdited");
                    upCost = false;
                    selected = -1;
                }
            }
            else if(removeCards.find()){
                for (int i = 0; i < CardsDB.cardsDB.getCards().size(); i++) {
                    String cardName = CardsDB.cardsDB.getCards().get(i).getName();
                    System.out.println(i+1 + ". " + cardName);
                }
                delete = true;
            }
        }
    }
    public static void output(String code, Object... params){
        String pathToJSON = "src/response/AdminMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }


}
