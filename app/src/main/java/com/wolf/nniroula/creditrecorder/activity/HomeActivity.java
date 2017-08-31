package com.wolf.nniroula.creditrecorder.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.johnpersano.supertoasts.SuperToast;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.adapter.HomeFragmentAdapter;
import com.wolf.nniroula.creditrecorder.dialogs.SettingDialog;
import com.wolf.nniroula.creditrecorder.ui.CustomSliderView;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;

import java.util.HashMap;

/**
 * Created by Niraj Niroula on 8/24/17.
 */

public class HomeActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    private MaterialRippleLayout custom;
    private MaterialRippleLayout tags;
    private MaterialRippleLayout months;
    private MaterialRippleLayout list;
    private MaterialRippleLayout settings;
    private MaterialRippleLayout help;
    private MaterialRippleLayout rate;
    private MaterialRippleLayout about;


    private Context mContext;

    private HomeFragmentAdapter adapter = null;


    private TextView userName;
    private TextView userEmail;


    private SliderLayout mDemoSlider;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.home_activity);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        mViewPager.getPagerTitleStrip().setTypeface(CreditUtil.getInstance().typefaceLatoLight, Typeface.NORMAL);
        mViewPager.getPagerTitleStrip().setAllCaps(false);
        mViewPager.getPagerTitleStrip().setUnderlineColor(Color.parseColor("#00000000"));
        mViewPager.getPagerTitleStrip().setIndicatorColor(Color.parseColor("#00000000"));
        mViewPager.getPagerTitleStrip().setUnderlineHeight(0);
        mViewPager.getPagerTitleStrip().setIndicatorHeight(0);

        setFonts();

        View view = mViewPager.getRootView();
        TextView title = (TextView) view.findViewById(R.id.text_white);
        title.setTypeface(CreditUtil.typefaceLatoLight);
        title.setText("Credit Note");


        setTitle("");
        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        custom = (MaterialRippleLayout) mDrawer.findViewById(R.id.custom_layout);
        tags = (MaterialRippleLayout) mDrawer.findViewById(R.id.tag_layout);
        months = (MaterialRippleLayout) mDrawer.findViewById(R.id.month_layout);
        list = (MaterialRippleLayout) mDrawer.findViewById(R.id.list_layout);

        rate = (MaterialRippleLayout) mDrawer.findViewById(R.id.sync_layout);

        settings = (MaterialRippleLayout) mDrawer.findViewById(R.id.settings_layout);
        help = (MaterialRippleLayout) mDrawer.findViewById(R.id.help_layout);
        about = (MaterialRippleLayout) mDrawer.findViewById(R.id.about_layout);

        //new RecordManager(CreditApplication.getAppContext()).initRecords();
        setPage(0);

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(0);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 700);
            }
        });


        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(1);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 700);
            }
        });


        months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(2);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 700);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingDialog();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 700);
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SettingActivity.class));
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, HelpActivity.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AboutActivity.class));
            }
        });


        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);


        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, Integer> urls = CreditUtil.GetDrawerTopImages();

        for (String name : urls.keySet()) {
            CustomSliderView customSliderView = new CustomSliderView(this);
            // initialize a SliderLayout
            customSliderView
                    .image(urls.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mDemoSlider.addSlider(customSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                return HeaderDesign.fromColorAndDrawable(
                        ContextCompat.getColor(CreditApplication.getAppContext(), R.color.my_blue),
                        ContextCompat.getDrawable(
                                CreditApplication.getAppContext(), R.drawable.material_design_3));
            }
        });


    }

    private void showSettingDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingDialog sd = new SettingDialog();
        sd.setInfo(this);
        sd.show(fm, "dialogSettings");
    }

    private void setPage(int pos) {
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(), pos);
        mViewPager.getViewPager().setAdapter(adapter);
//        mViewPager.getViewPager().setPageTransformer(false, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//
//                page.setRotationY(position * -30); // animation style... change as you want..
//            }
//        });
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.getPagerTitleStrip().invalidate();
        mViewPager.getViewPager().setOffscreenPageLimit(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDemoSlider.startAutoCycle();
    }


    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MaterialViewPagerHelper.unregister(this);
        SuperToast.cancelAllSuperToasts();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }

    private void setFonts() {
        ((TextView) findViewById(R.id.custom_text)).setTypeface(CreditUtil.getTypeface());
        ((TextView) findViewById(R.id.tag_text)).setTypeface(CreditUtil.getTypeface());
        ((TextView) findViewById(R.id.month_text)).setTypeface(CreditUtil.getTypeface());
        ((TextView) findViewById(R.id.list_text)).setTypeface(CreditUtil.getTypeface());
        ((TextView) findViewById(R.id.sync_text)).setTypeface(CreditUtil.getTypeface());
        ((TextView) findViewById(R.id.settings_text)).setTypeface(CreditUtil.getTypeface());
        ((TextView) findViewById(R.id.help_text)).setTypeface(CreditUtil.getTypeface());
        ((TextView) findViewById(R.id.about_text)).setTypeface(CreditUtil.getTypeface());
    }


    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers();
            return;
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            SuperToast.cancelAllSuperToasts();
            SuperToast superToast = new SuperToast(CreditApplication.getAppContext());

            superToast.setAnimations(CreditUtil.TOAST_ANIMATION);
            superToast.setDuration(SuperToast.Duration.LONG);
            superToast.setTextColor(Color.parseColor("#ffffff"));
            superToast.setTextSize(SuperToast.TextSize.SMALL);
            superToast.setText("Press again to exit");
            superToast.setBackground(SuperToast.Background.BLUE);
            superToast.show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

}
