package model.specialCards;

import model.cards.cards;
import model.components.Game;
import model.components.Player;

import java.util.ArrayList;
import java.util.Random;

// kam konande kart az dast harif
public class cardSnatcher extends specialCards {
    public cardSnatcher() {
        this.setDuration(0);
        this.setName("cardSnatcher");
        this.setPrice(120);
    }

    @Override
    public void run(Object param) {
        if (param instanceof Game) {
            run((Game) param);
        } else {
            System.out.println("ridi");
        }
    }

    public static void run(Game game) {
        Player player = game.getCurrentPlayer();
        Player enemy = game.getCurrentEnemy();
        Double random = game.getRandom().nextDouble(1);
        if (random < 0.7) {
            moveCard(player, enemy, game.getRandom());
        } else {
            moveSpecialCard(player, enemy, game.getRandom());
        }
    }

    public static void moveCard(Player player, Player enemy, Random random) {
        ArrayList<cards> enemyCards = enemy.getPlayerCardsDeck();
        if (!enemyCards.isEmpty()) {
            int Index = random.nextInt(enemyCards.size());
            player.getPlayerCardsDeck().add(enemyCards.get(Index));
            enemyCards.remove(Index);
        } else if (!enemy.getPlayerSpecialCardsDeck().isEmpty()) {
            moveSpecialCard(player, enemy, random);
        } else {
            System.out.println("Enemy doesn't have any card");
        }
    }

    public static void moveSpecialCard(Player player, Player enemy, Random random) {
        ArrayList<specialCards> enemyCards = enemy.getPlayerSpecialCards();
        if (!enemyCards.isEmpty()) {
            int Index = random.nextInt(enemyCards.size());
            player.getPlayerSpecialCards().add(enemyCards.get(Index));
            enemyCards.remove(Index);
        } else if (!enemy.getPlayerCardsDeck().isEmpty()) {
            moveSpecialCard(player, enemy, random);
        } else {
            System.out.println("Enemy doesn't have any card");
        }
    }
}
