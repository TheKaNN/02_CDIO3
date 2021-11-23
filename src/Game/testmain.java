package Game;

import GUI.GUIMain;
import Game.Rafflecup;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class testmain {

    public static void main(String[] args) {
        //Rafflecup r1=new Rafflecup(2,6);
        //r1.rollar();
        //System.out.println(Arrays.toString(r1.getCup()));

        TXTReader myReader = new TXTReader(Settings.FIELD_DATABASE);
        String test = myReader.readTXTFile();
        System.out.println(test);

        Board myBoard = new Board(Settings.FIELD_DATABASE, Settings.BOARD_SIZE);
        myBoard.generateBoard();

        Field[] myFields = myBoard.getMyFields();

        for(int i = 0; i < Settings.BOARD_SIZE; i++){
            System.out.println("NUMBER " + i + ": " + myFields[i].getfType() + myFields[i].getColor() + myFields[i].getPrice() + myFields[i].getName());
        }


        GUIMain myGui = new GUIMain(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, 24, myFields);
    }
    public Player[] generateplayers(int amount){
        Scanner playername= new Scanner(System.in);

        Player[] players= new Player[amount];
        for (int i = 0; i < amount; i++) {
            System.out.println("Indtast navn på spiller: "+i);
            players[i]=new Player(playername.nextLine());

        }
        return players;

    }
}
