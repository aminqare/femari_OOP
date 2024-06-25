package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.cards.cards;
import model.utils.Encryption;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CardsDB implements Serializable {
    private static List<cards> cards;
    private final String pathToUsersDBJsonFile = "src/database/cards.json";

    public static CardsDB cardsDB = new CardsDB();

    public CardsDB(){
        this.cards = new ArrayList<>();
        try {
            this.fromJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void fromJSON() throws IOException {
        JsonObject cardsDBJson;
        Gson gson = new Gson();
        FileReader usersJSON = new FileReader(pathToUsersDBJsonFile);
        this.cards = gson.fromJson(usersJSON, new TypeToken<List<cards>>(){}.getType());
        usersJSON.close();
    }

    public void toJSON() throws IOException {
        Gson gson = new Gson();
        FileWriter usersJSON = new FileWriter(pathToUsersDBJsonFile);
        String jsonData = gson.toJson(this.cards, new TypeToken<List<cards>>(){}.getType());
        BufferedWriter writer = new BufferedWriter(usersJSON);
        writer.write(jsonData);
        writer.close();
    }

    public static cards getAtIndex(int index){
        return cards.get(index);
    }

    public void addCards(cards card){
        this.cards.add(card);
    }

    public cards getCardByName(String name){
        for(cards card: this.cards){
            if(card.getName().equals(name)){
                return card;
            }
        }
        return null;
    }

    public static List<cards> getCards() {
        return cards;
    }

    public void removeCardsByName(String name){
        this.cards.remove(this.getCardByName(name));
    }

    public void update(cards card){
        for(cards iter: this.cards){
            if(card.equals(card)){
                iter = card;
                return;
            }
        }
    }
}
