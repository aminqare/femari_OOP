package controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.components.Captcha;
import model.components.User;
import model.UsersDB;
import model.utils.Encryption;
import view.profileMenuView;
import view.signUpLoginView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static controller.menuController.getJSONRegexMatcher;

public class profileMenuController {

    public static boolean enterCaptcha(Scanner scanner){
        final int maxTries = 3;
        int currentTries = 0;
        Captcha captcha = new Captcha();
        profileMenuView.output("captcha",(Object) captcha.getGeneratedCaptcha());
        String enteredCaptcha = profileMenuView.input(scanner);
        boolean captchaSucceed = true;
        while(!enteredCaptcha.equals(captcha.getAccordingNum())){
            if(enteredCaptcha.equals("back") || maxTries<=currentTries){
                captchaSucceed = false;
                break;
            }
            profileMenuView.output("captchainvalid");
            currentTries++;
            enteredCaptcha = signUpLoginView.input(scanner);
        }
        return captchaSucceed;
    }
    public static void updateDB(User currentUser){
        UsersDB.usersDB.update(currentUser);
        try {
            UsersDB.usersDB.toJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


}