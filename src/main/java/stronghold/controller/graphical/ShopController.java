package stronghold.controller.graphical;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stronghold.model.CardsDB;
import stronghold.model.UsersDB;
import stronghold.model.components.User;
import stronghold.model.graphical.graphicalCards;
import stronghold.model.specialCards.specialCards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Callable;

public class ShopController {
    public static User user;
    public HBox cardsBar;
    public HBox specialCardsBar;
    public Label userID;
    public ProgressBar XpProgress;

    public static User getUser() {
        return user;
    }

    public static void setUser(User Newuser) {
        user = Newuser;
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
    public void addSpecialCardsToBar()
    {
        for (int i = 0; i < specialCards.getGameSpecialCards().size(); i++) {
            System.out.println(specialCards.getGameSpecialCards().get(i).getName());
            graphicalCards card=new graphicalCards(specialCards.getGameSpecialCards().get(i).getName(),"specialCards");
            specialCardsBar.getChildren().add(card);
            card.setOnMouseClicked(event->openSpecialCardInfo(card));


        }
    }
    public void addCardsToBar()
    {
        for (int i = 0; i < CardsDB.getCards().size(); i++) {
            graphicalCards card=new graphicalCards(CardsDB.getCards().get(i).getName(),"cards");
            cardsBar.getChildren().add(card);
            card.setOnMouseClicked(event->openCardsInfo(card));

        }
    }
    @FXML
    public void initialize(){
        user.setMaxXP();

       addSpecialCardsToBar();
       addCardsToBar();

            setUserID();

        DoubleProperty xp = new SimpleDoubleProperty(user.getXP());
       XpProgress.progressProperty().bind(xp.divide(user.getMaxXP()));
    }
    public  void setUserID(){
        if(user!=null){

            userID.setText(user.getUsername()+"\n\tGold: "+user.getGold()+"\n\tLevel: "+ user.getLevel());

        }else{
            System.out.println("user is null");
        }
    }
    private void openSpecialCardInfo(graphicalCards card){
User user= UsersDB.usersDB.getUserByUsername(ShopController.getUser().getUsername());
        specialCardsInfoController.setCurrentUser(user);
        specialCardsInfoController.setCard(card);
        Pane root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/specialCardsInfo.fxml")));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(e->{
            setUserID();
        });
        stage.showAndWait();

    }
    private void openCardsInfo(graphicalCards card){
        User user= UsersDB.usersDB.getUserByUsername(ShopController.getUser().getUsername());
        cardsInfoController.setCurrentUser(user);
        cardsInfoController.setCard(card);
        Pane root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cardsInfo.fxml")));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(e->{
            setUserID();
        });
        stage.showAndWait();

    }
}
