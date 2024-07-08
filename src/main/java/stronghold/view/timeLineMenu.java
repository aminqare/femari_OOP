package stronghold.view;

import stronghold.model.cards.cards;
import stronghold.model.components.Game;
import stronghold.model.components.Player;
import stronghold.model.components.superGame;

import java.util.Scanner;

public class timeLineMenu {
    private static superGame currentSuperGame;
    //private static String pathToRegexJSON = "src/Regex/timeLineMenuRegex.json";

    public static void run(superGame currentSuperGame, Scanner scanner) {
        int stepTime = 9000;
        int index = 1;
        long startTime = System.currentTimeMillis();
        while (index <= 21) {
            Player playerOne = currentSuperGame.getPlayerOne();
            Player playerTwo = currentSuperGame.getPlayerTwo();
            long currentTime = System.currentTimeMillis();
            if (playerOne.getHP() <= 0 || playerTwo.getHP() <= 0) {
                if (playerOne.getHP() <= 0) {
                    endGameView.run(currentSuperGame, scanner, playerTwo);
                } else if (playerTwo.getHP() <= 0) {
                    endGameView.run(currentSuperGame, scanner, playerOne);
                }
            }
            if (currentTime >= stepTime + stepTime * index) {

                if (currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getType().get(index - 1).equals("cards") && ISnull(currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getType().get(index - 1))){
                    cards card = cards.GetCardByName(playerOne.getPlayerCards(),
                            currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getBoard().get(index - 1));
                    playerTwo.setHP(playerTwo.getHP() - (card.getDamage() / card.getDuration()));
                    timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                            playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                    timeLineMenu.Output("cards", card.getName(), "null");
                }else if (currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getType().get(index - 1).equals("cards") &&
                        isHeal(playerTwo, index - 1)) {
                    cards card = cards.GetCardByName(playerOne.getPlayerCards(),
                            currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getBoard().get(index - 1));
                    playerTwo.setHP(playerTwo.getHP() - (card.getDamage() / card.getDuration()));
                    playerTwo.IncreaseHP();
                    timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                            playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                    timeLineMenu.Output("cards", card.getName(), "Heal");
                } else if (currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getType().get(index - 1).equals("cards") &&
                        ISnull(currentSuperGame.getCurrentGame().getPlayerOne().getGameBoard().getType().get(index - 1))) {
                    cards card = cards.GetCardByName(playerTwo.getPlayerCards(),
                            currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getBoard().get(index - 1));
                    playerOne.setHP(playerOne.getHP() - (card.getDamage() / card.getDuration()));
                    timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                            playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                    timeLineMenu.Output("cards", "null", card.getName());
                } else if (currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getType().get(index - 1).equals("cards") &&
                        isHeal(playerOne, index - 1)) {
                    cards card = cards.GetCardByName(playerTwo.getPlayerCards(),
                            currentSuperGame.getCurrentGame().getPlayerTwo().getGameBoard().getBoard().get(index - 1));
                    playerOne.setHP(playerOne.getHP() - (card.getDamage() / card.getDuration()));
                    playerOne.IncreaseHP();
                    timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                            playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                    timeLineMenu.Output("cards", "Heal", card.getName());
                } else {
                    String specialCard = "specialCards";
                    String card = "cards";
                    String cardOne;
                    String cardTwo;
                    if (playerOne.getGameBoard().getType().get(index - 1).equals(specialCard) && playerTwo.getGameBoard().getType().get(index - 1).equals(specialCard)) {
                        if (playerOne.getGameBoard().getType().get(index - 1).equals("heal")) {
                            playerOne.IncreaseHP();
                            cardOne = "Heal";
                            cardTwo = playerTwo.getGameBoard().getBoard().get(index - 1);
                        }
                        if (playerTwo.getGameBoard().getType().get(index - 1).equals("heal")) {
                            playerTwo.IncreaseHP();
                            cardTwo = "Heal";
                            cardOne = playerOne.getGameBoard().getBoard().get(index - 1);
                        } else {
                            cardTwo = playerTwo.getGameBoard().getBoard().get(index - 1);
                            cardOne = playerOne.getGameBoard().getBoard().get(index - 1);
                        }
                        timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                                playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                        timeLineMenu.Output("cards", cardOne, cardTwo);
                    } else if (playerOne.getGameBoard().getType().get(index - 1).equals(specialCard) && ISnull(playerTwo.getGameBoard().getType().get(index - 1))) {
                        if (playerOne.getGameBoard().getType().get(index - 1).equals("heal")) {
                            playerOne.IncreaseHP();
                            cardOne = "Heal";
                            cardTwo = "null";
                        } else {
                            cardTwo = "null";
                            cardOne = playerOne.getGameBoard().getBoard().get(index - 1);
                        }
                        timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                                playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                        timeLineMenu.Output("cards", cardOne, cardTwo);
                    } else if (playerTwo.getGameBoard().getType().get(index - 1).equals(specialCard) && ISnull(playerOne.getGameBoard().getType().get(index - 1))) {
                        if (playerTwo.getGameBoard().getType().get(index - 1).equals("heal")) {
                            playerTwo.IncreaseHP();
                            cardTwo = "Heal";
                            cardOne = "null";
                        } else {
                            cardTwo = playerTwo.getGameBoard().getBoard().get(index - 1);
                            cardOne = "null";
                        }
                        timeLineMenu.Output("HP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                                playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
                        timeLineMenu.Output("cards", cardOne, cardTwo);
                    }

                }

                index++;
            }


        }
        Player playerOne = currentSuperGame.getPlayerOne();
        Player playerTwo = currentSuperGame.getPlayerTwo();
        timeLineMenu.Output("finalHP", playerOne.getUsername(), String.valueOf(playerOne.getHP()),
                playerTwo.getUsername(), String.valueOf(playerTwo.getHP()));
        timeLineMenu.Output("newRound");
        playerOne.setRounds(2);
        playerTwo.setRounds(2);
        GameMenuView.run(currentSuperGame, scanner);

    }

    public static boolean isHeal(Player player, int index) {
        if (player.getGameBoard().getBoard().get(index).equals("heal")) {
            return true;
        }
        return false;


    }

    public static String input(Scanner scanner) {
        if (!scanner.hasNextLine())
            return "";
        return scanner.nextLine();
    }

    public static void Output(String code, Object... params) {
        String pathToJSON = "src/main/java/stronghold/response/timeLineMenuResponses.json";
        menuView.output(pathToJSON, code, params);
    }

    public  static boolean ISnull(String s) {
        if (s.equals("empty")) {
            return true;
        } else if (s.equals("unv")) {
            return true;
        } else if (s.equals("hole")) {
            return true;
        } else {
            return false;
        }
    }
}
