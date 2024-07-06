
package stronghold.controller.graphical;

import javafx.application.Application;
import javafx.stage.Stage;
import stronghold.view.graphics.CaptchaView;

public class GraphicalCaptchaController {

    public static boolean lastResult = false;
    public static boolean controlSubmission(String text, String answer){
        boolean result = text.equals(answer);
        lastResult = result;
        return result;
    }

    public static boolean generateCaptcha(){
        CaptchaView captchaView = new CaptchaView();
        captchaView.start(new Stage());
        return lastResult;
    }

    public static void main(String[] args) {
        System.out.println(generateCaptcha());
    }
}
