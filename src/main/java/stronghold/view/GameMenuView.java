package stronghold.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stronghold.model.CardsDB;
import stronghold.model.cards.cards;
import stronghold.model.components.*;
import stronghold.model.specialCards.builder;
import stronghold.model.specialCards.specialCards;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

import static stronghold.controller.menuController.getJSONRegexMatcher;

public class GameMenuView extends menuView {
    private static superGame currentSuperGame;
    private static String pathToRegexJSON = "src/main/java/stronghold/Regex/GameMenuRegex.json";

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
//if(currentSuperGame.getCurrentGame().getCurrentPlayer().getRounds()<4 &&currentSuperGame.getCurrentGame().getCurrentEnemy().getRounds()<4){
//    if(notFull(currentSuperGame.getCurrentGame().getCurrentPlayer().getGameBoard().getBoard())){
//        Game game = currentSuperGame.getCurrentGame();
//        Player player = game.getCurrentEnemy();
//        ArrayList<cards> cards = player.getPlayerCards();
//        ArrayList<specialCards> specialCards = player.getPlayerSpecialCards();
//        double randomIndex = Math.abs(game.getRandom().nextDouble(1));
//        if (randomIndex < 0.7) {
//            int randomIndexForCards = Math.abs(game.getRandom().nextInt(cards.size()));
//            player.getPlayerCardsDeck().add(cards.get(randomIndexForCards));
//        } else {
//            int randomIndexForSpecialCards = Math.abs(game.getRandom().nextInt(specialCards.size()));
//            player.getPlayerSpecialCardsDeck().add(specialCards.get(randomIndexForSpecialCards));
//        }
//    }
//    System.out.println(currentSuperGame.getCurrentGame().getCurrentEnemy().getUsername() + "is receiving bonus");
//}
if(currentSuperGame.getCurrentGame().getPlayerOne().getRounds() == 0 && currentSuperGame.getCurrentGame().getPlayerTwo().getRounds() == 0){
    GameMenuView.Output("timeLine");
    timeLineMenu.run(currentSuperGame, scanner);

}
            if (command.matches("\\s*exit\\s*")) {
                Output("exit");
                System.exit(0);
            } else if (showDeck.find()) {
                Player player = currentSuperGame.getCurrentGame().getCurrentPlayer();
                if(!player.isHasMehradHidden()) {
                    GameMenuView.Output("playerDeck", player.getPlayerCardsDeck().toString(),
                            player.getPlayerSpecialCardsDeck().toString());
                }else{
                    GameMenuView.Output("playerDeck", "suck suck suck suck my dickkkkkkkkk", "you can't see these chicks in here");
                }
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
                    if(Objects.equals(selectedSpecialCard.getName(), "builder")){
                        builder.run(currentSuperGame.getCurrentGame(), (numberBlock - 1));
                        player.getPlayerSpecialCardsDeck().remove(selectedSpecialCard);
                        switchTurns(currentSuperGame.getCurrentGame());
                    }
                    else{
                        if ((numberBlock - 2) + duration > 20) {
                            GameMenuView.Output("invalidMove");
                        } else if (!Objects.equals(player.getGameBoard().getBoard().get(numberBlock - 1), "0")) {
                            GameMenuView.Output("invalidMove");
                        }else if(!intractSpecial(selectedSpecialCard, player, numberBlock - 1).isEmpty()){
                            GameMenuView.Output("invalidMove");
                        } else {
                            runSpecialCards(currentSuperGame.getCurrentGame(),selectedSpecialCard,numberBlock - 1);
                            player.getPlayerSpecialCardsDeck().remove(selectedSpecialCard);
                            player.setRounds(player.getRounds() - 1);
                            switchTurns(currentSuperGame.getCurrentGame());
                        }
                    }
                } else if (Objects.equals(cardDeckName, "cards")) {
                    cards selectedCard = player.getPlayerCardsDeck().get(numberCard - 1);
                    int duration = selectedCard.getDuration();
                    if ((numberBlock - 2) + duration > 20) {
                        GameMenuView.Output("invalidMove");
                    } else if (!Objects.equals(player.getGameBoard().getBoard().get(numberBlock - 1), "0")) {
                        GameMenuView.Output("invalidMove");
                    } else if(!intract(selectedCard, player, numberBlock - 1).isEmpty()){
                        GameMenuView.Output("invalidMove");
                    }
                    else {
                        ArrayList<Integer> Interacts=intract(selectedCard,enemyPlayer,numberBlock-1);
                        for (int i =numberBlock-1; i < (numberBlock+selectedCard.getDuration()-1); i++) {
                            if(Interacts.contains(i)) {
                                cards enemyCard = cards.GetCardByName(enemyPlayer.getPlayerCardsDeck(), enemyPlayer.getGameBoard().getBoard().get(i));
                                Player temp = cardFaceOfWinner(player, enemyPlayer, selectedCard, enemyCard);
                                if(temp==null){
                                    enemyPlayer.getGameBoard().getBoard().set(i, "Hole");
                                    player.getGameBoard().getBoard().set(i, "Hole");
                                    //player.setHP(player.getHP() + (enemyCard.getDamage() / enemyCard.getDuration()));
                                } else if (temp.getUsername().equals(player.getUsername())) {
                                    enemyPlayer.getGameBoard().getBoard().set(i, "Hole");
                                    player.getGameBoard().getBoard().add(i, selectedCard.getName());
                                    //enemyPlayer.setHP(enemyPlayer.getHP() - (selectedCard.getDamage() / selectedCard.getDuration()));
                                    //player.setHP(player.getHP() + (enemyCard.getDamage() / enemyCard.getDuration()));
                                } else if (temp.getUsername().equals(enemyPlayer.getUsername())) {
                                    player.getGameBoard().getBoard().set(i, "Hole");
                                    //player.setHP(player.getHP() - (enemyCard.getDamage() / enemyCard.getDuration()));
                                }
                            }else {
                                //enemyPlayer.setHP(enemyPlayer.getHP() - (selectedCard.getDamage() / selectedCard.getDuration()));
                                    player.getGameBoard().getBoard().add(i, selectedCard.getName());

                            }
                        }

                        player.getPlayerCardsDeck().remove(selectedCard);
                        player.setRounds(player.getRounds() - 1);
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
        String pathToJSON = "src/main/java/stronghold/response/GameMenuResponses.json";
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
        Double random = game.getRandom().nextDouble();
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
        ArrayList<cards> cards = player.getPlayerCards();
        ArrayList<specialCards> specialCards = player.getPlayerSpecialCards();
            double randomIndex = Math.abs(game.getRandom().nextDouble());
            if (randomIndex < 0.7) {
                int randomIndexForCards = Math.abs(game.getRandom().nextInt(cards.size()));
                player.getPlayerCardsDeck().add(cards.get(randomIndexForCards));
            } else {
                int randomIndexForSpecialCards = Math.abs(game.getRandom().nextInt(specialCards.size()));
                player.getPlayerSpecialCardsDeck().add(specialCards.get(randomIndexForSpecialCards));
            }

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
        for (int i = BlockIndex; i < BlockIndex + playedCard.getDuration()+1; i++) {
            if (enemy.getGameBoard().getType().get(i).equals("cards")) {
                Temp.add(i);
            }
        }
        return Temp;
    }
    public static ArrayList<Integer> intractSpecial(specialCards playedCard, Player enemy, int BlockIndex) {
        ArrayList<Integer> Temp = new ArrayList<>();
        for (int i = BlockIndex; i < BlockIndex + playedCard.getDuration()+1; i++) {
            if (enemy.getGameBoard().getType().get(i).equals("specialCards")) {
                Temp.add(i);
            }
        }
        return Temp;
    }
    public static void runSpecialCards(Game game, specialCards card,int BlockIndex){
        String cardName = card.getName();
if(cardName.equals("shield")){
    String oposite = game.getCurrentEnemy().getGameBoard().getBoard().get(BlockIndex);
    if(oposite.equals("0")||oposite.equals("1")){
        GameMenuView.Output("invalidMove");
    }else{
        game.getCurrentEnemy().getGameBoard().getBoard().set(BlockIndex,"Hole");
        game.getCurrentPlayer().getGameBoard().getBoard().set(BlockIndex,"shield");
    }
}else{
    specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), cardName).run(game);
    for (int i =BlockIndex; i < BlockIndex+ card.getDuration(); i++) {
        game.getCurrentPlayer().getGameBoard().getBoard().add(i, card.getName());
    }
}


    }
    public static boolean notFull(ArrayList<String> board){
        for (int i = 0; i < board.size(); i++) {
            if(board.get(i).equals("0")||board.get(i).equals("1")||board.get(i).equals("Hole")){
                continue;
            }
            else{
                return false;
            }

        }
        return true;
    }
}

