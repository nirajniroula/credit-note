package com.wolf.nniroula.creditrecorder.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.utils.TypeWriter;

/**
 * Created by root on 7/19/17.
 */

public class SplashScreen extends AppCompatActivity {


    public static SplashScreen screen;
    private static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        screen = this;
        mContext = getBaseContext();

        TypeWriter writer = (TypeWriter) findViewById(R.id.splash_text);

        //Add a character every 150ms
        writer.setCharacterDelay(150);
        writer.animateText(getResources().getString(R.string.app_dev));


    }

    public static Context getContext() {
        return mContext;
    }


}
