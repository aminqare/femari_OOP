package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.UsersDB;
import model.components.Captcha;
import model.components.User;
import model.utils.Encryption;
import model.utils.StringParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static controller.menuController.*;
import static controller.signUpMenuController.*;

public class signUpLoginView extends menuView{
    private static String pathToRegexJSON = "src/Regex/signUpLoginRegex.json";
    private static User currentUser;
    public static void run(Scanner scanner){
        int failedAttempts = 0;
         long nextAttemptTime=0;
        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject menuRegexPatternsObject = regexElement.getAsJsonObject();

        while(true){
            long currentTime=System.currentTimeMillis();
            String input = signUpLoginView.input(scanner);


            Matcher registerMatcher = getJSONRegexMatcher(input, "register", menuRegexPatternsObject);
            Matcher registerRandPassMatcher = getJSONRegexMatcher(input,
                    "register_randpass", menuRegexPatternsObject);
            Matcher loginMatcher = getJSONRegexMatcher(input, "login", menuRegexPatternsObject);
            Matcher loginStayLoggedInMatcher = getJSONRegexMatcher(input, "loginStayLoggedIn",
                    menuRegexPatternsObject);
            Matcher forgotMyPassMatcher = getJSONRegexMatcher(input, "forgotMyPass",
                    menuRegexPatternsObject);
            Matcher adminLoggedIn = getJSONRegexMatcher(input, "loginAdmin", menuRegexPatternsObject);

            if (registerMatcher.find()) {
                String username = StringParser.removeQuotes(registerMatcher.group(3));
                String password = StringParser.removeQuotes(registerMatcher.group(5));
                String passwordConfirmation = StringParser.removeQuotes(registerMatcher.group(6));
                String email = StringParser.removeQuotes(registerMatcher.group(8));
                String nickname = StringParser.removeQuotes(registerMatcher.group(12));
//                System.out.println(username);
//                System.out.println(password);
//                System.out.println(passwordConfirmation);
//                System.out.println(email);
//                System.out.println(nickname);
                if(username == null) {
                    signUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null) {
                    signUpLoginView.output("registerpassword404");
                    continue;
                }
                if(passwordConfirmation == null) {
                    signUpLoginView.output("registerpassword404");
                    continue;
                }
                if(email == null) {
                    signUpLoginView.output("registeremail404");
                    continue;
                }
                if(nickname == null) {
                    signUpLoginView.output("registernickname404");
                    continue;
                }

                if(!usernameFormatCorrect(username)) {
                    signUpLoginView.output("invalidusername");
                    continue;
                }
                if(usernameExists(username)) {
                    signUpLoginView.output("usernameexists");
                    continue;
                }
                if(!passwordIsStrong(password)) {
                    signUpLoginView.output("passwordweak");
                    continue;
                }
                if (!password.equals(passwordConfirmation)) {
                    signUpLoginView.output("unmatchingpasswords");
                    continue;
                }
                if(emailExists(email)) {
                    signUpLoginView.output("emailexists");
                    continue;
                }
                if(!emailIsValid(email)) {
                    signUpLoginView.output("invalidemail");
                    continue;
                }


                signUpLoginView.output("showsecurityquestions");

                String questionPickInput = signUpLoginView.input(scanner);
                Matcher questionPickMatcher = getJSONRegexMatcher(questionPickInput,
                        "questionPick", menuRegexPatternsObject);

                int passwordRecoveryQuestion = - 1;
                String passwordRecoveryAnswer = "null";
                String passwordRecoveryAnswerConfirmation = "null";



                while(!questionPickMatcher.find()){
                    if(questionPickInput.equals("Exit")){
                        return;
                    }
                    signUpLoginView.output("recoveryquestion404");
                    questionPickInput = signUpLoginView.input(scanner);
                    questionPickMatcher = getJSONRegexMatcher(questionPickInput,
                            "questionPick", menuRegexPatternsObject);
                }
                passwordRecoveryQuestion = Integer.parseInt(questionPickMatcher.group("questionNumber"));
                passwordRecoveryAnswer = StringParser.removeQuotes(questionPickMatcher.group(
                        "answer"));
                passwordRecoveryAnswerConfirmation = StringParser.removeQuotes(
                        questionPickMatcher.group("answerConfirm"));

                if (!passwordRecoveryAnswer.equals(passwordRecoveryAnswerConfirmation)){
                    signUpLoginView.output("unmatchingpasswords");
                    continue;
                }
                Captcha captcha = new Captcha();
                signUpLoginView.output("captcha",(Object) captcha.getGeneratedCaptcha());
                String enteredCaptcha = signUpLoginView.input(scanner);
                boolean captchaSucceed = true;
                while(!enteredCaptcha.equals(captcha.getAccordingNum())){
                    if(enteredCaptcha.equals("back")){
                        captchaSucceed = false;
                        break;
                    }
                    signUpLoginView.output("captchainvalid");
                    enteredCaptcha = signUpLoginView.input(scanner);
                }
                if(!captchaSucceed)
                    continue;
                User user = new User(username, Encryption.toSHA256(password)
                        , nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer);
                UsersDB.usersDB.addUser(user);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                signUpLoginView.output("usercreated", (Object) username, (Object) nickname);
            }
            else if(registerRandPassMatcher.find()){

                String username = StringParser.removeQuotes(registerRandPassMatcher.group(3));
                String password = StringParser.removeQuotes(registerRandPassMatcher.group(5));
                String email = StringParser.removeQuotes(registerRandPassMatcher.group(7));
                String nickname = StringParser.removeQuotes(registerRandPassMatcher.group(11));

                if(!password.equals("random")){
                    signUpLoginView.output("registerpasswordconfirm404");
                    continue;
                }
                if(username == null) {
                    signUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null) {
                    signUpLoginView.output("registerpassword404");
                    continue;
                }
                if(email == null) {
                    signUpLoginView.output("registeremail404");
                    continue;
                }
                if(nickname == null) {
                    signUpLoginView.output("registernickname404");
                    continue;
                }

                if(!usernameFormatCorrect(username)) {
                    signUpLoginView.output("invalidusername");
                    continue;
                }
                if(usernameExists(username)) {
                    signUpLoginView.output("usernameexists");
                    continue;
                }
                if(emailExists(email)) {
                    signUpLoginView.output("emailexists");
                    continue;
                }
                if(!emailIsValid(email)) {
                    signUpLoginView.output("invalidemail");
                    continue;
                }

                password = generateRandomString();
                signUpLoginView.output("randpass", (Object) password);
                signUpLoginView.output("confirmrandpass");
                String passwordConfirmation = signUpLoginView.input(scanner);

                if(!passwordConfirmation.equals(password)){
                    signUpLoginView.output("unmatchingpasswords");
                    continue;
                }


                signUpLoginView.output("showsecurityquestions");

                String questionPickInput = signUpLoginView.input(scanner);
                Matcher questionPickMatcher = getJSONRegexMatcher(questionPickInput,
                        "questionPick", menuRegexPatternsObject);

                int passwordRecoveryQuestion = - 1;
                String passwordRecoveryAnswer = "null";
                String passwordRecoveryAnswerConfirmation = "null";



                while(!questionPickMatcher.find()){
                    if(questionPickInput.equals("Exit")){
                        return;
                    }
                    signUpLoginView.output("recoveryquestion404");
                    questionPickInput = signUpLoginView.input(scanner);
                    questionPickMatcher = getJSONRegexMatcher(questionPickInput,
                            "questionPick", menuRegexPatternsObject);
                }
                passwordRecoveryQuestion = Integer.parseInt(questionPickMatcher.group("questionNumber"));
                passwordRecoveryAnswer = StringParser.removeQuotes(questionPickMatcher.group(
                        "answer"));
                passwordRecoveryAnswerConfirmation = StringParser.removeQuotes(
                        questionPickMatcher.group("answerConfirm"));

                if (!passwordRecoveryAnswer.equals(passwordRecoveryAnswerConfirmation)){
                    signUpLoginView.output("unmatchingpasswords");
                    continue;
                }

                Captcha captcha = new Captcha();
                signUpLoginView.output("captcha",(Object) captcha.getGeneratedCaptcha());
                String enteredCaptcha = signUpLoginView.input(scanner);
                boolean captchaSucceed = true;
                while(!enteredCaptcha.equals(captcha.getAccordingNum())){
                    if(enteredCaptcha.equals("back")){
                        captchaSucceed = false;
                        break;
                    }
                    signUpLoginView.output("captchainvalid");
                    enteredCaptcha = signUpLoginView.input(scanner);
                }
                if(!captchaSucceed)
                    continue;

                User user = new User(username, Encryption.toSHA256(password)
                        , nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer);
                UsersDB.usersDB.addUser(user);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                signUpLoginView.output("usercreated", (Object) username, (Object) nickname);

            }
            else if (loginMatcher.find()) {
                String username = StringParser.removeQuotes(loginMatcher.group("username"));
                String password = StringParser.removeQuotes(loginMatcher.group("pass"));

                if(username == null) {
                    signUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null) {
                    signUpLoginView.output("registerpassword404");
                    continue;
                }
                if(!usernameExists(username)){
                    signUpLoginView.output("usernotfound");
                }

                boolean userAuthenticated = authenticate(username,password);

                if(!userAuthenticated ){
                    if(currentTime<nextAttemptTime){
                        long waitTime=(nextAttemptTime-currentTime)/1000;
                        System.out.println("Try again in "+waitTime+" seconds");

                    }else {
                        failedAttempts++;
                        signUpLoginView.output("unmatchingpassanduser");
                        nextAttemptTime = System.currentTimeMillis() + failedAttempts * 5 * 1000;
                        System.out.println("Try again in " + 5 * failedAttempts + " seconds");
                    }
                    continue;
                }


                currentUser = UsersDB.usersDB.getUserByUsername(username);
                setCurrentUser(currentUser);
                signUpLoginView.output("successfulLogin");
                mainMenuView.run(currentUser, scanner);
            }
            else if (loginStayLoggedInMatcher.find()) {

                String username = StringParser.removeQuotes(loginStayLoggedInMatcher.group("username"));
                String password = StringParser.removeQuotes(loginStayLoggedInMatcher.group("pass"));

                if(username == null) {
                    signUpLoginView.output("registerusername404");
                    continue;
                }
                if(password == null) {
                    signUpLoginView.output("registerpassword404");
                    continue;
                }

                boolean userAuthenticated = authenticate(username,password);

                if(!userAuthenticated){
                    signUpLoginView.output("unmatchingpassanduser");
                    try {
                        Thread.sleep(5000 * failedAttempts++);
                    } catch (
                            InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                JsonElement prefsElement;
                String pathToPrefs = "src/database/preferences.json";
                try {
                    prefsElement = JsonParser.parseReader(
                            new FileReader(pathToPrefs));
                } catch (
                        FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                try {
                    String toBeWritten = prefsElement.toString();
                    toBeWritten = toBeWritten.replace("!NULLUSER",username);
                    FileWriter fileWriter = new FileWriter(pathToPrefs);
                    fileWriter.write(toBeWritten);
                    fileWriter.close();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }

                currentUser = UsersDB.usersDB.getUserByUsername(username);
                setCurrentUser(currentUser);
                signUpLoginView.output("successfulLogin");
                mainMenuView.run(currentUser, scanner);

            }
            else if (forgotMyPassMatcher.find()) {
                String username = StringParser.removeQuotes(forgotMyPassMatcher.group("username"));

                if(!usernameExists(username)){
                    signUpLoginView.output("usernotfound");
                    continue;
                }

                User pendingUser = UsersDB.usersDB.getUserByUsername(username);
                signUpLoginView.output("securityQ"+pendingUser.getPasswordRecoveryQuestion());

                String answer = signUpLoginView.input(scanner);

                if(!pendingUser.getPasswordRecoveryAnswer().equals(answer)){
                    signUpLoginView.output("incorrectsecurityanswer");
                    continue;
                }

                signUpLoginView.output("setnewpassword");
                String newPassword = signUpLoginView.input(scanner);

                signUpLoginView.output("confirmnewpassword");
                String newConfirmation = signUpLoginView.input(scanner);

                if(!newConfirmation.equals(newPassword)){
                    signUpLoginView.output("unmatchingpasswords");
                    continue;
                }

                if(!passwordIsStrong(newPassword)){
                    signUpLoginView.output("passwordweak");
                    continue;
                }

                Captcha captcha = new Captcha();
                signUpLoginView.output("captcha",(Object) captcha.getGeneratedCaptcha());
                String enteredCaptcha = signUpLoginView.input(scanner);
                boolean captchaSucceed = true;
                while(!enteredCaptcha.equals(captcha.getAccordingNum())){
                    if(enteredCaptcha.equals("back")){
                        captchaSucceed = false;
                        break;
                    }
                    signUpLoginView.output("captchainvalid");
                    enteredCaptcha = signUpLoginView.input(scanner);
                }
                if(!captchaSucceed)
                    continue;

                pendingUser.setPassword(Encryption.toSHA256(newPassword));
                UsersDB.usersDB.update(pendingUser);
                try {
                    UsersDB.usersDB.toJSON();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
                signUpLoginView.output("passChanged");

            }
            else if(adminLoggedIn.find()){
                signUpLoginView.output("adminLogin");
                AdminMenuView.run(scanner);
            }
            else{
                signUpLoginView.output("invalid");
            }
        }
    }

    public static void output(String code, Object... params){
        String pathToJSON = "src/response/signUpLoginMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }

    public static String input(Scanner scanner){
        if(!scanner.hasNextLine())
            return "";
        String readLine = scanner.nextLine();
        return readLine;
    }

}