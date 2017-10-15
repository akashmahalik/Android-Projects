package com.akash.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    TextView resultTextView;
    TextView pointsTextView;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;
    Button button;
    Button button2;
    Button button3;
    Button button4;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;

    public void playAgain( View view){
        generateQuestion();
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        new CountDownTimer(3000+100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(Long.toString(millisUntilFinished/1000) + "s");

            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                resultTextView.setText("Your Score " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions) );
            }
        }.start();
    }
    public void generateQuestion(){
        answers.clear();
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
        locationCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;

        for (int i=0;i<4;i++){

            if(i==locationCorrectAnswer){
                answers.add(a+b);
            }else{
                incorrectAnswer=rand.nextInt(41);
                while(incorrectAnswer==a+b){
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));

    }
    public void chooseAnswer(View view){
        Log.i("Tag",(String)view.getTag()  );

        if(playAgainButton.getVisibility()==View.INVISIBLE) {
            if (view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))) {
                Log.i("COrrect", "correct");
                score++;
                resultTextView.setText("Correct!");

            } else {
                resultTextView.setText("Wrong!");
            }
            numberOfQuestions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            generateQuestion();
        }else{
            Toast.makeText(getApplicationContext(),"Click Play Again",Toast.LENGTH_SHORT).show();
        }
    }

    public void startView(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton)); //timer and all to start after pressing GO\
        // and not just on opening of app


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        gameRelativeLayout = (RelativeLayout)findViewById(R.id.gameRelativeLayout);
         button = (Button)findViewById(R.id.button);
         button2 = (Button)findViewById(R.id.button2);
         button3 = (Button)findViewById(R.id.button3);
         button4 = (Button)findViewById(R.id.button4);





    }
}
