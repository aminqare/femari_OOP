package stronghold.model.graphical;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class graphicalCards extends Rectangle {
    private String name;
    private boolean locked;
    private String Type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public graphicalCards(String name, String Type){
        super(150,206);
        this.Type=Type;
        this.name=name;
        if(Type.equals("specialCards")) {
            setFill(new ImagePattern(new Image(graphicalCards.class.getResource("/images/specialCards/" + name + ".png").toExternalForm())));
        }else{
            setFill(Color.BLACK);
        }
    }
}
