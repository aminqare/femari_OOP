package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import controller.menuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class menuView {
    public static void output(String pathToOutputJSON,String code, Object... params) {
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(pathToOutputJSON));
            String response = String.valueOf(jsonElement.getAsJsonObject().get(code));

            response = response.substring(1,response.length()-1);

            response = response.replaceAll("^Error!:", "\u001B[41m\u001B[30mError!\u001B[0m\u001B[31m:");
            response = response.replaceAll("^Success!:", "\u001B[42m\u001B[30mSuccess!\u001B[0m\u001B[32m:");
            response = response.replaceAll("\\\\n","\n");
            response = response.replaceAll("\\\\\"","\"");
            response = response.replaceAll("^(.+)(\\r\\n|[\\r\\n])","\u001B[47m\u001B[30m$1\u001B[0m\n");

            for (int i = 1; i <= Arrays.stream(params).count(); i++) {
                String toBeReplaced = "\\$VAR" + i + "\\$";
                response = response.replaceAll(toBeReplaced, (String) params[i - 1]);
            }


        } catch (
                Error |
                FileNotFoundException e){
        }
    }

}
