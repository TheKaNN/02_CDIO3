package Game;

import java.io.IOException;

public class ChanceCardGenerate {


    public static void main(String[] args) throws IOException {
        String[] text = ChancekortReader.text();
        ChanceMoneyOrMove c16 = new ChanceMoneyOrMove("Modtag 2", text[16], 16, 2, 0, 0);
        ChanceMoneyOrMove c2 = new ChanceMoneyOrMove("Modtag 2", text[1], 2, 2, 0, 0);
        ChanceMoneyOrMove c1 = new ChanceMoneyOrMove("Giv Bil kort",text[0],1,0,0,0);
        System.out.println(c2.toString());
        System.out.println(c16.getText());
        System.out.println(c2.getText());
    }
}