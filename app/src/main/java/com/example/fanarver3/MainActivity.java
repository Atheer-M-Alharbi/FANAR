package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    // speed to which the screen will be moving in sec
    private static int MOVING_SCREEN = 3000;
    //variables
    Animation topAnim , bottomAnim;
    ImageView image;
    TextView text1 , text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);




        //Animation of the facade screen load it from anim folder where we defind it
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //find view's
        image = findViewById(R.id.imageView3);
        text1 = findViewById(R.id.textView4);
        text2 = findViewById(R.id.textView5);

        // set animation for logo images & 2 test
        image.setAnimation(topAnim);
        text1.setAnimation(bottomAnim);
        text2.setAnimation(bottomAnim);

        //create/move to the next screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // call the next activity
                Intent  intent = new Intent(MainActivity.this, LogIn.class );
                startActivity(intent);

            }
            //speed
        },MOVING_SCREEN);

    }
}