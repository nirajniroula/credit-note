package com.wolf.nniroula.creditrecorder.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.view.SplashScreen;
import com.wolf.nniroula.creditrecorder.view.ToView;

/**
 * Created by root on 7/19/17.
 */

public class TypeWriter extends TextView {

    private CharSequence mText;
    private int mIndex;
    private long mDelay = 500; //Default 500ms delay


    public TypeWriter(Context context) {
        super(context);
        init();
    }

    public TypeWriter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "night.ttf");
            setTypeface(tf);
        }
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            } else {
                Intent intent = new Intent(SplashScreen.screen, ToView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SplashScreen.getContext().startActivity(intent);
                SplashScreen.screen.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                SplashScreen.screen.finish();
            }
        }
    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;
        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }


    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

}
