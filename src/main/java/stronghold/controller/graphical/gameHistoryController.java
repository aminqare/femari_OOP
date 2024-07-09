package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import stronghold.model.components.User;
import stronghold.model.components.gameHistory;
import stronghold.view.mainMenuView;

import java.util.ArrayList;
import java.util.Collections;

import static stronghold.view.mainMenuView.levelComparator;

public class gameHistoryController {

    public Label GAMEHISTORY;
    private static User user;
    public Button Date;
    public Button Level;
    public Button Name;
    public Button winLoss;
    public Button next;
    public Button previos;
    public Label pageNumber;
    private int page=1;
    private String sortingType="date";


    public User getUser() {
        return user;
    }

    public static void setUser(User newuser) {
        user = newuser;
    }
    public void openErrorDialog(String error) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Error!");
        Label label = new Label(error);
        dialog.setContentText(label.getText());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().getChildren().add(label);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();
    }
    public void openMessageDialog(String error) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Message");
        Label label = new Label(error);
        dialog.setContentText(label.getText());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().getChildren().add(label);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();
    }

    @FXML
    public void initialize() {
        sortTextAndShow(sortingType);
        pageNumber.setText(String.valueOf(getPage()));
    }

    public void sortTextAndShow(String sortType) {
        ArrayList<gameHistory> History = user.getGameHistory();
        if (sortType.equals("name")) {
            History.sort(mainMenuView.nameComparator);
            setText(History,page);
        } else if (sortType.equals("level")) {
            History.sort(mainMenuView.levelComparator);
            setText(History,page);
        } else if (sortType.equals("gameState")) {
            History.sort(mainMenuView.winLossComparator);
            setText(History,page);
        } else if (sortType.equals("date")) {
            History.sort(mainMenuView.dateComparator);
            setText(History,page);
        }
    }

    public void setText(ArrayList<gameHistory> gameHistories,int page) {
        StringBuilder output = new StringBuilder();
        for (int i = (page-1)*6+1; i <(page*6) ; i++) {
            if(gameHistories.size()>i) {
                output.append((i) + "." + gameHistories.get(i - 1).toString() + "\n\n");
            }else{
                continue;
            }
        }
        GAMEHISTORY.setText(output.toString());
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSortingType() {
        return sortingType;
    }

    public void setSortingType(String sortingType) {
        this.sortingType = sortingType;
    }

    public void DateSort(MouseEvent mouseEvent) {

        sortTextAndShow("date");
        setSortingType("date");
    }

    public void levelSort(MouseEvent mouseEvent) {
        sortTextAndShow("level");
        setSortingType("level");
    }

    public void nameSort(MouseEvent mouseEvent) {
        sortTextAndShow("name");
        setSortingType("name");
    }

    public void gameStateSort(MouseEvent mouseEvent) {
        sortTextAndShow("gameState");
        setSortingType("gameState");
    }

    public void nextPage(MouseEvent mouseEvent) {
        if(user.getGameHistory().size()<=(page)*6){
            openErrorDialog("next page is empty");
        }else{
            setPage(getPage()+1);
            pageNumber.setText(String.valueOf(getPage()));
            sortTextAndShow(sortingType);
        }
    }

    public void previousPage(MouseEvent mouseEvent) {
        if(page==1){
            openErrorDialog("There is no previous page");
        }else{
            setPage(getPage()-1);
            pageNumber.setText(String.valueOf(getPage()));
            sortTextAndShow(sortingType);
        }
    }
}
