package controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.components.User;
import model.UsersDB;
//import view.gameMenuView;
import model.specialCards.*;
import view.mainMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class mainMenuController extends menuController{
    public static void welcome(User currentUser){
        System.out.println("\u001B[45m\u001B[30mWELCOME, " + currentUser.getNickname() + "!\u001B[0m");

    }
    public static void StartPack(Random random,User cuurentUser){
        ArrayList<specialCards> playerSpecialCards=cuurentUser.getPlayerSpecialCards();
        double SpecialCardWeght=0.3;
        double CardsWeight=0.7;

        for(int i=0;i<20;i++){
            double randomVal= random.nextDouble();
            if(randomVal%1<CardsWeight){
                //TODO add cards
            }else{
                addSpecialCards(random,playerSpecialCards,AdminMenuController.GameSpecialCardsName);
            }
        }
    }
    public static void addSpecialCards(Random random,ArrayList<specialCards> playerCards,ArrayList<String> names){
        int randomIndex= random.nextInt()%names.size();
        String name= names.get(randomIndex);
        if(name.equals("shield")){
            playerCards.add(new shield());
        }else if(name.equals("roundDefuser")){
            playerCards.add(new roundDefuser());
        }else if(name.equals("powerade")){
            playerCards.add(new powerade());
        }else if(name.equals("oppsCardDefuser")){
            playerCards.add(new oppsCardDefuser());
        }else if(name.equals("mole")){
            playerCards.add(new mole());
        }else if(name.equals("mehradHidden")){
            playerCards.add(new mehradHidden());
        }else if(name.equals("heal")){
            playerCards.add(new heal());
        }else if(name.equals("copyCat")){
            playerCards.add(new copyCat());
        }else if(name.equals("cardSnatcher")){
            playerCards.add(new cardSnatcher());
        }else if(name.equals("builder")){
            playerCards.add(new builder());
        }
    }


}