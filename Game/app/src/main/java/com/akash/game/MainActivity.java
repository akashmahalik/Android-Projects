package com.akash.game;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //yellow = 0 , red = 1;
    int activePlayer = 0;
    boolean gameIsActive = true;
    // 2 mean unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winninPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public void dropIn(View view){

        ImageView counter = (ImageView) view;

        System.out.println(counter.getTag().toString());

        int taggedCounter = Integer.parseInt((counter.getTag().toString()));
        if(gameIsActive && gameState[taggedCounter]==2) {

            gameState[taggedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for(int[] winningPosition : winninPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]]==gameState[winningPosition[2]] && gameState[winningPosition[0]]!=2){

                    String winner = "Red";
                    if(gameState[winningPosition[0]]==0) winner = "yellow";
                    //System.out.println(gameState[winningPosition[0]]);
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + "has won!");
                 //   GridLayout gridlayout = (GridLayout) findViewById(R.id.grid);
                   // gridlayout.setVisibility(View.INVISIBLE);
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                    gameIsActive = false;


                } else{
                    boolean gameisOver = true;
                    for(int counterstate:gameState){
                        if(counterstate==2) gameisOver = false;
                    }
                    if(gameisOver){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw");
                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);

                    }
                }

            }
        }

    }
    public void playAgain(View view){
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
         activePlayer = 0;

        for(int i=0;i<gameState.length;i++)
            gameState[i]=2;

        GridLayout grid = (GridLayout) findViewById(R.id.grid);

        for(int i=0;i<grid.getChildCount(); i++)
        {
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }
        gameIsActive = true;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
