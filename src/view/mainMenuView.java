//package view;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
////import controller.ProfileMenuController;
//import controller.signUpMenuController;
//import model.components.User;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//
//import static controller.MenuController.getJSONRegexMatcher;
//
//public class MainMenuView extends menuView{
//
//    private static String pathToRegexJSON = "src/main/java/stronghold/database/utils/regex/MainMenuRegex.json";
//
//    public static void run( User currentUser,Scanner scanner) {
//
//
//
//        mainMenuController.welcome(currentUser);
//
//        JsonElement regexElement = null;
//        try {
//            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
//        } catch (
//                FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        JsonObject MainMenuRegexObj = regexElement.getAsJsonObject();
//
//        while (true) {
//            String command = input(scanner).trim();
//
//            Matcher startGameMatcher = getJSONRegexMatcher(command, "startNewGame", MainMenuRegexObj);
//            Matcher loadGameMatcher = getJSONRegexMatcher(command, "loadGame", MainMenuRegexObj);
//            Matcher profileMenuMatcher = getJSONRegexMatcher(command, "profileMenu", MainMenuRegexObj);
//
//            if (command.matches("\\s*exit\\s*")) {
//                MainMenuView.output("exit");
//                System.exit(0);
//            }
//            else if (command.matches("user\\s+logout")) {
//                MainMenuView.output("logout");
//                JsonElement prefsElement;
//                String pathToPrefs = "src/main/java/stronghold/database/database.datasets/preferences.json";
//                try {
//                    prefsElement = JsonParser.parseReader(
//                            new FileReader(pathToPrefs));
//                } catch (
//                        FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//
//                try {
//                    String toBeWritten = prefsElement.toString();
//                    toBeWritten = toBeWritten.replace(currentUser.getUsername(),"!NULLUSER");
//                    FileWriter fileWriter = new FileWriter(pathToPrefs);
//                    fileWriter.write(toBeWritten);
//                    fileWriter.close();
//                } catch (
//                        IOException e) {
//                    throw new RuntimeException(e);
//                }
//                SignUpLoginView.run(scanner);
//                break;
//
//            } else if ((startGameMatcher = getJSONRegexMatcher(command, "startNewGame", MainMenuRegexObj)).matches()){
//                int i=Integer.parseInt(startGameMatcher.group("opponent"));
//                int j=Integer.parseInt(startGameMatcher.group("rounds"));
//                Scanner scanner1=new Scanner(System.in);
//                System.out.println("enter map size:");
//                int mapSize=scanner1.nextInt();
//
//
//                GameMenuView.run( scanner, j,i,mapSize);
//
//            }  else if(profileMenuMatcher.find()){
//                ProfileMenuView.run(scanner, currentUser);
//
//            } else if(loadGameMatcher.find()){
//
//
//            } else{
//                output("invalid");
//            }
//        }
//
//    }
//    public static void output(String code, Object... params){
//        String pathToJSON = "src/main/java/stronghold/database/textcontent/MainMenuResponses.json";
//        MenuView.output(pathToJSON, code, params);
//    }
//
//    public static String input(Scanner scanner){
//        if(!scanner.hasNextLine())
//            return "";
//        return scanner.nextLine();
//    }
//
//
//}