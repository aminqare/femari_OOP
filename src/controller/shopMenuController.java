package controller;

import model.CardsDB;
import model.cards.cards;
import model.specialCards.specialCards;
import model.utils.SpecialCardsDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class shopMenuController {
    public static void ShowCards(List<cards> cards, ArrayList<specialCards> specialCards){
        StringBuilder output=new StringBuilder();
        int Index=0;
        for (cards playerCard : cards) {
            Index++;
            output.append(Index+"."+playerCard.toString()+"\n");
        }
        for (specialCards playerSpecialCard : specialCards) {
            Index++;
            output.append(Index+"."+playerSpecialCard.toString()+"\n");
        }
        System.out.print(output);
    }
    public static void addCards(specialCards cards){
        SpecialCardsDB.specialCardsDB.addCards(cards);
        try {
            SpecialCardsDB.specialCardsDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
