package controller;

import model.CardsDB;
import model.UsersDB;
import model.cards.cards;
import model.components.User;
import model.utils.Encryption;

import java.io.IOException;
import java.util.ArrayList;

public class AdminMenuController {
    public ArrayList< cards> GameCards=new ArrayList<>();
    public static boolean nameExists(String name){
        return CardsDB.cardsDB.getCardByName(name) != null;
    }
    public static void addCards(String name, double defence, double attack,int duration, double damage, int level, double upgradeCost){
        cards cards = new cards(name, defence
                , attack, duration, damage, level, upgradeCost);
        CardsDB.cardsDB.addCards(cards);
        try {
            CardsDB.cardsDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateDB(cards card){
        CardsDB.cardsDB.update(card);
        try {
            CardsDB.cardsDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

}
