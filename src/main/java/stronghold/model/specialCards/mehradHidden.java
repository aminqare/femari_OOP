package stronghold.model.specialCards;

import stronghold.model.components.Game;

//makkhfi konandeh
public class mehradHidden extends specialCards {

    @Override
    public void run(Object param) {
        if (param instanceof Game) {
            run((Game) param);
        } else {
            System.out.println("ridi");
        }
    }
    public  mehradHidden(){
        this.setDuration(0);
        this.setName("mehradHidden");
        this.setPrice(42);
    }

    public static void run(Game game) {
        game.getCurrentEnemy().setHasMehradHidden(true);
    }

}
