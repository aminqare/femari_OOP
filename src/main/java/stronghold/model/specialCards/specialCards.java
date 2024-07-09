package stronghold.model.specialCards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import stronghold.model.cards.cards;
import stronghold.model.components.User;
import stronghold.model.utils.SpecialCardsDB;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class specialCards implements Serializable {
    private String name;
    private boolean IsBuyed=false;
    private int level=1;
    private int duration;
    private double price;
    private static ArrayList<specialCards> gameSpecialCards = new ArrayList<>(){{
        add(new shield()) ;
        add(new roundDefuser()) ;
        //add(new powerade());
        add(new powerade());
        add(new oppsCardDefuser());
        add(new mole());
        //add(new mehradHidden());
        add(new heal());
        //add(new copyCat());
        //add(new cardSnatcher());
        add(new builder());
    }};
    public static specialCards GetSpecialCardByName(List<specialCards> cards, String name){
        for (specialCards card : cards) {
            if(card.getName().equals(name)){
                return card;
            }
        }
        return null;
    }


    public static ArrayList<specialCards> getGameSpecialCards() {
        return gameSpecialCards;
    }

    public void setGameSpecialCards(ArrayList<specialCards> gameSpecialCards) {
        this.gameSpecialCards = gameSpecialCards;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public abstract void run(Object param);

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        String output="Name: "+this.getName()+" Level: "+String.valueOf(this.getLevel())+" Duration: "+String.valueOf(this.getDuration())+" Upgrade Price: " +String.valueOf(this.getPrice()) + "\n";
        return output;
    }


}
