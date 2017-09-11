package com.wolf.nniroula.creditrecorder.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.sacot41.scviewpager.DotsView;
import com.dev.sacot41.scviewpager.SCPositionAnimation;
import com.dev.sacot41.scviewpager.SCViewAnimation;
import com.dev.sacot41.scviewpager.SCViewAnimationUtil;
import com.dev.sacot41.scviewpager.SCViewPager;
import com.dev.sacot41.scviewpager.SCViewPagerAdapter;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.model.SettingManager;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;


public class TutorialActivity extends AppCompatActivity {

    private static final int PAGES = 4;

    private SCViewPager mViewPager;
    private SCViewPagerAdapter mPageAdapter;
    private DotsView mDotsView;
    private ImageView ready;


    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mContext = this;

        //title = (TextView) findViewById(R.id.title);
        CreditUtil.init(mContext);
        //title.setTypeface(CreditUtil.typefaceLatoLight);
        //title.setText(mContext.getResources().getString(R.string.app_name));

        mViewPager = (SCViewPager) findViewById(R.id.viewpager_tuts);
        mDotsView = (DotsView) findViewById(R.id.dotsview_main);
        mDotsView.setDotRessource(R.drawable.dot_active, R.drawable.dot_inactive);
        mDotsView.setNumberOfPage(PAGES);

        mPageAdapter = new SCViewPagerAdapter(getSupportFragmentManager());
        mPageAdapter.setNumberOfPage(PAGES);
        mPageAdapter.setFragmentBackgroundColor(R.color.colorPrimaryDark);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 3) {
                    SettingManager.getInstance().setFirstTime(false);
                    ready.setVisibility(View.VISIBLE);

                } else {
                    ready.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mDotsView.selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


        final Point size = SCViewAnimationUtil.getDisplaySize(this);

        int iconOffsetX = CreditUtil.getInstance().changeDpToPx(28);
        int iconOffsetY = CreditUtil.getInstance().changeDpToPx(28);

        ready = (ImageView) findViewById(R.id.ready);
        ready.setX(size.x * 3 / 4 - iconOffsetX);
        ready.setY(size.y * 5 / 7 - iconOffsetY);

        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity context = (Activity) mContext;
                context.finish();
            }
        });


        ImageView credit1 = (ImageView) findViewById(R.id.credit_1);
        credit1.getLayoutParams().width = size.x / 2;
        credit1.getLayoutParams().height = size.x / 2 * 653 / 320;
        SCViewAnimation sc6 = new SCViewAnimation(findViewById(R.id.credit_1));
        sc6.startToPosition(size.x / 2 - size.x, size.y / 20);
        sc6.addPageAnimation(new SCPositionAnimation(this, 0, size.x, 0));
        sc6.addPageAnimation(new SCPositionAnimation(this, 1, size.x, 0));
        mViewPager.addAnimation(sc6);

        ImageView credit2 = (ImageView) findViewById(R.id.credit_2);
        credit2.getLayoutParams().width = size.x / 2;
        credit2.getLayoutParams().height = size.x / 2 * 653 / 320;
        SCViewAnimation sc7 = new SCViewAnimation(findViewById(R.id.credit_2));
        sc7.startToPosition(size.x / 2 + size.x / 2, size.y / 2 - size.y / 20);
        sc7.addPageAnimation(new SCPositionAnimation(this, 0, -size.x, 0));
        sc7.addPageAnimation(new SCPositionAnimation(this, 1, -size.x, 0));
        mViewPager.addAnimation(sc7);

        ((TextView) findViewById(R.id.text_1)).setTypeface(CreditUtil.getInstance().typefaceLatoLight);
        SCViewAnimation sc8 = new SCViewAnimation(findViewById(R.id.text_1));
        sc8.startToPosition(size.x, null);
        sc8.addPageAnimation(new SCPositionAnimation(this, 0, -size.x, 0));
        sc8.addPageAnimation(new SCPositionAnimation(this, 1, -size.x, 0));
        mViewPager.addAnimation(sc8);

        ImageView fifth = (ImageView) findViewById(R.id.cloud);
        fifth.getLayoutParams().width = size.x / 2;
        fifth.getLayoutParams().height = size.x / 2 * 653 / 320;

        SCViewAnimation sc9 = new SCViewAnimation(findViewById(R.id.cloud));
        sc9.startToPosition(size.x + size.x / 4, size.y / 4);
        sc9.addPageAnimation(new SCPositionAnimation(this, 1, -size.x, 0));
        sc9.addPageAnimation(new SCPositionAnimation(this, 2, 0, size.y));
        mViewPager.addAnimation(sc9);


        ((TextView) findViewById(R.id.text_2)).setTypeface(CreditUtil.getInstance().typefaceLatoLight);
        SCViewAnimation sc11 = new SCViewAnimation(findViewById(R.id.text_2));
        sc11.startToPosition(size.x, null);
        sc11.addPageAnimation(new SCPositionAnimation(this, 1, -size.x, 0));
        sc11.addPageAnimation(new SCPositionAnimation(this, 2, -size.x, 0));
        mViewPager.addAnimation(sc11);

        ImageView remind1 = (ImageView) findViewById(R.id.remind_1);
        remind1.getLayoutParams().width = size.x / 2;
        remind1.getLayoutParams().height = size.x / 2 * 653 / 320;
        SCViewAnimation sc12 = new SCViewAnimation(findViewById(R.id.remind_1));
        sc12.startToPosition(size.x / 2 - size.x, size.y / 20);
        sc12.addPageAnimation(new SCPositionAnimation(this, 2, size.x, 0));
        sc12.addPageAnimation(new SCPositionAnimation(this, 3, size.x, 0));
        mViewPager.addAnimation(sc12);

        Log.e("Test", "" + (size.x / 2 - size.x));

        ImageView remind2 = (ImageView) findViewById(R.id.remind_2);
        remind2.getLayoutParams().width = size.x / 2;
        remind2.getLayoutParams().height = size.x / 2 * 653 / 320;
        SCViewAnimation sc13 = new SCViewAnimation(findViewById(R.id.remind_2));
        sc13.startToPosition(size.x / 2 + size.x / 2, size.y / 2 - size.y / 20);
        sc13.addPageAnimation(new SCPositionAnimation(this, 2, -size.x, 0));
        sc13.addPageAnimation(new SCPositionAnimation(this, 3, -size.x, 0));
        mViewPager.addAnimation(sc13);

        Log.e("Test0", "" + (size.x / 2 + size.x / 2));


