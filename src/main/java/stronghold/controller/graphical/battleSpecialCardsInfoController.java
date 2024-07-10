package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import stronghold.model.cards.cards;
import stronghold.model.components.Game;
import stronghold.model.components.Player;
import stronghold.model.components.User;
import stronghold.model.graphical.cell;
import stronghold.model.graphical.graphicalCards;
import stronghold.model.specialCards.builder;
import stronghold.model.specialCards.specialCards;
import stronghold.view.GameMenuView;
import stronghold.view.shopMenuView;
import stronghold.view.timeLineMenu;

import java.util.ArrayList;
import java.util.Objects;

import static stronghold.controller.graphical.GameMenuController.switchTurns;
import static stronghold.controller.graphical.HubMenuController.updateDB;
import static stronghold.controller.graphical.battleCardInfoController.currentSuperGame;
import static stronghold.view.GameMenuView.intractSpecial;
import static stronghold.view.GameMenuView.runSpecialCards;

public class battleSpecialCardsInfoController {
    public Label NameField;
    public Label LevelField;
    public Label PriceField;
    public Label DurationField;
    private static cell cell;
    private static User currentUser;
    public TextField BlockIndex;


    public User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public static stronghold.model.graphical.cell getCell() {
        return cell;
    }

    public static void setCell(stronghold.model.graphical.cell cell) {
        battleSpecialCardsInfoController.cell = cell;
    }

    @FXML
    public void initialize() {
        setTex();
    }
    public void setTex(){
        if (currentUser != null) {
            specialCards MyspecialCard = specialCards.GetSpecialCardByName(currentUser.getPlayerSpecialCards(), cell.getName());
            if (MyspecialCard != null) {
                NameField.setText("Name: " + MyspecialCard.getName());
                LevelField.setText("Level: " + MyspecialCard.getLevel());
                PriceField.setText("Price: " + MyspecialCard.getPrice());
                DurationField.setText("Duration: " + MyspecialCard.getDuration());
            } else {
                specialCards specialCard = specialCards.GetSpecialCardByName(specialCards.getGameSpecialCards(), cell.getName());
                NameField.setText("Name: " + specialCard.getName());
                LevelField.setText("Level: " + specialCard.getLevel());
                PriceField.setText("Price: " + specialCard.getPrice());
                DurationField.setText("Duration: " + specialCard.getDuration());
            }
        }else{
            System.out.println("user is null");
        }
    }
    public static void openErrorDialog(String error) {
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


    public void placeCard(MouseEvent mouseEvent) {
        Player player = currentSuperGame.getCurrentGame().getCurrentPlayer();
        Player enemyPlayer = currentSuperGame.getCurrentGame().getCurrentEnemy();
        System.out.println(player.getUser().getUsername());
        String text = BlockIndex.getText();
        int numberBlock = Integer.parseInt(text);
            specialCards selectedSpecialCard = specialCards.GetSpecialCardByName(player.getUser().getPlayerSpecialCards(), cell.getName());
            int duration = selectedSpecialCard.getDuration();
            if (Objects.equals(selectedSpecialCard.getName(), "builder")) {
                builder.run(currentSuperGame.getCurrentGame(), (numberBlock - 1));
                player.getPlayerSpecialCardsDeck().remove(selectedSpecialCard);
                switchTurns(currentSuperGame.getCurrentGame());
            } else {
                if ((numberBlock - 2) + duration > 20) {
                    openErrorDialog("Invalid Move: Interaction failed");
                } else if (!Objects.equals(player.getGameBoard().getBoard().get(numberBlock - 1), "0")) {
                    openErrorDialog("Invalid Move: Interaction failed");
                } else if (!intractSpecial(selectedSpecialCard, player, numberBlock - 1).isEmpty()) {
                    openErrorDialog("Invalid Move: Interaction failed");
                } else {
                    runSpecialCards(currentSuperGame.getCurrentGame(), selectedSpecialCard, numberBlock - 1);
                    player.getPlayerSpecialCardsDeck().remove(selectedSpecialCard);
                    player.getSpecialCardsCell().remove(cell);
                    player.setRounds(player.getRounds() - 1);
                    player.getGameBoard().getBoard().set(numberBlock - 1, selectedSpecialCard.getName());
                    openMessageDialog("Card placed successfully!");
                    switchTurns(currentSuperGame.getCurrentGame());

                }
            }


    }
    public static void runSpecialCards(Game game, specialCards card, int BlockIndex){
        String cardName = card.getName();
        if(cardName.equals("shield")){
            String oposite = game.getCurrentEnemy().getGameBoard().getBoard().get(BlockIndex);
            if(oposite.equals("0")||oposite.equals("1")){
                openErrorDialog("Invalid Move: Interaction failed");
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
}