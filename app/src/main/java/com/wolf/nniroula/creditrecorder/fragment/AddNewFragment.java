package com.wolf.nniroula.creditrecorder.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.android.gms.ads.AdView;
import com.rey.material.widget.RadioButton;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.activity.CreditApplication;
import com.wolf.nniroula.creditrecorder.model.ItemModel;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;
import com.wolf.nniroula.creditrecorder.utils.PreferenceCredit;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Niraj Niroula on 8/27/17.
 */

public class AddNewFragment extends Fragment implements View.OnClickListener {

    private ObservableScrollView mScrollView;
    private RadioButton radioButton[];
    private EditText name, weight, paid, date;
    private Double price;
    private String sName, sDate, sUnit;
    private String item = "";
    private Button save;
    private Double sWt, Total, sPaid;
    private AdView mAdView;
    private ArrayList<ItemModel> itemModels = null;
    private CheckBox dateCheck;
    private RecordManager recordManager;


    public static AddNewFragment newInstance() {
        AddNewFragment fragment = new AddNewFragment();

        return fragment;
    }

    private Activity activity;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        recordManager = new RecordManager(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new, container, false);

        AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(1000);
        view.setAlpha(1f);
        view.startAnimation(animation1);

        this.name = (EditText) view.findViewById(R.id.input_name);
        this.name.setTypeface(CreditUtil.typefaceLatoLight);

        this.date = (EditText) view.findViewById(R.id.input_date);
        this.date.setTypeface(CreditUtil.typefaceLatoLight);

        this.weight = (EditText) view.findViewById(R.id.input_weight);
        this.weight.setTypeface(CreditUtil.typefaceLatoLight);

        this.paid = (EditText) view.findViewById(R.id.input_paid);
        this.paid.setTypeface(CreditUtil.typefaceLatoLight);

        this.dateCheck = (CheckBox) view.findViewById(R.id.checkBox);
        this.dateCheck.setTypeface(CreditUtil.typefaceLatoLight);

        this.save = (Button) view.findViewById(R.id.btn_save);
        this.save.setTypeface(CreditUtil.typefaceLatoLight);


        this.radioButton = new RadioButton[RecordManager.TOTAL_ITEMS];
        setRadioButton(view);

        final String prevDate = date.getText().toString();
        this.dateCheck.setOnClickListener(new View.OnClickListener() {
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


        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Credit", "Save");
                //.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                try {

                    if (date.getText() == null || name.getText() == null || weight.getText() == null || "".equals(date.getText().toString()) || "".equals(name.getText().toString()) || "".equals(weight.getText().toString())) {

                        showToast("Please enter the required(*) values!", 2);
                    } else {
                        sName = name.getText().toString();
                        sDate = date.getText().toString();
                        sWt = Double.parseDouble(weight.getText().toString());


                        Total = sWt * price;
                        if (recordManager.nameDuplicates(sName)) {
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle(sName.toUpperCase());
                            builder.setMessage("Name Already Exists !");
                            builder.setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    recordManager.createEntry(sName, sWt, item, sUnit, Total, sDate);
                                    String cPaid = paid.getText().toString();
                                    if (!"".equals(cPaid)) {
                                        sPaid = Double.parseDouble(paid.getText().toString());
                                        recordManager.createPaid(sName, sPaid, sDate);
                                        showToast("Saved", 1);
                                    }
                                }
                            });
                            builder.setNegativeButton("RENAME", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    name.setSelection(name.getText().length());
                                    dialog.dismiss();
                                    name.setHovered(true);
                                }
                            });
                            builder.setIcon(R.drawable.ic_add_alert_red_24dp);
                            builder.show();

                        } else {
                            String cPaid = paid.getText().toString();
                            if (!"".equals(cPaid)) {
                                sPaid = Double.parseDouble(paid.getText().toString());
                                recordManager.createPaid(sName, sPaid, sDate);
                                showToast("Saved", 1);
                            }
                            recordManager.createEntry(sName, sWt, item, sUnit, Total, sDate);
                            name.setText(null);
                            weight.setText(null);
                            paid.setText(null);

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void setRadioButton(View view) {
        RadioGroup radioLayout0 = (RadioGroup) view.findViewById(R.id.radLayout0);
        for (int i = 0; i < RecordManager.TOTAL_ITEMS; i++) {
            LinearLayout linearLayout = new LinearLayout(CreditApplication.getAppContext());
            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(param1);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(24, 0, 24, 0);

            radioButton[i] = new RadioButton(CreditApplication.getAppContext(), null, R.style.myRadioButtonStyle);
            radioButton[i].setId(i);
            radioButton[i].setLayoutParams(param);
            radioButton[i].setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            radioButton[i].applyStyle(R.style.myRadioButtonStyle);

            TextView radioText = new TextView(CreditApplication.getAppContext());
            radioText.setId(i + 1000);
            radioText.setLayoutParams(param);
            radioText.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            radioText.setText(RecordManager.ALL_ITEMS.get(i).getItem_name());
            radioText.setTypeface(CreditUtil.typefaceLatoLight);
            radioText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            linearLayout.addView(radioButton[i]);
            linearLayout.addView(radioText);

            radioLayout0.addView(linearLayout);
            radioButton[i].setOnClickListener(this);
        }
        if (radioButton != null) radioButton[0].performClick();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);

    }

    private void showToast(String msg, int toastType) {

        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(activity);

        superToast.setAnimations(CreditUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(msg);
        switch (toastType) {
            case 1:
                // Record update
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 2:
                // Update fail
                superToast.setBackground(SuperToast.Background.RED);
                break;
        }
        superToast.getTextView().setTypeface(CreditUtil.getTypeface());
        superToast.show();
    }

    @Override
    public void onClick(View v) {


        for (int i = 0; i < RecordManager.TOTAL_ITEMS; i++) {
            radioButton[i].setChecked(false);
            if (v.getId() == i) {
                price = RecordManager.ALL_ITEMS.get(i).getItem_price();
                item = RecordManager.ALL_ITEMS.get(i).getItem_name();
                weight.setHint("*Quantity [" + RecordManager.ALL_ITEMS.get(i).getItem_unit() + "]");
                paid.setHint("Paid [" + PreferenceCredit.getCurrency() + "]");
                sUnit = RecordManager.ALL_ITEMS.get(i).getItem_unit();
                Log.e("View", v.getId() + "--" + RecordManager.ALL_ITEMS.get(i).getId());
                radioButton[i].setChecked(true);
                YoYo.with(Techniques.Bounce).delay(100).playOn(weight);

            }
        }

    }
}
