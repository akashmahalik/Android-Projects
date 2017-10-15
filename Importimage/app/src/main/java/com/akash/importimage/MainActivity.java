package com.akash.importimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void newCat(View view){

        ImageView image = (ImageView) findViewById(R.id.catimageView);
        image.setImageResource(R.drawable.cat2);

         Log.i("Test","Button Clicked");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
