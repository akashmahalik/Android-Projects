package com.akash.basicphrases;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void buttonTapped(View view){

        int id = view.getId();
        String ourID = "";
        ourID = view.getResources().getResourceEntryName(id);
        int resourceId = getResources().getIdentifier(ourID,"raw","com.akash.basicphrases");
        MediaPlayer mplayer = MediaPlayer.create(this,resourceId);
        mplayer.start();
        Log.i("Button tapped",ourID);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
