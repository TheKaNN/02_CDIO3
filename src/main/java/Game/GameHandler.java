package Game;

import GUI.GUIMain;
import GUI.PopupBox;

import java.sql.SQLOutput;
import java.util.Scanner;

public class GameHandler {


    public GameHandler() {

    }

    public void startGame() throws InterruptedException {

        String[] printLinesInProgram= new String[10];

        try {
             printLinesInProgram = TXTReader.udskrift("Udskrift.txt");
        }
        catch (Exception e){

        }


        Rafflecup r1 = new Rafflecup(1, 6);
        r1.rollar();
        System.out.flush();
        System.out.println("Linje 8");

        for (int i = 0; i < printLinesInProgram.length; i++) {
            System.out.println(printLinesInProgram[i]);
        }

        System.out.println(printLinesInProgram[9]);
        System.out.println("Linje 9");
        System.out.print(printLinesInProgram[10]);


        int playerAmount = 100;
        while (playerAmount < Settings.MIN_PLAYERS || Settings.MAX_PLAYERS < playerAmount) {
            PopupBox myPop = new PopupBox(printLinesInProgram[0], printLinesInProgram[1]);
            playerAmount = myPop.popup().charAt(0) - 48;
        }
        Player[] players = generateplayers(playerAmount, printLinesInProgram);
        System.out.println("før gui");
        // Generates the play board.
        Board myBoard = new Board(Settings.FIELD_DATABASE, Settings.BOARD_SIZE);
        myBoard.generateBoard();
        GUIMain myGui = new GUIMain(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, Settings.BOARD_SIZE, myBoard.getMyFields(), players, playerAmount);
        System.out.println("Efter gui");
        myGui.updateGUI();
        myGui.updateGUI();
        myGui.updateGUI();
        Scanner myScanner2 = new Scanner(System.in);


        do {
            for (int i = 0, playersLength = players.length; i < playersLength; i++) {
                Player player = players[i];
                turn(player, r1, myBoard, printLinesInProgram);
                myGui.updateGUI();
                myScanner2.nextLine();
            }
        } while (!playermoney.playerloser(players));
        Player win = playermoney.playerwin(players);
        System.out.println(win.getName());
        System.out.println(printLinesInProgram[18]);

    }

    public Player[] generateplayers(int amount, String[] printLinesInProgram) {


        Player[] players = new Player[amount];
        for (int i = 0; i < amount; i++) {
            PopupBox myPop = new PopupBox(printLinesInProgram[3] + (i + 1), printLinesInProgram[4]);
            players[i] = new Player(myPop.popup());


        }
        return players;
    }


    public void turn(Player player, Rafflecup r1, Board myboard, String[] printLinesInProgram) {
        System.out.print(printLinesInProgram[5]+":  ");
        System.out.println(player.getName());

        //Tjekker om spilleren skal være i fængsel og frikender spilleren.
        if (player.isIsjailed()) {
            System.out.println(printLinesInProgram[6]);
            player.setIsjailed(false);
            return;
        }
        //Slag
        System.out.flush();
        System.out.print(printLinesInProgram[7]+":  ");
        int sum = r1.sum();
        System.out.flush();
        System.out.print((printLinesInProgram[8])+":  ");
        System.out.flush();
        System.out.println(sum);
        int positionBeforRoll = player.getPosition();

        player.setPosition((player.getPosition() + sum) % (Settings.BOARD_SIZE - 1));

        int positionAfterRoll = player.getPosition();

        if (positionAfterRoll % (Settings.BOARD_SIZE - 1) < positionBeforRoll % (Settings.BOARD_SIZE - 1)) {
            System.out.println(printLinesInProgram[19]);
            player.getAc().newBalance(Settings.GO_SPOT_MONEY);
        }

        //Tjek om spiller går over start

        Field field = myboard.getMyFields()[player.getPosition()];


        System.out.print(printLinesInProgram[9]+":  ");
        System.out.println(field.getName());
        //Standard miste penge på felts værdi

        //Tjekker om de specialle cases Jail free parking go jail property ogg chance.
        switch (field.getfType()) {
            case PROPERTY:
                landonfield(player, field, printLinesInProgram);
                break;
            case FREEPARKING:
            case JAIL:
            case START:
                break;
            case GOJAIL:
                System.out.println(printLinesInProgram[17]);
                player.setPosition(6);
                player.setIsjailed(true);
                break;

            case CHANCE:
                //Når chance metoden kommer vil der skrives noget i denne branch af switchen
                Chancekort chancekorttest = new Chancekort();
                Chancekort.ChancekortTypes tilfældigtChancekort = chancekorttest.DrawRandomChanceCard();
                chancekorttest.chancekorthandling(tilfældigtChancekort, myboard, player, printLinesInProgram);

                break;
        }
    }

    public static void landonfield(Player player, Field field, String[] printLinesInProgram) {
        if (field.getOwner() == null) {
            player.getAc().newBalance(-field.getPrice());
            if (player.getSoldSigns() > 0) {
                field.setOwner(player);
                System.out.println(printLinesInProgram[12]);
            } else
                System.out.println(printLinesInProgram[13]);
        } else {
            if (field.getOwner().equals(player)) {
                System.out.println(printLinesInProgram[14]);
            } else {
                System.out.println(printLinesInProgram[15]);
                System.out.println(field.getOwner().getName());
                System.out.println(printLinesInProgram[16]);
                System.out.println(field.getPrice());
                player.getAc().newBalance(-field.getPrice());
                field.getOwner().getAc().newBalance(field.getPrice());

            }

        }

    }
}
