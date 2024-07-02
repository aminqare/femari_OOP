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
    public  mehradHidden(){
        this.setDuration(4);
        this.setName("mehrad Hidden");
        this.setPrice(42);
    }

    public void run(Game game) {
        game.getCurrentEnemy().setHasMehradHidden(true);
    }

}