//        SCViewAnimation go = new SCViewAnimation(findViewById(R.id.ready));
//        go.startToPosition(size.x * 3 / 4 - iconOffsetX, size.y * 5 / 7 - iconOffsetY);
//        mViewPager.addAnimation(go);

        Log.e("Test1", "" + (size.x * 3 / 4 - iconOffsetX));

        ((TextView) findViewById(R.id.text_3)).setTypeface(CreditUtil.getInstance().typefaceLatoLight);
        SCViewAnimation sc14 = new SCViewAnimation(findViewById(R.id.text_3));
        sc14.startToPosition(size.x, null);
        sc14.addPageAnimation(new SCPositionAnimation(this, 2, -size.x, 0));
        sc14.addPageAnimation(new SCPositionAnimation(this, 3, -size.x, 0));
        mViewPager.addAnimation(sc14);

        SCViewAnimation sc0 = new SCViewAnimation(findViewById(R.id.icon_4));
        sc0.startToPosition(size.x / 4 - iconOffsetX, size.y * 2 / 7 - iconOffsetY);
        sc0.addPageAnimation(new SCPositionAnimation(this, 0, 0, size.y));
        mViewPager.addAnimation(sc0);

        SCViewAnimation sc1 = new SCViewAnimation(findViewById(R.id.icon_11));
        sc1.startToPosition(size.x * 3 / 4 - iconOffsetX, size.y * 3 / 7 - iconOffsetY);
        sc1.addPageAnimation(new SCPositionAnimation(this, 0, 0, -size.y));
        mViewPager.addAnimation(sc1);

        SCViewAnimation sc2 = new SCViewAnimation(findViewById(R.id.icon_12));
        sc2.startToPosition(size.x / 4 - iconOffsetX, size.y * 4 / 7 - iconOffsetY);
        sc2.addPageAnimation(new SCPositionAnimation(this, 0, size.x, 0));
        mViewPager.addAnimation(sc2);

        SCViewAnimation sc3 = new SCViewAnimation(findViewById(R.id.icon_19));
        sc3.startToPosition(size.x * 3 / 4 - iconOffsetX, size.y * 5 / 7 - iconOffsetY);
        sc3.addPageAnimation(new SCPositionAnimation(this, 0, -size.x, 0));
        mViewPager.addAnimation(sc3);

        ((TextView) findViewById(R.id.text_0)).setTypeface(CreditUtil.getInstance().typefaceLatoLight);
        SCViewAnimation sc4 = new SCViewAnimation(findViewById(R.id.text_0));
        sc4.addPageAnimation(new SCPositionAnimation(this, 0, -size.x, 0));
        mViewPager.addAnimation(sc4);


//        toolbarLayout = findViewById(R.id.toolbar_layout);
//        SCViewAnimation toolbarLayoutAnimation = new SCViewAnimation(toolbarLayout);
//        toolbarLayoutAnimation.startToPosition(null, -size.y / 2);
//        toolbarLayoutAnimation.addPageAnimation(new SCPositionAnimation(this, 3, 0, size.y / 2));
//        mViewPager.addAnimation(toolbarLayoutAnimation);


        View background = findViewById(R.id.background);
        SCViewAnimation backgroundAnimation = new SCViewAnimation(background);
        backgroundAnimation.startToPosition(null, -size.y - 100);
        backgroundAnimation.addPageAnimation(new SCPositionAnimation(this, 3, 0, size.y + 100));
        mViewPager.addAnimation(backgroundAnimation);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
