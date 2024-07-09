package stronghold.controller;

import stronghold.model.CardsDB;
import stronghold.model.cards.cards;

import java.io.IOException;
import java.util.ArrayList;

public class AdminMenuController {
    public static ArrayList<String> GameSpecialCardsName=new ArrayList<>(){{
        add("shield");
        add("roundDefuser");
        add("powerade");
        add("oppsCardDefuser");
        add("mole");
        add("heal");
        add("builder");
    }};

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



}}
