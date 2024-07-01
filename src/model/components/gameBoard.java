package model.components;
import java.util.ArrayList;
import java.util.Random;

public class gameBoard {
    ArrayList<String> Board = new ArrayList<>();

    public gameBoard() {
        Random random = new Random();
        Integer playerRandomIndex = random.nextInt(21);
        for (int i = 0; i < 21; i++) {
            if(i != playerRandomIndex){
            Board.add(i, "0");
            }
            Board.add(playerRandomIndex, "1");
        }

    }

    public ArrayList<String> getBoard() {
        return Board;
    }

    public void setBoard(ArrayList<String> playerGameBoard) {
        Board = playerGameBoard;
    }

}
