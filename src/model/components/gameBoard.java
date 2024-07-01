package model.components;
import java.util.ArrayList;
import java.util.Random;

public class gameBoard {
    ArrayList<String> firstPlayerGameBoard = new ArrayList<>();
    ArrayList<String> secondPlayerGameBoard = new ArrayList<>();
    public gameBoard() {
        Random random = new Random();
        Integer playerOneRandomIndex = random.nextInt(21);
        Integer playerTwoRandomIndex = random.nextInt(21);
        for (int i = 0; i < 21; i++) {
            if(i != playerOneRandomIndex){
            firstPlayerGameBoard.add(i, "0");
            }
            firstPlayerGameBoard.add(playerOneRandomIndex, "1");
        }
        for (int i = 0; i < 21; i++) {
            if(i != playerTwoRandomIndex){
                secondPlayerGameBoard.add(i, "0");
            }
            secondPlayerGameBoard.add(playerTwoRandomIndex, "1");
        }
    }
}
