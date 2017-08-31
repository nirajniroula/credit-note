package com.wolf.nniroula.creditrecorder.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.dbhelper.Recorder;
import com.wolf.nniroula.creditrecorder.model.ItemModel;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.utils.EditTextDialogUtils;
import com.wolf.nniroula.creditrecorder.utils.MyTextView;
import com.wolf.nniroula.creditrecorder.utils.PreferenceCredit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class MyActivity extends Activity implements View.OnClickListener {

    private EditText name, weight, paid, date;
    private PreferenceCredit preferenceCredit;
    private CheckBox dateCheck;
    private RadioButton radioButton;
    private Button save, view;
    private String item = "";
    private Recorder entry;
    private Double price;
    private ArrayList<ItemModel> itemModels = null;
    private Bundle bundle = null;
    private Double sWt, Total, sPaid;
    private String sName, sDate, sUnit;
    private AdView mAdView;
    private MyTextView businessName;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        preferenceCredit = new PreferenceCredit(this);
        try {
            entry = new Recorder(MyActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //createItems();
        itemModels = RecordManager.ALL_ITEMS;

        businessName = (MyTextView) findViewById(R.id.business_name);
        businessName.setSelected(true);

        name = (EditText) findViewById(R.id.input_name);
        date = (EditText) findViewById(R.id.input_date);
        dateCheck = (CheckBox) findViewById(R.id.checkBox);
        weight = (EditText) findViewById(R.id.input_weight);
        paid = (EditText) findViewById(R.id.input_paid);
        save = (Button) findViewById(R.id.btn_save);
        view = (Button) findViewById(R.id.btn_view);


        save.setOnClickListener(this);
        view.setOnClickListener(this);


        setRadioButton();
        final String prevDate = date.getText().toString();
        dateCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateCheck.isChecked()) {
                    Calendar cal = Calendar.getInstance();
                    String sTime = cal.get(Calendar.YEAR) + " " + cal.getTime().toString().substring(4, 11);
                    date.setText(sTime);
                } else {
                    date.setText(prevDate);
                }
            }
        });

        this.overridePendingTransition(R.anim.leftout,
                R.anim.rightin);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            ToView.fa.finish();
            name.setText(bundle.getString("key"));
        }

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("748B5D5B0C5DC53B78C93CCFC7F0EDB3").build();

        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });


    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public void setRadioButton() {
        RadioGroup radioLayout0 = (RadioGroup) findViewById(R.id.radLayout0);
        for (int i = 0; i < itemModels.size(); i++) {
            radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioButton.setText(itemModels.get(i).getItem_name());
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            radioButton.setLayoutParams(param);
            radioLayout0.addView(radioButton);
            radioButton.setOnClickListener(this);
        }
        if (radioButton != null) radioButton.performClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.my, menu);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Touch again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < itemModels.size(); i++) {
            if (view.getId() == i) {
                price = itemModels.get(i).getItem_price();
                item = itemModels.get(i).getItem_name();
                weight.setHint("*Quantity [" + itemModels.get(i).getItem_unit() + "]");
                paid.setHint("Paid [" + preferenceCredit.getCurrency() + "]");
                sUnit = itemModels.get(i).getItem_unit();
                Log.e("View", view.getId() + "--" + itemModels.get(i).getId());
                break;
            }
        }

        switch (view.getId()) {

            case R.id.btn_save:

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                try {

                    if (date.getText() == null || name.getText() == null || weight.getText() == null || "".equals(date.getText().toString()) || "".equals(name.getText().toString()) || "".equals(weight.getText().toString())) {

                        Toast.makeText(getApplicationContext(), "Please enter the required(*) values!", Toast.LENGTH_LONG).show();
                    } else {
                        sName = name.getText().toString();
                        sDate = date.getText().toString();
                        sWt = Double.parseDouble(weight.getText().toString());


                        Total = sWt * price;
                        if (entry.verification(sName)) {
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle(sName.toUpperCase());
                            builder.setMessage("Name Already Exists !");
                            builder.setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //entry.CreateEntry(sName, sWt, item, sUnit, Total, sDate);
                                    String cPaid = paid.getText().toString();
                                    if (!"".equals(cPaid)) {
                                        sPaid = Double.parseDouble(paid.getText().toString());
                                        //entry.CreatePaid(sName, sPaid, sDate);
                                    }
                                    entry.close();
                                    Intent intent = new Intent(getApplicationContext(), ToView.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                                }
                            });
                            builder.setNegativeButton("RENAME", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    name.setSelection(name.getText().length());
                                    dialog.dismiss();
                                }
                            });
                            builder.setIcon(R.drawable.ic_add_alert_red_24dp);
                            builder.show();

                        } else {
                            String cPaid = paid.getText().toString();
                            if (!"".equals(cPaid)) {
                                sPaid = Double.parseDouble(paid.getText().toString());
                                //entry.CreatePaid(sName, sPaid, sDate);
                            }
                            //entry.CreateEntry(sName, sWt, item, sUnit, Total, sDate);
                            //entry.close();
                            name.setText(null);
                            weight.setText(null);
                            paid.setText(null);
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_view:
                Intent intent = new Intent(getApplicationContext(), ToView.class);
                startActivity(intent);
                finish();
                break;

            case R.id.business_name:
                final AlertDialog.Builder businessDialog = EditTextDialogUtils.EditTextDialog("Name", "Enter your business name here. \n[Eg. Phantom Trades]", this);
                LinearLayout layout = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(params);
                final EditText business = EditTextDialogUtils.TextField(this);
                layout.addView(business);
                businessDialog.setView(layout);

                businessDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //Get this stuff done below on Positive button set on click listener, so this goes here -------> *

                    }
                });
                businessDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog passwordAlertDialog = businessDialog.create();
                passwordAlertDialog.show();

                passwordAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean wantToCloseDialog = false;
                        if (!business.getText().toString().isEmpty()) {
                            preferenceCredit.setBusiness(business.getText().toString());
                            businessName.setText(business.getText().toString());
                            Toast.makeText(MyActivity.this, "Name Saved.", Toast.LENGTH_SHORT).show();
                            wantToCloseDialog = true;
                        } else
                            Toast.makeText(MyActivity.this, "Name, Please!", Toast.LENGTH_SHORT).show();
                        //Do stuff, possibly set wantToCloseDialog to true then...
                        if (wantToCloseDialog)
                            passwordAlertDialog.dismiss();
                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                    }
                });
        }
    }
}
