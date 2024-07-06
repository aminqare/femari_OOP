package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.controller.menuController;
import stronghold.model.components.User;
import stronghold.model.UsersDB;
import stronghold.model.utils.Encryption;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.menuController.getJSONRegexMatcher;
import static stronghold.controller.profileMenuController.*;

public class profileMenuView {

    private static String pathToRegexJSON = "src/main/java/stronghold/Regex/ProfileMenuRegex.json";
    public static void run(Scanner scanner, User currentUser){
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();

        while(true) {
            String input = signUpLoginView.input(scanner);

            Matcher changeUsernameMatcher =
                    getJSONRegexMatcher(input, "userNameChange", menuRegexPatternsObject);
            Matcher changeNicknameMatcher =
                    getJSONRegexMatcher(input, "nickNameChange" , menuRegexPatternsObject);
            Matcher changePasswordMatcher =
                    getJSONRegexMatcher(input, "passWordChange" , menuRegexPatternsObject);;
            Matcher changeEmailMatcher =
                    getJSONRegexMatcher(input, "emailChange" , menuRegexPatternsObject);;
            Matcher getScoreMatcher =
                    getJSONRegexMatcher(input, "score" , menuRegexPatternsObject);;
            Matcher getRankMatcher =
                    getJSONRegexMatcher(input, "rank" , menuRegexPatternsObject);;
            Matcher displayAllMatcher =
                    getJSONRegexMatcher(input, "displayAll" , menuRegexPatternsObject);;

            if (input.matches("\\s*back\\s*")) {
                profileMenuView.output("back");
                break;
            } else if(changeUsernameMatcher.find()){
                String username = changeUsernameMatcher.group("username");
                if(username == null){
                    profileMenuView.output("usernameEmpty");
                    continue;
                }
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setUsername(username);
                updateDB(currentUser);
                profileMenuView.output("usernameChanged");
            }
            else if(changeNicknameMatcher.find()){

                String nickname = changeNicknameMatcher.group("nickname");
                if(nickname == null){
                    profileMenuView.output("nicknameEmpty");
                    continue;
                }
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setNickname(nickname);
                updateDB(currentUser);
                profileMenuView.output("nicknameChanged");
            }
            else if(changePasswordMatcher.find()){
                String oldPassword = changePasswordMatcher.group("old");
                String newPassword = changePasswordMatcher.group("new");
                if(oldPassword == null){
                    profileMenuView.output("oldPass404");
                    continue;
                }
                if(newPassword == null){
                    profileMenuView.output("newPass404");
                    continue;
                }
                if(!currentUser.getPassword().equals(Encryption.toSHA256(oldPassword))){
                    profileMenuView.output("passwordError");
                    continue;
                }
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setPassword(Encryption.toSHA256(newPassword));
                updateDB(currentUser);
                profileMenuView.output("passwordChanged");
            }
            else if(changeEmailMatcher.find()){
                String email = changeEmailMatcher.group("email");
                if(email == null){
                    profileMenuView.output("email404");
                    continue;
                }
                if(!menuController.emailIsValid(email)){
                    profileMenuView.output("invalidEmail");
                    continue;
                }
                if(menuController.emailExists(email)){
                    profileMenuView.output("emailExists");
                    continue;
                }
                if(!enterCaptcha(scanner)){
                    continue;
                }
                currentUser.setEmail(email);
                updateDB(currentUser);
                updateDB(currentUser);
                profileMenuView.output("emailChanged");
            }
            else if(getRankMatcher.find()){
                int rank = UsersDB.usersDB.sortByScore().indexOf(currentUser);
                profileMenuView.output("rank", (Object) String.valueOf(rank));
            }
            else if(displayAllMatcher.find()){
                profileMenuView.output("show", currentUser.getUsername(),
                        currentUser.getEmail(), String.valueOf(currentUser.getScore()),
                        currentUser.getNickname(),
                        currentUser.getPlayerSpecialCards().toString(),
                        currentUser.getPlayerCards().toString(),String.valueOf(currentUser.getLevel()));
            }
            else{
                profileMenuView.output("invalid");
            }
        }
    }
    public static void output(String code, Object... params){
        String pathToJSON = "src/main/java/stronghold/response/ProfileMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }

}