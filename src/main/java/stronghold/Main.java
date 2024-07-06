
package stronghold;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.controller.GameMenuController;
import stronghold.controller.mainMenuController;
import stronghold.controller.signUpMenuController;
import stronghold.controller.graphical.HubMenuController;
import stronghold.model.components.User;
import stronghold.model.UsersDB;
import stronghold.model.utils.StringParser;
import stronghold.view.mainMenuView;
import stronghold.view.signUpLoginView;
import stronghold.view.graphics.HubMenuView;
import stronghold.view.graphics.LoginView;
import stronghold.view.graphics.RegisterView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        JsonElement prefsElement;
        boolean startInGraphical = true;


        try {
            prefsElement = JsonParser.parseReader(
                    new FileReader("src/main/java/stronghold/database/preferences.json"));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String loggedinuser = StringParser.removeQuotes(
                String.valueOf(prefsElement.getAsJsonObject().get("logged-in user")));
        System.out.println(loggedinuser);

        if (startInGraphical) {

            if (loggedinuser.equals("!NULLUSER")) {

                RegisterView.main(args);
            }

        } else {
            Scanner scanner = new Scanner(System.in);

            if (loggedinuser.equals("!NULLUSER")) {

                signUpLoginView.run(scanner);
            }
            else {

                User currentUser = UsersDB.usersDB.getUserByUsername(loggedinuser);
                mainMenuView.run(currentUser, scanner);
            }


    }

}
}