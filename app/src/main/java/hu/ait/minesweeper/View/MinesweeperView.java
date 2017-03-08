package hu.ait.minesweeper.View;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.minesweeper.MainActivity;
import hu.ait.minesweeper.Model.MinesweeperModel;
import hu.ait.minesweeper.R;

/**
 * Created by zberkowitz on 3/1/17.
 */

public class MinesweeperView extends View {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintOne;
    private Paint paintTwo;
    private Paint paintThree;
    private Paint clearedField;
    private Paint bombField;
    private Bitmap mineIcon;
    private Bitmap flagIcon;

    private int BOARD_LENGTH;

    private MainActivity context = (MainActivity)getContext();




    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPaints();

        BOARD_LENGTH = 5;

        mineIcon  = BitmapFactory.decodeResource(getResources(), R.drawable.mine);

        flagIcon = BitmapFactory.decodeResource(getResources(), R.drawable.flag);


    }

    private void setPaints() {
        paintBg = new Paint();
        paintBg.setColor(Color.rgb(182,182,182));
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStrokeWidth(10);
        paintLine.setStyle(Paint.Style.STROKE);

        paintOne = new Paint();
        paintOne.setColor(Color.BLUE);
        paintOne.setStyle(Paint.Style.FILL);
        paintOne.setTextSize(250);

        paintTwo = new Paint();
        paintTwo.setColor(Color.GREEN);
        paintTwo.setStyle(Paint.Style.FILL);
        paintTwo.setTextSize(250);

        paintThree = new Paint();
        paintThree.setColor(Color.RED);
        paintThree.setStyle(Paint.Style.FILL);
        paintThree.setTextSize(250);

        clearedField = new Paint();
        clearedField.setColor(Color.GREEN);
        clearedField.setStyle(Paint.Style.FILL);

        bombField = new Paint();
        bombField.setColor(Color.RED);
        bombField.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        drawBoard(canvas);

        drawElements(canvas);



    }

    private void drawBoard(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        canvas.drawLine(getWidth()/5, 0, getWidth()/5, getHeight(), paintLine);
        canvas.drawLine(2 * getWidth()/5, 0, 2 * getWidth()/5, getHeight(), paintLine);
        canvas.drawLine(3 * getWidth()/5, 0, 3 * getWidth()/5, getHeight(), paintLine);
        canvas.drawLine(4 * getWidth()/5, 0, 4 * getWidth()/5, getHeight(), paintLine);

        canvas.drawLine(0, getHeight()/5, getWidth(), getHeight()/5, paintLine);
        canvas.drawLine(0, 2 * getHeight()/5, getWidth(), 2 * getHeight()/5, paintLine);
        canvas.drawLine(0, 3 * getHeight()/5, getWidth(), 3 * getHeight()/5, paintLine);
        canvas.drawLine(0, 4 * getHeight()/5, getWidth(), 4 * getHeight()/5, paintLine);
    }

    private int offsetXText;
    private int offsetYText;
    private int offsetXMine;
    private int offsetYMine;

    private void drawElements(Canvas canvas) {
        offsetXText = getWidth() / 20;
        offsetYText = getHeight() / 30;
        offsetXMine = getWidth() / 60;
        offsetYMine = getHeight() / 60;


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int field = MinesweeperModel.getInstance().getField(i, j);
                if (MinesweeperModel.getInstance().getReveal(i, j) != MinesweeperModel.HIDDEN) {
                    if (MinesweeperModel.getInstance().getReveal(i, j) ==
                            MinesweeperModel.FLAGGED ) {
                        canvas.drawBitmap(flagIcon,
                                new Rect(flagIcon.getWidth(), flagIcon.getHeight(), 0, 0),
                                new Rect(i * getWidth() / 5 + offsetXMine,
                                        (j + 1) * getHeight() / 5 - offsetYMine,
                                        (i + 1) * getWidth() / 5 - offsetXMine,
                                        j * getHeight() / 5 + offsetYMine), null);
                    }
                    else if (field == MinesweeperModel.EMPTY) {
                        canvas.drawRect(i * getWidth() / 5 , (j + 1) * getHeight() / 5,
                                (i + 1) * getWidth() / 5, j * getHeight() / 5 , clearedField);
                        canvas.drawRect(i * getWidth() / 5 , (j + 1) * getHeight() / 5,
                                (i + 1) * getWidth() / 5, j * getHeight() / 5 , paintLine);
                    } else if (field == MinesweeperModel.ONE) {
                        canvas.drawText("1", i * getWidth() / 5 + offsetXText,
                                (j + 1) * getHeight() / 5 - offsetYText, paintOne);
                    } else if (field == MinesweeperModel.TWO) {
                        canvas.drawText("2", i * getWidth() / 5 + offsetXText,
                                (j + 1) * getHeight() / 5 - offsetYText, paintTwo);
                    } else if (field == MinesweeperModel.THREE) {
                        canvas.drawText("3", i * getWidth() / 5 + offsetXText,
                                (j + 1) * getHeight() / 5 - offsetYText, paintThree);
                    } else if (field == MinesweeperModel.MINE) {
                        canvas.drawRect(i * getWidth() / 5 , (j + 1) * getHeight() / 5,
                                (i + 1) * getWidth() / 5, j * getHeight() / 5 , bombField);
                        canvas.drawRect(i * getWidth() / 5 , (j + 1) * getHeight() / 5,
                                (i + 1) * getWidth() / 5, j * getHeight() / 5 , paintLine);
                        canvas.drawBitmap(mineIcon,
                                new Rect(0, 0, mineIcon.getWidth(), mineIcon.getHeight()),
                                new Rect(i * getWidth() / 5 + offsetXMine,
                                        (j + 1) * getHeight() / 5 - offsetYMine,
                                        (i + 1) * getWidth() / 5 - offsetXMine,
                                        j * getHeight() / 5 + offsetYMine), null);
                    }




                }

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        MinesweeperModel gameInstance = MinesweeperModel.getInstance();

        if( event.getAction() == MotionEvent.ACTION_DOWN){



            int xT = (int) event.getX() / (getWidth() / BOARD_LENGTH);

            int yT = (int) event.getY() / (getHeight() / BOARD_LENGTH);

            if(gameInstance.checkWin() || gameInstance.checkLoss()){

            }
            else if (gameInstance.getFlagOn() == true) {
                gameInstance.flagSquare(xT, yT);
            } else {
                gameInstance.revealSquare(xT, yT);
            }



        }
        else if(event.getAction() == MotionEvent.ACTION_UP){

            if(gameInstance.checkWin()){
                context.setMessage("YOU WIN");

            }
            else if(gameInstance.checkLoss()){
                context.setMessage("YOU LOSE");
            }

        }



        invalidate();

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void resetGame(){

       MinesweeperModel.getInstance().resetGame();
        invalidate();
    }
}
