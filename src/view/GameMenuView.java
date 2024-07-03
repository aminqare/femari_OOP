package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.cards.cards;
import model.components.*;
import model.specialCards.specialCards;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

import static controller.menuController.getJSONRegexMatcher;

public class GameMenuView extends menuView {
    private static superGame currentSuperGame;
    private static String pathToRegexJSON = "src/Regex/GameMenuRegex.json";

    public static void run(superGame currentGame, Scanner scanner) {
        System.out.println("Hello");
        GameMenuView.currentSuperGame = currentGame;
        currentSuperGame.setCurrentGame(new Game(currentSuperGame));
        FirstTurn(currentSuperGame.getCurrentGame());


        JsonElement regexElement = null;
        try {
            regexElement = JsonParser.parseReader(new FileReader(pathToRegexJSON));
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JsonObject GameMenuRegexObj = regexElement.getAsJsonObject();

        while (true) {
            String command = input(scanner).trim();
            Matcher showDeck = getJSONRegexMatcher(command, "showDeck", GameMenuRegexObj);
            Matcher cartSelection = getJSONRegexMatcher(command, "cartSelection", GameMenuRegexObj);
            Matcher PlaceCard = getJSONRegexMatcher(command, "PlaceCard", GameMenuRegexObj);
            GameMenuView.ShowGround(currentGame.getCurrentGame());
            if (command.matches("\\s*exit\\s*")) {
                Output("exit");
                System.exit(0);
            }else if(showDeck.find()){
                Player player = currentGame.getCurrentGame().getCurrentPlayer();
                    GameMenuView.Output("playerDeck",player.getPlayerCardsDeck().toString(),
                            player.getPlayerSpecialCardsDeck().toString());
            }else if(cartSelection.find()){
                Player player = currentGame.getCurrentGame().getCurrentPlayer();
                String cardDeckName = cartSelection.group("cardDeck");
                String cardNumber = cartSelection.group("CardNumber");
                int number = Integer.parseInt(cardNumber);
                if(Objects.equals(cardDeckName, "specialCards")){
                    specialCards selectedSpecialCard = player.getPlayerSpecialCardsDeck().get(number - 1);
                    GameMenuView.Output("selectionSuccess");
                    System.out.println();
                    GameMenuView.Output("selectedSpecialCard", selectedSpecialCard.getName());
                }
                else if(Objects.equals(cardDeckName, "cards")){
                    cards selectedCard = player.getPlayerCardsDeck().get(number - 1);
                    GameMenuView.Output("selectionSuccess");
                    System.out.println();
                    GameMenuView.Output("selectedCard", selectedCard.getName(), String.valueOf(selectedCard.getLevel()),
                            String.valueOf(selectedCard.getAttack()), String.valueOf(selectedCard.getDefence()));
                }
                else{
                    GameMenuView.Output("invalid");
                }
            }
            else if(PlaceCard.find()){
                Player player = currentGame.getCurrentGame().getCurrentPlayer();
                String cardDeckName = PlaceCard.group("cardDeck");
                String cardNumber = PlaceCard.group("CardNumber");
                String blockNumber = PlaceCard.group("BlockNumber");
                int numberBlock = Integer.parseInt(blockNumber);
                int numberCard = Integer.parseInt(cardNumber);
                if(Objects.equals(cardDeckName, "specialCards")){
                    specialCards selectedSpecialCard = player.getPlayerSpecialCardsDeck().get(numberCard - 1);
                    int duration = selectedSpecialCard.getDuration();
                    if((numberBlock - 2) + duration > 20){
                        GameMenuView.Output("invalidMove");
                    }
                    else if(!Objects.equals(player.getGameBoard().getBoard().get(numberBlock - 1), "0")){
                        GameMenuView.Output("invalidMove");
                    }
                    else{
                        player.getGameBoard().getBoard().add(numberBlock - 1, selectedSpecialCard.getName());
                        for (int i = numberBlock; i < selectedSpecialCard.getDuration(); i++) {
                            player.getGameBoard().getBoard().add(i, "...");
                        }
                        player.getPlayerSpecialCardsDeck().remove(selectedSpecialCard);
                        switchTurns(currentGame.getCurrentGame());
                    }
                }
                else if(Objects.equals(cardDeckName, "cards")){
                    cards selectedCard = player.getPlayerCardsDeck().get(numberCard - 1);
                    int duration = selectedCard.getDuration();
                    if((numberBlock - 2) + duration > 20){
                        GameMenuView.Output("invalidMove");
                    }
                    else if(!Objects.equals(player.getGameBoard().getBoard().get(numberBlock - 1), "0")){
                        GameMenuView.Output("invalidMove");
                    }
                    else{
                        player.getGameBoard().getBoard().add(numberBlock - 1, selectedCard.getName());
                        for (int i = numberBlock; i < selectedCard.getDuration(); i++) {
                            player.getGameBoard().getBoard().add(i, "...");
                        }
                        player.getPlayerCardsDeck().remove(selectedCard);
                        switchTurns(currentGame.getCurrentGame());
                    }
                }
                else{
                    GameMenuView.Output("invalid");
                }
            }
            else {
                GameMenuView.Output("invalid");
            }
        }


    }

    public static String input(Scanner scanner) {
        if (!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }

    public static void Output(String code, Object... params) {
        String pathToJSON = "src/response/GameMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }

    public static void ShowGround(Game game) {
    if(game.getPlayerOne().isFirstPlayer()){
    game.getPlayerTwo().getGameBoard().ShowBoard();
        System.out.println();
    game.getPlayerOne().getGameBoard().ShowBoard();
    }
    else{
        game.getPlayerOne().getGameBoard().ShowBoard();
        System.out.println();
        game.getPlayerTwo().getGameBoard().ShowBoard();
    }
    }

    public static void FirstTurn(Game game) {
        System.out.println("wotking well");
        Double random = game.getRandom().nextDouble(1);
        if (random < 0.7) {
            game.getPlayerOne().setTurn(true);
            game.getPlayerTwo().setTurn(false);
            game.getPlayerOne().setFirstPlayer(true);
            GameMenuView.Output("Turn", game.getPlayerOne().getUsername());
        } else {
            game.getPlayerOne().setTurn(false);
            game.getPlayerTwo().setTurn(true);
            game.getPlayerTwo().setFirstPlayer(true);
            GameMenuView.Output("Turn", game.getPlayerTwo().getUsername());
        }
    }
    public static void switchTurns(Game game){
        if(game.getPlayerOne().isTurn()){
            game.getPlayerOne().setTurn(false);
            game.getPlayerOne().setTurn(true);
        }
        else{
            game.getPlayerOne().setTurn(true);
            game.getPlayerOne().setTurn(false);
        }
    }
}
