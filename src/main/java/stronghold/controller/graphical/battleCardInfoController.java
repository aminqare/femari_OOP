package stronghold.controller.graphical;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import stronghold.model.CardsDB;
import stronghold.model.cards.cards;
import stronghold.model.components.Player;
import stronghold.model.components.User;
import stronghold.model.components.superGame;
import stronghold.model.graphical.cell;
import stronghold.model.graphical.graphicalCards;
import stronghold.model.specialCards.builder;
import stronghold.model.specialCards.specialCards;
import stronghold.view.GameMenuView;
import stronghold.view.timeLineMenu;

import java.util.ArrayList;
import java.util.Objects;

import static stronghold.controller.graphical.GameMenuController.switchTurns;
import static stronghold.controller.graphical.HubMenuController.updateDB;

public class battleCardInfoController {
    private static cell cell;
    private static User currentUser;
    public Label NameField;
    public Label LevelField;
    public Label DurationField;
    public Label Damage;
    public Label Attack;
    public Label Defence;
    public Label PriceField;
    public Label UpgradeCost;
    public static superGame currentSuperGame;
    public TextField BlockIndex;


    public Label getNameField() {
        return NameField;
    }

    public void setNameField(Label nameField) {
        NameField = nameField;
    }

    public Label getLevelField() {
        return LevelField;
    }

    public void setLevelField(Label levelField) {
        LevelField = levelField;
    }

    public Label getDurationField() {
        return DurationField;
    }

    public void setDurationField(Label durationField) {
        DurationField = durationField;
    }

    public Label getDamage() {
        return Damage;
    }

    public void setDamage(Label damage) {
        Damage = damage;
    }

    public Label getAttack() {
        return Attack;
    }

    public void setAttack(Label attack) {
        Attack = attack;
    }

    public Label getDefence() {
        return Defence;
    }

    public void setDefence(Label defence) {
        Defence = defence;
    }

    public Label getPriceField() {
        return PriceField;
    }

    public void setPriceField(Label priceField) {
        PriceField = priceField;
    }

    public Label getUpgradeCost() {
        return UpgradeCost;
    }

    public void setUpgradeCost(Label upgradeCost) {
        UpgradeCost = upgradeCost;
    }

    public superGame getCurrentSuperGame() {
        return currentSuperGame;
    }

    public static void setCurrentSuperGame(superGame CurrentSuperGame) {
        currentSuperGame = CurrentSuperGame;
    }

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
        battleCardInfoController.cell = cell;
    }

    @FXML
    public void initialize() {
        setText();
    }

    public void setText() {
        if (currentUser != null) {
            cards MyCard = cards.GetCardByName(currentUser.getPlayerCards(), cell.getName());
            if (MyCard != null) {
                NameField.setText("Name: " + MyCard.getName());
                LevelField.setText("Level: " + MyCard.getLevel());
                PriceField.setText("Price: " + MyCard.getPrice());
                DurationField.setText("Duration: " + MyCard.getDuration());
                Damage.setText("Damage: " + MyCard.getDamage());
                Attack.setText("Attack: " + MyCard.getAttack());
                Defence.setText("Defence: " + MyCard.getDefence());
                UpgradeCost.setText("Upgrade Cost: " + MyCard.getUpgradeCost());
            } else {
                cards cardd = cards.GetCardByName(CardsDB.getCards(), cell.getName());
                NameField.setText("Name: " + cardd.getName());
                LevelField.setText("Level: " + cardd.getLevel());
                PriceField.setText("Price: " + cardd.getPrice());
                DurationField.setText("Duration: " + cardd.getDuration());
                Damage.setText("Damage: " + cardd.getDamage());
                Attack.setText("Attack: " + cardd.getAttack());
                Defence.setText("Defence: " + cardd.getDefence());
                UpgradeCost.setText("Upgrade Cost: " + cardd.getUpgradeCost());
            }
        } else {
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


    public void placeCard(MouseEvent mouseEvent) {
        Player player = currentSuperGame.getCurrentGame().getCurrentPlayer();
        Player enemyPlayer = currentSuperGame.getCurrentGame().getCurrentEnemy();
        String text = BlockIndex.getText();
        int numberBlock = Integer.parseInt(text);

        if (cell.getName() == null) {
            System.out.println("Cell name is null");
        } else {
            System.out.println("Cell name: " + cell.getName());
        }

        if (player == null) {
            System.out.println("Player is null");
            return; // Add return to stop further execution if player is null
        }

        cards selectedCard = cards.GetCardByName(player.getUser().getPlayerCards(), cell.getName());

        if (selectedCard == null) {
            System.out.println("Selected card is null");
            return; // Add return to stop further execution if selectedCard is null
        } else {
            System.out.println("Selected card: " + selectedCard.getName());
        }

        Integer duration = selectedCard.getDuration(); // Change to Integer to check for null

        if (duration == null) {
            System.out.println("Card duration is null for card: " + selectedCard.getName());
            return; // Add return to stop further execution if duration is null
        } else {
            System.out.println("Card duration: " + duration);
        }

        if ((numberBlock - 2) + duration > 20) {
            openErrorDialog("Invalid Move: Duration exceeds board limit");
        } else if (!Objects.equals(player.getGameBoard().getBoard().get(numberBlock - 1), "0")) {
            openErrorDialog("Invalid Move: Block is not empty");
        } else if (!GameMenuView.intract(selectedCard, player, numberBlock - 1).isEmpty()) {
            openErrorDialog("Invalid Move: Interaction failed");
        } else {
            ArrayList<Integer> Interacts = GameMenuView.intract(selectedCard, enemyPlayer, numberBlock - 1);
            for (int i = numberBlock - 1; i < (numberBlock + selectedCard.getDuration() - 1); i++) {
                if (Interacts.contains(i)) {
                    cards enemyCard = cards.GetCardByName(enemyPlayer.getUser().getPlayerCards(), enemyPlayer.getGameBoard().getBoard().get(i));
                    Player temp = GameMenuView.cardFaceOfWinner(player, enemyPlayer, selectedCard, enemyCard);
                    if (temp == null) {
                        enemyPlayer.getGameBoard().getBoard().set(i, "Hole");
                        player.getGameBoard().getBoard().set(i, "Hole");
                    } else if (temp.getUsername().equals(player.getUsername())) {
                        enemyPlayer.getGameBoard().getBoard().set(i, "Hole");
                        player.getGameBoard().getBoard().add(i, selectedCard.getName());
                    } else if (temp.getUsername().equals(enemyPlayer.getUsername())) {
                        player.getGameBoard().getBoard().set(i, "Hole");
                    }
                } else {
                    player.getGameBoard().getBoard().add(i, selectedCard.getName());
                }
            }
            player.getCardsCell().remove(cell);
            player.getPlayerCardsDeck().remove(selectedCard);
            player.setRounds(player.getRounds() - 1);
            player.getGameBoard().getBoard().set(numberBlock - 1, selectedCard.getName());
            openMessageDialog("Card placed successfully!");
            switchTurns(currentSuperGame.getCurrentGame());

        }
    }

}
