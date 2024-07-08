package stronghold.controller.graphical;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import stronghold.model.CardsDB;
import stronghold.model.cards.cards;
import stronghold.model.components.User;
import stronghold.model.graphical.graphicalCards;
import stronghold.model.specialCards.specialCards;
import stronghold.model.utils.SpecialCardsDB;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ShopController {
    public static User user;
    public HBox cardsBar;
    public HBox specialCardsBar;
    //public FlowPane specialCardsBar;
    public Label userID;
    public ProgressBar XpProgress;
    public ScrollPane scrollPane2;

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
            graphicalCards card = new graphicalCards(specialCards.getGameSpecialCards().get(i).getName(), "specialCards");
            card.getStyleClass().add("card");
            card.setOnMouseEntered(event -> card.setScaleX(1.1));
            card.setOnMouseExited(event -> card.setScaleX(1.0));
            specialCardsBar.getChildren().add(card);
            addFadeInEffect(card);
        }
    }
    public void addCardsToBar()
    {
        for (int i = 0; i < CardsDB.getCards().size(); i++) {
            graphicalCards card = new graphicalCards(CardsDB.getCards().get(i).getName(), "cards");
            card.getStyleClass().add("card");
            card.setOnMouseEntered(event -> card.setScaleX(1.1));
            card.setOnMouseExited(event -> card.setScaleX(1.0));
            cardsBar.getChildren().add(card);
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
    public void addFadeInEffect(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(300), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public void setUserID(){
        if(user!=null){

            userID.setText(user.getUsername()+"\n\tGold: "+user.getGold()+"\n\tLevel: "+ user.getLevel());

        }else{
            System.out.println("user is null");
        }
    }
}