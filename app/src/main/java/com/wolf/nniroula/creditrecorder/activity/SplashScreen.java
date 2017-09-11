package com.wolf.nniroula.creditrecorder.activity;


import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.model.SettingManager;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;

import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;


/**
 * Created by root on 7/19/17.
 */

public class SplashScreen extends AppCompatActivity {

    private Context mContext;

    private RevealFrameLayout reveal;
    private LinearLayout ly;

    private ImageView image;
    private TextView appName;
    private TextView loadingText;
    private TextView versionText;
    private TextView devText;


    private boolean loadDataCompleted = false;
    private boolean showAnimationCompleted = false;
    private boolean activityStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mContext = this;

        image = (ImageView) findViewById(R.id.image);
        YoYo.with(Techniques.Pulse).delay(200).playOn(image);

        appName = (TextView) findViewById(R.id.app_name);
        appName.setTypeface(CreditUtil.getInstance().typefaceLatoLight);
        loadingText = (TextView) findViewById(R.id.loading_text);
        loadingText.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        versionText = (TextView) findViewById(R.id.version_text);
        versionText.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        devText = (TextView) findViewById(R.id.developer_text);
        devText.setTypeface(CreditUtil.getInstance().typefaceLatoLight);


        reveal = (RevealFrameLayout) findViewById(R.id.reveal);
        ly = (LinearLayout) findViewById(R.id.ly);

        new InitData().execute();
    }

    private void startCircularReveal() {
        // get the center for the clipping circle
        int[] location = new int[2];
        image.getLocationOnScreen(location);
        int cx = location[0] + CreditUtil.changeDpToPx(24);
        int cy = location[1] + CreditUtil.changeDpToPx(24);

        // get the final radius for the clipping circle
        int dx = Math.max(cx, ly.getWidth() - cx);
        int dy = Math.max(cy, ly.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        Animator animator = ViewAnimationUtils.createCircularReveal(ly, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(3000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                showAnimationCompleted = true;
                if (loadDataCompleted && showAnimationCompleted && !activityStarted) {
                    activityStarted = true;
                    startActivity(new Intent(mContext, HomeActivity.class));
                    finish();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        hasAnimationStarted = true;
    }

    private boolean hasAnimationStarted;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !hasAnimationStarted) {
            startCircularReveal();
        }
    }

    public class InitData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Looper.prepare();
            SettingManager.getInstance();
            RecordManager.getInstance(CreditApplication.getAppContext());
            CreditUtil.init(CreditApplication.getAppContext());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("CoCoin", "Loading Data completed");
            versionText.setText(CreditUtil.getCurrentVersion());
            loadingText.setText(mContext.getResources().getString(R.string.loading));
            loadDataCompleted = true;
            if (loadDataCompleted && showAnimationCompleted && !activityStarted) {
                activityStarted = true;
                startActivity(new Intent(mContext, HomeActivity.class));
                finish();
            }
        }
    }

}
