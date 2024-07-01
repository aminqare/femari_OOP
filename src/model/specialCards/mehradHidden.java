package model.specialCards;

import model.components.Game;

//makkhfi konandeh
public class mehradHidden extends specialCards {

    @Override
    void run(Object param) {
        if (param instanceof Game) {
            run((Game) param);
        } else {
            System.out.println("ridi");
        }
    }

    public void run(Game game) {
        game.getCurrentEnemy().setHasMehradHidden(true);
    }

}
