package com.wolf.nniroula.creditrecorder.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
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
import com.wolf.nniroula.creditrecorder.controller.HomeController;
import com.wolf.nniroula.creditrecorder.dialogs.SettingDialog;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.model.SettingManager;
import com.wolf.nniroula.creditrecorder.ui.CustomSliderView;
import com.wolf.nniroula.creditrecorder.ui.interfaces.AddToExistedListener;
import com.wolf.nniroula.creditrecorder.ui.interfaces.MyDialogListener;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;

import java.util.HashMap;

/**
 * Created by Niraj Niroula on 8/24/17.
 */

public class HomeActivity extends AppCompatActivity implements AddToExistedListener {

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    private MaterialRippleLayout home;
    private MaterialRippleLayout listView;
    private MaterialRippleLayout addNew;
    private MaterialRippleLayout itemSetting;
    private MaterialRippleLayout settings;
    private MaterialRippleLayout help;
    private MaterialRippleLayout rate;
    private MaterialRippleLayout about;
    static String tipText = "";
    private HomeController homeController;


    private Context mContext;

    private HomeFragmentAdapter adapter = null;


    private TextView tips;


    private SliderLayout mDemoSlider;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.home_activity);
        homeController = new HomeController(this);

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
        title.setText(SettingManager.getInstance().getNotesName());


        setTitle("");
        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        home = (MaterialRippleLayout) mDrawer.findViewById(R.id.custom_layout);
        rate = (MaterialRippleLayout) mDrawer.findViewById(R.id.rate_layout);
        tips = (TextView) mDrawer.findViewById(R.id.credit_tip);
        tips.setTypeface(CreditUtil.typefaceLatoLight);


        listView = (MaterialRippleLayout) mDrawer.findViewById(R.id.tag_layout);
        addNew = (MaterialRippleLayout) mDrawer.findViewById(R.id.month_layout);
        itemSetting = (MaterialRippleLayout) mDrawer.findViewById(R.id.list_layout);


        settings = (MaterialRippleLayout) mDrawer.findViewById(R.id.settings_layout);
        help = (MaterialRippleLayout) mDrawer.findViewById(R.id.help_layout);
        about = (MaterialRippleLayout) mDrawer.findViewById(R.id.about_layout);

        //new RecordManager(CreditApplication.getAppContext()).initRecords();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(0);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 200);
            }
        });


        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(1);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 200);
            }
        });


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(2);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 200);
            }
        });

        itemSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingDialog();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 200);
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

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openRateAndReview();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawer.closeDrawers();
                    }
                }, 200);
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

//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            int i = 0;
//
//            public void run() {
//                i++;
//                if (i > 4) {
//                    i = 0;
//                }
//                tips.setText(CreditUtil.DRAWER_TOP_TIPS[i]);
//                handler.postDelayed(this, 10000); //added this line
//            }
//        }, 10000);


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
                        ContextCompat.getColor(CreditApplication.getAppContext(), R.color.colorPrimary),
                        ContextCompat.getDrawable(
                                CreditApplication.getAppContext(), R.drawable.material_design_3));
            }
        });

        if (SettingManager.getInstance().getFirstTime()) {
            Intent intent = new Intent(CreditApplication.getAppContext(), TutorialActivity.class);
            startActivity(intent);
        }

    }

    private void openRateAndReview() {
        new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .typeface(CreditUtil.getTypeface(), CreditUtil.getTypeface())
                .title(R.string.rate_dialog_title)
                .content(R.string.rate_dialog_content)
                .iconRes(R.drawable.ic_grade_black_24dp)
                .negativeText(R.string.rate_dialog_positive)
                .positiveText(R.string.rate_dialog_negative)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Phantom+Projects")));
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();

                    }
                })
                .show();
    }

    private void showSettingDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingDialog sd = new SettingDialog();
        sd.setInfo(this);
        sd.show(fm, "dialogSettings");
        sd.DismissListener(onDialogCloseListener);
    }

    protected void setPage(int pos) {
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(), pos);
        mViewPager.getViewPager().setAdapter(adapter);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        mViewPager.getPagerTitleStrip().invalidate();
        mViewPager.getViewPager().setOffscreenPageLimit(1);
    }

    MyDialogListener onDialogCloseListener = new MyDialogListener() {
        @Override
        public void handleDialogDismiss(DialogInterface dialogInterface) {
            RecordManager.initRecords();
            setPage(0);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Test", "Resume");
        mDemoSlider.startAutoCycle();
        RecordManager.initRecords();
        setPage(0);
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
        ((TextView) findViewById(R.id.rate_text)).setTypeface(CreditUtil.getTypeface());
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

    @Override
    public void onAddClick() {
        setPage(2);
    }
}
