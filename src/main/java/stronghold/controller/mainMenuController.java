package stronghold.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.CardsDB;
import stronghold.model.cards.cards;
import stronghold.model.components.User;
import stronghold.model.UsersDB;
//import view.gameMenuView;
import stronghold.model.specialCards.*;
import stronghold.model.utils.SpecialCardsDB;
import stronghold.view.mainMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class mainMenuController extends menuController{

    public static void welcome(User currentUser){
        System.out.println("\u001B[45m\u001B[30mWELCOME, " + currentUser.getNickname() + "!\u001B[0m");

    }
    public static void StartPack(Random random,User cuurentUser){

        double SpecialCardWeght=0.3;
        double CardsWeight=0.7;
        List<cards> cards=CardsDB.getCards();

        for(int i=0;i<20;i++){
            double randomVal= random.nextDouble();
            if(randomVal<CardsWeight){

                addCards(random,cards,cuurentUser);
            }else{
                addSpecialCards(random,AdminMenuController.GameSpecialCardsName,cuurentUser);
            }
        }
    }
    public static void addSpecialCards(Random random,ArrayList<String> names,User curentUser){
        int randomIndex= Math.abs(random.nextInt(9));
        String name= names.get(randomIndex);
        if(name.equals("shield")){
            curentUser.getPlayerSpecialCards().add(new shield());
        }else if(name.equals("roundDefuser")){
            curentUser.getPlayerSpecialCards().add(new roundDefuser());
        }else if(name.equals("powerade")){
            curentUser.getPlayerSpecialCards().add(new powerade());
        }else if(name.equals("oppsCardDefuser")){
            curentUser.getPlayerSpecialCards().add(new oppsCardDefuser());
        }else if(name.equals("mole")){
            curentUser.getPlayerSpecialCards().add(new mole());
        }
        else if(name.equals("mehradHidden")){
            curentUser.getPlayerSpecialCards().add(new mehradHidden());
        }
        else if(name.equals("heal")){
            curentUser.getPlayerSpecialCards().add(new heal());
        }
//        else if(name.equals("copyCat")){
//            curentUser.getPlayerSpecialCards().add(new copyCat());
//        }
        else if(name.equals("cardSnatcher")){
            curentUser.getPlayerSpecialCards().add(new cardSnatcher());
        }
        else if(name.equals("builder")){
            curentUser.getPlayerSpecialCards().add(new builder());
        }
    }
    public static void addCards(Random random,List<cards> cards,User currentUser){
        int randomIndex=Math.abs(random.nextInt(cards.size()));
        cards card=new cards(cards.get(randomIndex));
        currentUser.getPlayerCards().add(card);
    }




}