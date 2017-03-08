package hu.ait.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hu.ait.minesweeper.Model.MinesweeperModel;
import hu.ait.minesweeper.View.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private Button flagBtn;
    private Button checkBtn;
    private Button resetBtn;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagBtn = (Button) findViewById(R.id.flagBtn);
        checkBtn = (Button) findViewById(R.id.checkBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        textView = (TextView) findViewById(R.id.tvTitle);

        flagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinesweeperModel.getInstance().setFlagOn(true);
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinesweeperModel.getInstance().setFlagOn(false);
            }
        });

        final MinesweeperView minesweeperView = (MinesweeperView) findViewById(R.id.mineView);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minesweeperView.resetGame();
                setMessage("TOUCH TO PLAY");
            }
        });





    }

    public void setMessage(String text){

        textView.setText(text);

    }

}
