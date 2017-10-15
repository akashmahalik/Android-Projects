package com.akash.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void fade(View view){
        ImageView luffy = (ImageView) findViewById(R.id.luffy);

        luffy.animate().translationXBy(2000f).translationYBy(2000f).rotationBy(3600f).setDuration(2000);

//        luffy.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);
//        luffy.animate().rotation(1000f).setDuration(2000);
//        luffy.animate().translationXBy(2000f).setDuration(2000);
//      luffy.animate().translationYBy(2000f).setDuration(2000);
//        ImageView hancock = (ImageView) findViewById(R.id.hancock);
//        luffy.animate().alpha(0f).setDuration(2000); //0.5f
//        hancock.animate().alpha(0f).setDuration(0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView luffy = (ImageView) findViewById(R.id.luffy);
        luffy.setTranslationX(-2000f);
        luffy.setTranslationY(-2000f);


    }
}
