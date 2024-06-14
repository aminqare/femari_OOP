package controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.components.User;
import model.UsersDB;
//import view.gameMenuView;
import view.mainMenuView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class mainMenuController extends menuController{
    public static void welcome(User currentUser){
        System.out.println("\u001B[45m\u001B[30mWELCOME, " + currentUser.getNickname() + "!\u001B[0m");

    }


}