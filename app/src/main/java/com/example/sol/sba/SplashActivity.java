package com.example.sol.sba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = findViewById(R.id.splash_text);
        iv = findViewById(R.id.splash_image);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);

        tv.startAnimation(myanim);
        iv.startAnimation(myanim);

        final Intent i = new Intent(this,MainActivity.class);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);

                }catch(Exception e){

                }finally {
                    startActivity(i);
                    finish();
                }}
        };
        timer.start();
    }
}
