package model.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.CardsDB;
import model.cards.cards;
import model.specialCards.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialCardsDB implements Serializable {

    private List<specialCards> specialCards;
    private final String pathToUsersDBJsonFile = "src/database/specialCards.json";

    public static SpecialCardsDB specialCardsDB=new SpecialCardsDB();

    public SpecialCardsDB(){
        this.specialCards = new ArrayList<>();
        try {
            this.fromJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void fromJSON() throws IOException {
        JsonObject SpecialCardsDBJson;
        Gson gson = new Gson();
        FileReader usersJSON = new FileReader(pathToUsersDBJsonFile);
        this.specialCards = gson.fromJson(usersJSON, new TypeToken<List<specialCards>>(){}.getType());
        usersJSON.close();
    }

    public void toJSON() throws IOException {
        Gson gson = new Gson();
        FileWriter usersJSON = new FileWriter(pathToUsersDBJsonFile);
        String jsonData = gson.toJson(this.specialCards, new TypeToken<List<specialCards>>(){}.getType());
        BufferedWriter writer = new BufferedWriter(usersJSON);
        writer.write(jsonData);
        writer.close();
    }

    public specialCards getAtIndex(int index){
        return this.specialCards.get(index);
    }

    public void addCards(specialCards card){
        this.specialCards.add(card);
    }


    public specialCards getCardByName(String name){
        for(specialCards card: this.specialCards){
            if(card.getName().equals(name)){
                return card;
            }
        }
        return null;
    }


    public List<specialCards> getCards() {
        return specialCards;
    }

    public void removeCardsByName(String name){
        this.specialCards.remove(this.getCardByName(name));
    }

    public void update(specialCards card){
        for(specialCards iter: this.specialCards){
            if(card.equals(card)){
                iter = card;
                return;
            }
        }
    }
}
