package com.akash.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000,1000){
            public void onTick(long millisecondUntilDone){
                //Countdow is counting down(everysecond)
                Log.i("Seconds lef",Long.toString(millisecondUntilDone/1000));
            }

            public void onFinish(){
               // Countoown is
                Log.i("DOne","Countdone done");
            }
        }.start();
        /*
        final Handler handler = new Handler();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                //Insert code to be run every second
                //this here refers to the run method we are in
                handler.postDelayed(this,1000);
                Log.i("Runnable has run!","A second has passed");
            }
        };

        handler.post(run);
        */

    }
}
