
package stronghold.model.components;


import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class GraphicalCaptcha {

    private static int SIZE = 10;

    private static Color primaryColor = new Color(0.305, 0.551, 0.797, 1);
    private static Color backgroundColor = new Color(.578, .742, .867, 1);
    private static Color noiseColor = new Color(0.441,0.598,0.938, 1);
    public Group graphicalCaptcha;
    public String answer;

    public GraphicalCaptcha(){
        Captcha captcha = new Captcha(false);
        String generatedCaptcha = captcha.getGeneratedCaptcha();

        this.graphicalCaptcha = new Group();
        this.answer = captcha.getAccordingNum();

        String[] rows = generatedCaptcha.split("\n");

        char[][] array = new char[5][rows[0].length()];
        int row = 0, col = 0;

        for(String line: rows){
            for(char c: line.toCharArray()){
                array[row][col] = c;
                col++;
            }
            col = 0;
            row++;
        }


        for(int i = 0; i < 5; i++){
            for(int j = 0; j < array[i].length; j++){
                int randScale = new Random().nextInt(4);
                int randOffset = new Random().nextInt(1 );
                Rectangle pixel = new Rectangle(j*SIZE+randOffset,
                        i*SIZE+randOffset,(SIZE+ randScale),SIZE + randScale);
                pixel.setFill(backgroundColor);
                if(array[i][j] == '#')
                    pixel.setFill(primaryColor);
                else if(array[i][j] == '!')
                    pixel.setFill(noiseColor);
                this.graphicalCaptcha.getChildren().add(pixel);
            }
        }
    }

}
