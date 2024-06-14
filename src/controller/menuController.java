package controller;

import com.google.gson.JsonObject;
import model.UsersDB;
//import model.database.UsersDB;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class menuController {

    public static Matcher getJSONRegexMatcher(String input, String key, JsonObject jsonObject){
        String pattern = String.valueOf(jsonObject.get(key).getAsString());
        return Pattern.compile(pattern).matcher(input);
    }

    public static boolean usernameFormatCorrect(String username) {
        return username.matches("^[A-Za-z0-9_]+$");
    }

    public static boolean usernameExists(String username){
        return UsersDB.usersDB.getUserByUsername(username) != null;
    }

    public static boolean emailIsValid(String email) {
        return email.matches("^[A-Za-z0-9_.]+@[A-Za-z0-9.]+\\.[A-Za-z0-9.]+$");
    }

    public static boolean emailExists(String email){
        return UsersDB.usersDB.getUserByEmail(email) != null;
    }

    public static boolean passwordIsStrong(String password) {

        boolean hasLowercase = password.matches("^.*[a-z].*$");
        boolean hasUppercase = password.matches("^.*[A-Z].*$");
        boolean hasDigit = password.matches("^.*[0-9].*$");
        boolean hasSymbol = !password.matches("^[A-Za-z0-9]+$");

        if(password.length() < 6)
            return false;

        return hasLowercase && hasUppercase && hasDigit && hasSymbol;
    }

    public static void run(Scanner scanner){

    }


}