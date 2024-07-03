package view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.CardsDB;
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

    public static void run(superGame currentSuperGame, Scanner scanner) {
        System.out.println("Hello");
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

            if (command.matches("\\s*exit\\s*")) {
                Output("exit");
                System.exit(0);
            } else if (showDeck.find()) {
                Player player = currentSuperGame.getCurrentGame().getCurrentPlayer();
                GameMenuView.Output("playerDeck", player.getPlayerCardsDeck().toString(),
                        player.getPlayerSpecialCardsDeck().toString());
            } else if (cartSelection.find()) {
                Player player = currentSuperGame.getCurrentGame().getCurrentPlayer();
                String cardDeckName = cartSelection.group("cardDeck");
                String cardNumber = cartSelection.group("CardNumber");
                int number = Integer.parseInt(cardNumber);
                if (Objects.equals(cardDeckName, "specialCards")) {
                    specialCards selectedSpecialCard = player.getPlayerSpecialCardsDeck().get(number - 1);
                    GameMenuView.Output("selectionSuccess");
                    System.out.println();
                    GameMenuView.Output("selectedSpecialCard", selectedSpecialCard.getName());
                } else if (Objects.equals(cardDeckName, "cards")) {
                    cards selectedCard = player.getPlayerCardsDeck().get(number - 1);
                    GameMenuView.Output("selectionSuccess");
                    System.out.println();
                    GameMenuView.Output("selectedCard", selectedCard.getName(), String.valueOf(selectedCard.getLevel()),
                            String.valueOf(selectedCard.getAttack()), String.valueOf(selectedCard.getDefence()), String.valueOf(selectedCard.getDamage()));
                } else {
                    GameMenuView.Output("invalid");
                }
            } else if (PlaceCard.find()) {
                Player player = currentSuperGame.getCurrentGame().getCurrentPlayer();
                Player enemyPlayer = currentSuperGame.getCurrentGame().getCurrentEnemy();
                String cardDeckName = PlaceCard.group("cardDeck");
                String cardNumber = PlaceCard.group("CardNumber");
                String blockNumber = PlaceCard.group("BlockNumber");
                int numberBlock = Integer.parseInt(blockNumber);
                int numberCard = Integer.parseInt(cardNumber);
                if (Objects.equals(cardDeckName, "specialCards")) {
                    specialCards selectedSpecialCard = player.getPlayerSpecialCardsDeck().get(numberCard - 1);
                    int duration = selectedSpecialCard.getDuration();
                    if ((numberBlock - 2) + duration > 20) {
                        GameMenuView.Output("invalidMove");
                    } else if (!Objects.equals(player.getGameBoard().getBoard().get(numberBlock - 1), "0")) {
                        GameMenuView.Output("invalidMove");
                    } else {

                        for (int i = numberBlock-1; i < numberBlock + selectedSpecialCard.getDuration(); i++) {
                            player.getGameBoard().getBoard().add(i, selectedSpecialCard.getName());
                        }
                        player.getPlayerSpecialCardsDeck().remove(selectedSpecialCard);
                        switchTurns(currentSuperGame.getCurrentGame());
                        System.out.println(currentSuperGame.getCurrentGame().getCurrentPlayer().getUsername());
                    }
                } else if (Objects.equals(cardDeckName, "cards")) {
                    cards selectedCard = player.getPlayerCardsDeck().get(numberCard - 1);
                    int duration = selectedCard.getDuration();
                    if ((numberBlock - 2) + duration > 20) {
                        GameMenuView.Output("invalidMove");
                    } else if (!Objects.equals(player.getGameBoard().getBoard().get(numberBlock - 1), "0")) {
                        GameMenuView.Output("invalidMove");
                    } else {
                        ArrayList<Integer> Interacts=intract(selectedCard,enemyPlayer,numberBlock-1);
                        for (int i =numberBlock-1; i < numberBlock+selectedCard.getDuration(); i++) {
                            if(Interacts.contains(i)) {
                                cards enemyCard = cards.GetCardByName(enemyPlayer.getPlayerCardsDeck(), enemyPlayer.getGameBoard().getBoard().get(i));
                                Player temp = cardFaceOfWinner(player, enemyPlayer, selectedCard, enemyCard);
                                if(temp==null){
                                    enemyPlayer.getGameBoard().getBoard().set(i, "Hole");
                                    player.getGameBoard().getBoard().set(i, "Hole");
                                    player.setHP(player.getHP() + (enemyCard.getDamage() / enemyCard.getDuration()));
                                } else if (temp.getUsername().equals(player.getUsername())) {
                                    enemyPlayer.getGameBoard().getBoard().set(i, "Hole");
                                    player.getGameBoard().getBoard().add(i, selectedCard.getName());
                                    enemyPlayer.setHP(enemyPlayer.getHP() - (selectedCard.getDamage() / selectedCard.getDuration()));
                                    player.setHP(player.getHP() + (enemyCard.getDamage() / enemyCard.getDuration()));
                                } else if (temp.getUsername().equals(enemyPlayer.getUsername())) {
                                    player.getGameBoard().getBoard().set(i, "Hole");
                                    player.setHP(player.getHP() - (enemyCard.getDamage() / enemyCard.getDuration()));
                                }
                            }else {
                                enemyPlayer.setHP(enemyPlayer.getHP() - (selectedCard.getDamage() / selectedCard.getDuration()));
                                    player.getGameBoard().getBoard().add(i, selectedCard.getName());

                            }
                        }

                        player.getPlayerCardsDeck().remove(selectedCard);
                        switchTurns(currentSuperGame.getCurrentGame());
                    }
                } else {
                    GameMenuView.Output("invalid");
                }
            } else {
                GameMenuView.Output("invalid");
            }
            System.out.println(currentSuperGame.getCurrentGame().getCurrentPlayer().getUsername());
            GameMenuView.ShowGround(currentSuperGame.getCurrentGame());
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
        if (game.getPlayerOne().isFirstPlayer()) {
            game.getPlayerTwo().getGameBoard().ShowBoard();
            System.out.println();
            System.out.println();
            game.getPlayerOne().getGameBoard().ShowBoard();
            System.out.println();
        } else {
            game.getPlayerOne().getGameBoard().ShowBoard();
            System.out.println();
            System.out.println();
            game.getPlayerTwo().getGameBoard().ShowBoard();
            System.out.println();
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

    public static void switchTurns(Game game) {
        Player player = game.getCurrentPlayer();
        Player enemy = game.getCurrentEnemy();
        player.setTurn(false);
        enemy.setTurn(true);
    }

    public static Player cardFaceOfWinner(Player playerOne, Player playerTwo, cards fisrtCard, cards secondCard) {
        double maxValueOfCardOne = Math.max(fisrtCard.getDefence(), fisrtCard.getAttack());
        double maxValueOfCardTwo = Math.max(secondCard.getDefence(), secondCard.getAttack());
        if (maxValueOfCardOne > maxValueOfCardTwo) {
            return playerOne;
        } else if (maxValueOfCardOne < maxValueOfCardTwo) {
            return playerTwo;
        } else {
            return null;
        }

    }

    public static ArrayList<Integer> intract(cards playedCard, Player enemy, int BlockIndex) {
        ArrayList<Integer> Temp = new ArrayList<>();
        for (int i = BlockIndex; i < BlockIndex + playedCard.getDuration(); i++) {
            if (enemy.getGameBoard().getType().get(i).equals("cards")) {
                Temp.add(i);
            }
        }
        return Temp;
    }
}

