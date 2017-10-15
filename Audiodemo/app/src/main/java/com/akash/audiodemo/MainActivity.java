package com.akash.audiodemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mplayer;
    AudioManager audioManager;
    public void playAudio(View view){


        mplayer.start();

    }
    public void pauseAudio(View view){

        mplayer.pause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mplayer = MediaPlayer.create(this,R.raw.linkin);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);



        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("SeekBar value",Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);

            }


        });

        final SeekBar scrubControl = (SeekBar) findViewById(R.id.scrubBar);
        scrubControl.setMax(mplayer.getDuration());
        final  int duration = mplayer.getDuration();
        final int update = duration/50;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!(update * scrubControl.getProgress() >= duration)) {
                    int p = scrubControl.getProgress();
                    p += 1;
                    scrubControl.setProgress(p);
                }
            scrubControl.setProgress(mplayer.getCurrentPosition());
            }
        },0,update);
        scrubControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mplayer.seekTo(progress);
                Log.i("ScrubBar value",Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mplayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mplayer.start();
            }
        });

    }
}
