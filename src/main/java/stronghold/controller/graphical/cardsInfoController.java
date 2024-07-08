package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import stronghold.model.CardsDB;
import stronghold.model.cards.cards;
import stronghold.model.components.User;
import stronghold.model.graphical.graphicalCards;
import stronghold.model.specialCards.specialCards;

import static stronghold.controller.graphical.HubMenuController.updateDB;

public class cardsInfoController {
    private static graphicalCards card;
    private static User currentUser;
    public Label NameField;
    public Label LevelField;
    public Label DurationField;
    public Label Damage;
    public Label Attack;
    public Label Defence;
    public Label PriceField;
    public Label UpgradeCost;
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
       setText();
    }
    public void setText(){
        if (currentUser != null) {
            cards MyCard = cards.GetCardByName(currentUser.getPlayerCards(),card.getName());
            if (MyCard != null) {
                NameField.setText("Name: " + MyCard.getName());
                LevelField.setText("Level: " + MyCard.getLevel());
                PriceField.setText("Price: " + MyCard.getPrice());
                DurationField.setText("Duration: " + MyCard.getDuration());
                Damage.setText("Damage: "+MyCard.getDamage());
                Attack.setText("Attack: "+MyCard.getAttack());
                Defence.setText("Defence: "+MyCard.getDefence());
                UpgradeCost.setText("Upgrade Cost: "+MyCard.getUpgradeCost());
            } else {
                cards cardd = cards.GetCardByName(CardsDB.getCards(),card.getName());
                NameField.setText("Name: " + cardd.getName());
                LevelField.setText("Level: " + cardd.getLevel());
                PriceField.setText("Price: " + cardd.getPrice());
                DurationField.setText("Duration: " + cardd.getDuration());
                Damage.setText("Damage: "+cardd.getDamage());
                Attack.setText("Attack: "+cardd.getAttack());
                Defence.setText("Defence: "+cardd.getDefence());
                UpgradeCost.setText("Upgrade Cost: "+cardd.getUpgradeCost());
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

        cards MyCard = cards.GetCardByName(currentUser.getPlayerCards(),card.getName());
        if(MyCard!=null){
            openErrorDialog("you already have this card!");
            return;
        }else{
            cards cardd = cards.GetCardByName(CardsDB.getCards(),card.getName());
            if(cardd.getPrice() > currentUser.getGold()){
                openErrorDialog("you don't have enough gold");
                return;
            }
            else{
                currentUser.getPlayerCards().add(cardd);
                currentUser.setGold(currentUser.getGold() - cardd.getPrice());
                updateDB(currentUser);
                //  ShopController.setUserID();
                openMessageDialog(cardd.getName()+" added to your cards");
            }
        }

    }

    public void upgrade(MouseEvent mouseEvent) {
        cards MyCard = cards.GetCardByName(currentUser.getPlayerCards(),card.getName());
        if(MyCard==null){
            openErrorDialog("you don't have this card!");
            return;
        }else{
            if(MyCard.getUpgradeCost() > currentUser.getGold()){
                openErrorDialog("you don't have enough gold");
                return;
            } else{
                currentUser.setGold(currentUser.getGold() - MyCard.getUpgradeCost());
                MyCard.powerUp();
                MyCard.setLevel(MyCard.getLevel()+1);
                updateDB(currentUser);
                setText();
                // ShopController.setUserID();
                openMessageDialog(MyCard.getName()+" has leveled up!");
            }
        }
    }
}
