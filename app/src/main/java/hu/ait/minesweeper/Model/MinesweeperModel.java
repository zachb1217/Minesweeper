package hu.ait.minesweeper.Model;

import java.util.Random;

import hu.ait.minesweeper.View.MinesweeperView;


/**
 * Created by zberkowitz on 3/1/17.
 */

public class MinesweeperModel {

    private static MinesweeperModel instance = null;
    private boolean flagOn = false;
    private int mineNumber = 3;
    private int minesRemaining = mineNumber;
    private boolean gameLost = false;

    private MinesweeperModel() {

        for (int i = 0; i < mineNumber; i++) {
            int randX = randInt();
            int randY = randInt();
            setField(randX, randY, MINE);
            incrementRadius(randX, randY);
        }


    }

    public void incrementRadius(int x, int y){
        incrementField(x + 1, y + 1);
        incrementField(x + 1, y);
        incrementField(x + 1, y - 1);
        incrementField(x - 1, y);
        incrementField(x - 1, y + 1);
        incrementField(x - 1, y - 1);
        incrementField(x, y + 1);
        incrementField(x, y - 1);
    }



    public static int randInt() {

        Random rand = new Random();


        int randomNum = Math.abs(rand.nextInt()) % 5;

        return randomNum;
    }




    public static final short EMPTY = 0;
    public static final short ONE = 1;
    public static final short TWO = 2;
    public static final short THREE = 3;
    public static final short MINE = 4;

    private static short[][] board = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };

    public static final short HIDDEN = 0;
    public static final short REVEALED = 1;
    public static final short FLAGGED = 2;

    private static short[][] reveal = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
    };

    public void resetGame(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5 ; j++) {
                reveal[i][j] = HIDDEN;
                board[i][j] = EMPTY;
            }
        }

        instance = new MinesweeperModel();
        gameLost = false;
        minesRemaining = mineNumber;
    }

    public static MinesweeperModel getInstance(){
        if(instance == null){
            instance = new MinesweeperModel();
        }
        return instance;
    }

    public short getField(int i, int j){
        return board[j][i];
    }

    public void setField(int i, int j, short value){ board[j][i] = value; }

    public void incrementField(int i, int j){


        try {
            int field = (int)getField(i, j);

            if(field != MINE)
                setField(i, j, (short)(field + 1));
        }
        catch(Exception e){}

    }

    public short getReveal(int i, int j){
        return reveal[j][i];
    }

    public void revealSquare(int i, int j){
       try {
           if (board[j][i] == EMPTY && reveal[j][i] == HIDDEN) {

               reveal[j][i] = REVEALED;

               revealSquare(i + 1, j);
               revealSquare(i - 1, j);


               for (int k = 0; k < 3; k++) {
                   revealSquare(i - 1 + k, j + 1);
                   revealSquare(i - 1 + k, j - 1);
               }



           } else if (reveal[j][i] != FLAGGED) {
               reveal[j][i] = REVEALED;
           }

           if (board[j][i] == MINE) {
               gameLost = true;
           }
       }
       catch(Exception e){}
    }

    public void flagSquare(int i, int j){
        if(reveal[j][i] != REVEALED) {
            reveal[j][i] = FLAGGED;
        }

        if (board[j][i] != MINE){
            gameLost = true;
        }
        else {
            minesRemaining--;
        }
    }

    public boolean checkWin(){
        return (minesRemaining == 0);
    }

    public boolean checkLoss(){
        return gameLost;
    }

    public void setFlagOn(boolean b) {
        flagOn = b;
    }

    public boolean getFlagOn(){
        return flagOn;
    }
}
