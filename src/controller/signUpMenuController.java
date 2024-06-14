package controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.components.Captcha;
import model.UsersDB;
import model.components.User;
import model.utils.Encryption;
import model.utils.StringParser;
//import view.mainMenuView;
import view.signUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class signUpMenuController extends menuController{
    private static User currentUser;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-+={}[]|\\:;\"',.?/~`";
    private static final Random RANDOM = new SecureRandom();
    //public TextField usernameField;

    public static void setCurrentUser(User currentUser) {
        signUpMenuController.currentUser = currentUser;
    }

//    public static String chooseRandomSlogan(){
//        Random selector = new Random();
//        String[] slogans = {
//                "I have the high ground!",
//                "You underestimate my powers!",
//                "Don't try it!",
//                "Hello there!",
//                "This is the end for you!",
//                "You don't know the power of the dark side!",
//                "It is delusion to think your actions have had any consequence!",
//                "It's a canon event!"
//        };
//        return slogans[selector.nextInt(slogans.length)];
//    }

    public static String generateRandomString() {
        int length = 8; // minimum length of 6
        StringBuilder sb = new StringBuilder();
        // add at least 1 uppercase letter
        sb.append((char) (RANDOM.nextInt(26) + 'A'));
        // add at least 1 lowercase letter
        sb.append((char) (RANDOM.nextInt(26) + 'a'));
        // add at least 1 digit
        sb.append((char) (RANDOM.nextInt(10) + '0'));
        // add at least 1 symbol
        sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        // fill up the rest of the string with random characters
        for (int i = 4; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        // shuffle the characters randomly
        String shuffledString = shuffleString(sb.toString());
        return shuffledString;
    }

    private static String shuffleString(String string) {
        char[] characters = string.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = RANDOM.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    public static boolean authenticate(String username, String password){
        if(!usernameExists(username))
            return false;
        User user = UsersDB.usersDB.getUserByUsername(username);
        if(!user.getPassword().equals(Encryption.toSHA256(password))){
            return false;
        }
        return true;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}