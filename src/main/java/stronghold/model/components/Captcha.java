package stronghold.model.components;

import java.util.HashMap;
import java.util.Random;

public class Captcha {
    private String accordingNum = "";
    private String generatedCaptcha;

    private String colorify(String row){
        row = row.replaceAll("#","\033[44m \033[0m");
        row = row.replaceAll("!","\033[46m \033[0m");
        return row;
    }

    public String getAccordingNum() {
        return accordingNum;
    }

    public String getGeneratedCaptcha() {
        return generatedCaptcha;
    }

    private String noisify(String row){
        Random random = new Random();
        for(int i = 0; i < random.nextInt(3)+2;i++){
            int randomlySelectedIndex = random.nextInt(row.length());
            if(row.toCharArray()[randomlySelectedIndex] == '#'){
                i--;
                continue;
            }
            row = row.substring(0,randomlySelectedIndex) + "!" +
                    row.substring(randomlySelectedIndex+1,row.length());
        }
        return row;
    }
    private String[] firstRows = {"#####","    #","#####",
            "#####","#   #","#####","#####","#####","#####", "#####"};
    private String[] secondRows = {"#   #","  ###","    #","    #","#   #",
            "#    ","#    ","   # ","#   #","#   #"};

    private String[] thirdRows = {"#   #","    #","#####","#####","#####",
            "#####","#####","  #  ","#####","#####"};

    private String[] fourthRows = {"#   #","    #","#    ","    #","    #",
            "    #","#   #"," #   ","#   #","    #"};

    private String[] fifthRows = {"#####","    #","#####","#####","    #",
            "#####","#####","#    ","#####","#####"};


    public Captcha(){
        Random random = new Random();

        String firstRow = "", secondRow = "", thirdRow = "", fourthRow = "", fifthRow = "";

        int size = 4 + random.nextInt(5);
        for(int i = 0;i<size;i++){
            int thisDigit = random.nextInt(10);
            this.accordingNum += String.valueOf(thisDigit);
            firstRow += firstRows[thisDigit] + "   ";
            secondRow += secondRows[thisDigit] + "   ";
            thirdRow += thirdRows[thisDigit] + "   ";
            fourthRow += fourthRows[thisDigit] + "   ";
            fifthRow += fifthRows[thisDigit] + "   ";
        }

        firstRow = colorify(noisify(firstRow));
        secondRow = colorify(noisify(secondRow));
        thirdRow = colorify(noisify(thirdRow));
        fourthRow = colorify(noisify(fourthRow));
        fifthRow = colorify(noisify(fifthRow));


        this.generatedCaptcha = firstRow + "\n" + secondRow + "\n" + thirdRow + "\n" + fourthRow + "\n" + fifthRow;
    }

    public Captcha(boolean colorized){
        Random random = new Random();

        String firstRow = "", secondRow = "", thirdRow = "", fourthRow = "", fifthRow = "";

        int size = 4 + random.nextInt(5);
        for(int i = 0;i<size;i++){
            int thisDigit = random.nextInt(10);
            this.accordingNum += String.valueOf(thisDigit);
            firstRow += firstRows[thisDigit] + "   ";
            secondRow += secondRows[thisDigit] + "   ";
            thirdRow += thirdRows[thisDigit] + "   ";
            fourthRow += fourthRows[thisDigit] + "   ";
            fifthRow += fifthRows[thisDigit] + "   ";
        }

        firstRow = (noisify(firstRow));
        secondRow = (noisify(secondRow));
        thirdRow = (noisify(thirdRow));
        fourthRow = (noisify(fourthRow));
        fifthRow = (noisify(fifthRow));


        this.generatedCaptcha = firstRow + "\n" + secondRow + "\n" + thirdRow + "\n" + fourthRow + "\n" + fifthRow;
    }

    public static void main(String[] args) {
        Captcha captcha = new Captcha();
        System.out.println(captcha.generatedCaptcha);
        System.out.println(captcha.accordingNum);

    }
}