package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import stronghold.model.components.User;
import stronghold.model.graphical.graphicalCards;
import stronghold.model.specialCards.specialCards;
import stronghold.view.shopMenuView;

import static stronghold.controller.graphical.HubMenuController.updateDB;

public class specialCardsInfoController {
    public Label NameField;
    public Label LevelField;
    public Label PriceField;
    public Label DurationField;
    private static graphicalCards card;
    private static User currentUser;
    public Button BuyButtom;
    public Button UpgradeButtom;

    public User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public graphicalCards getCard() {
        return card;
    }

    public static void setCard(graphicalCards newcard) {
        card = newcard;
    }
    @FXML
    public void initialize() {
        setTex();
    }
    public void setTex(){
        if (currentUser != null) {
            specialCards MyspecialCard = specialCards.GetSpecialCardByName(currentUser.getPlayerSpecialCards(), card.getName());
            if (MyspecialCard != null) {
                NameField.setText("Name: " + MyspecialCard.getName());
                LevelField.setText("Level: " + MyspecialCard.getLevel());
                PriceField.setText("Price: " + MyspecialCard.getPrice());
                DurationField.setText("Duration: " + MyspecialCard.getDuration());
            } else {
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), card.getName());
                NameField.setText("Name: " + specialCard.getName());
                LevelField.setText("Level: " + specialCard.getLevel());
                PriceField.setText("Price: " + specialCard.getPrice());
                DurationField.setText("Duration: " + specialCard.getDuration());
            }
        }else{
            System.out.println("user is null");
        }
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


    public void Buy(MouseEvent mouseEvent) {

        specialCards MyspecialCard = specialCards.GetSpecialCardByName(currentUser.getPlayerSpecialCards(), card.getName());
        if(MyspecialCard!=null){
            openErrorDialog("you already have this card!");
            return;
        }else{
            specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), card.getName());
            if(specialCard.getPrice() > currentUser.getGold()){
                openErrorDialog("you don't have enough gold");
                return;
            }
            else{
                currentUser.getPlayerSpecialCards().add(specialCard);
                currentUser.setGold(currentUser.getGold() - specialCard.getPrice());
                updateDB(currentUser);
                //  ShopController.setUserID();
                openMessageDialog(specialCard.getName()+" added to your cards");
            }
        }

    }

    public void upgrade(MouseEvent mouseEvent) {
        specialCards MyspecialCard = specialCards.GetSpecialCardByName(currentUser.getPlayerSpecialCards(), card.getName());
        if(MyspecialCard==null){
            UpgradeButtom.setStyle("-fx-background-color:GREY");
            openErrorDialog("you don't have this card!");
            return;
        }else{
            if(MyspecialCard.getPrice() > currentUser.getGold()){
                openErrorDialog("you don't have enough gold");
                return;
            }
            else{
                MyspecialCard.setLevel(MyspecialCard.getLevel()+1);
                currentUser.setGold(currentUser.getGold() - MyspecialCard.getPrice());
                updateDB(currentUser);
                setTex();
                // ShopController.setUserID();
                openMessageDialog(MyspecialCard.getName()+" has leveled up!");
            }
        }
    }
}
