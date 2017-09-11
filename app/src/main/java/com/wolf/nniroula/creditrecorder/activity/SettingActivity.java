package com.wolf.nniroula.creditrecorder.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.balysv.materialripple.MaterialRippleLayout;
import com.github.johnpersano.supertoasts.SuperToast;
import com.rey.material.widget.Switch;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.dialogs.SettingDialog;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.model.SettingManager;
import com.wolf.nniroula.creditrecorder.ui.RisingNumberView;
import com.wolf.nniroula.creditrecorder.ui.interfaces.MyDialogListener;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.Arrays;
import java.util.List;

public class SettingActivity extends AppCompatActivity
        implements
        View.OnClickListener,
        ColorChooserDialog.ColorCallback,
        Switch.OnCheckedChangeListener {

    private final int UPDATE_LOGO = 0;
    private final int UPDATE_IS_MONTH_LIMIT = 1;
    private final int UPDATE_MONTH_LIMIT = 2;
    private final int UPDATE_IS_COLOR_REMIND = 3;
    private final int UPDATE_MONTH_WARNING = 4;
    private final int UPDATE_REMIND_COLOR = 5;
    private final int UPDATE_IS_FORBIDDEN = 6;
    private final int UPDATE_ACCOUNT_BOOK_NAME = 7;
    private final int UPDATE_ACCOUNT_BOOK_PASSWORD = 8;
    private final int UPDATE_SHOW_PICTURE = 9;
    private final int UPDATE_IS_HOLLOW = 10;
    private final int UPDATE_LOGO_ID = 11;

    private Context mContext;

    private MaterialIconView back;
    private TextView titleText;
    private TextView totalDuesTv;
    private TextView noteNameTv;
    private TextView noteName;
    private TextView duesLimitSwitchTV;
    private TextView duesLimitValueTv;
    private TextView duesLimitValue;
    private TextView duesLimitColorTv;
    private TextView sortTv;
    private TextView sort;
    private TextView currencyTv;
    private TextView currency;
    private TextView itemSettingTv;
    private TextView currentVersionTV;
    private TextView currencyDue;


    private RisingNumberView totalDues;

    private Switch duesColorSwitch;

    private MaterialIconView colorChanger;
    private MaterialIconView duesLimitSwitchIcon;
    private MaterialIconView duesLimitValueIcon;
    private MaterialIconView duesLimitColorIcon;
    private MaterialIconView canUpdateIcon;


    private MaterialRippleLayout versionLayout;
    private MaterialRippleLayout itemSettingLayout;
    private MaterialRippleLayout currencyCodeLayout;
    private MaterialRippleLayout sortLayout;
    private MaterialRippleLayout colorChangerLayout;
    private MaterialRippleLayout limitValueLayout;
    private MaterialRippleLayout limitSwitchLayout;
    private MaterialRippleLayout noteNameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.activity_setting);

        int currentApiVersion = Build.VERSION.SDK_INT;

        if (currentApiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(mContext, R.color.statusBarColor));
            }
        } else {
            // do something for phones running an SDK before lollipop
            View statusBarView = findViewById(R.id.status_bar_view);
            statusBarView.getLayoutParams().height = CreditUtil.getStatusBarHeight();
        }

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        SuperToast.cancelAllSuperToasts();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_image:
                break;
        }
    }


    private void changeNoteName() {
        new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .typeface(CreditUtil.getTypeface(), CreditUtil.getTypeface())
                .title(R.string.note_name_dialog_title)
                .inputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(1, 16)
                .positiveText(R.string.ok)
                .input(SettingManager.getInstance().getNotesName()
                        , null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                SettingManager.getInstance().setNotesName(input.toString());
                                titleText.setText(input.toString());
                                noteName.setText(input.toString());
                                showToast(2, "");

                            }
                        }).show();
    }


    private void changeDueLimit() {
        new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .typeface(CreditUtil.getTypeface(), CreditUtil.getTypeface())
                .title(R.string.due_limit_dialog_title)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .inputRange(1, 8)
                .positiveText(R.string.ok)
                .input(SettingManager.getInstance().getDueLimit() + ""
                        , null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                SettingManager.getInstance().setDueLimit(Integer.parseInt(input.toString()));
                                duesLimitValue.setText(input.toString());
                                showToast(1, "");

                            }
                        }).show();
    }

    private void changeCurrency() {
        List<String> currency = Arrays.asList(getResources().getStringArray(R.array.pref_currency_keys));
        final TextView LocalCurrency = this.currency;

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(SettingActivity.this,
                R.layout.simple_spinner_dropdown, currency);

        final Spinner sp = new Spinner(SettingActivity.this);
        sp.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        sp.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        sp.setSelection(adp.getPosition(SettingManager.getInstance().getCurrency()), true);
        //sp.setPopupBackgroundResource(R.drawable.spinner_bg);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(SettingActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Currency code");
        builder.setView(sp);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SettingManager.getInstance().setCurrency(sp.getSelectedItem().toString());
                sp.setSelection(sp.getSelectedItemPosition(), true);
                LocalCurrency.setText(SettingManager.getInstance().getCurrency());
                currencyDue.setText(SettingManager.getInstance().getCurrency());
                showToast(0, "");
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void changeSort() {
        List<String> sort = Arrays.asList(getResources().getStringArray(R.array.pref_sort_keys));
        final TextView LocalSort = this.sort;
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(SettingActivity.this,
                R.layout.simple_spinner_dropdown, sort);

        final Spinner sp = new Spinner(SettingActivity.this);
        sp.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        sp.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        sp.setSelection(adp.getPosition(SettingManager.getInstance().getSort()), true);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(SettingActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Sort by");
        builder.setView(sp);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("CoCoin", sp.getSelectedItem().toString());

                SettingManager.getInstance().setSort(sp.getSelectedItem().toString());
                sp.setSelection(sp.getSelectedItemPosition(), true);
                LocalSort.setText(SettingManager.getInstance().getSort());

                showToast(3, "");

                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void init() {
        back = (MaterialIconView) findViewById(R.id.icon_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        currencyDue = (TextView) findViewById(R.id.due_currency);
        currencyDue.setTypeface(CreditUtil.typefaceLatoLight);
        currencyDue.setText(SettingManager.getInstance().getCurrency());

        totalDues = (RisingNumberView) findViewById(R.id.dues);
        totalDues.setTypeface(CreditUtil.typefaceLatoLight);
        totalDues.withNumber(Float.parseFloat(RecordManager.TOTAL_DUES.toString())).setDuration(3000).start();

        titleText = (TextView) findViewById(R.id.title_text);
        titleText.setTypeface(CreditUtil.typefaceLatoLight);

        totalDuesTv = (TextView) findViewById(R.id.dues_text);
        totalDuesTv.setTypeface(CreditUtil.typefaceLatoLight);

        noteNameTv = (TextView) findViewById(R.id.note_name_text);
        noteNameTv.setTypeface(CreditUtil.typefaceLatoLight);

        noteName = (TextView) findViewById(R.id.note_name);
        noteName.setTypeface(CreditUtil.typefaceLatoLight);

        duesLimitSwitchTV = (TextView) findViewById(R.id.dues_limit_switch_text);
        duesLimitSwitchTV.setTypeface(CreditUtil.typefaceLatoLight);

        duesLimitValueTv = (TextView) findViewById(R.id.dues_limit_value_text);
        duesLimitValueTv.setTypeface(CreditUtil.typefaceLatoLight);

        duesLimitValue = (TextView) findViewById(R.id.dues_limit);
        duesLimitValue.setTypeface(CreditUtil.typefaceLatoLight);

        duesLimitColorTv = (TextView) findViewById(R.id.dues_limit_color_text);
        duesLimitColorTv.setTypeface(CreditUtil.typefaceLatoLight);

        currencyTv = (TextView) findViewById(R.id.currency_text);
        currencyTv.setTypeface(CreditUtil.typefaceLatoLight);

        currency = (TextView) findViewById(R.id.currency_name);
        currency.setTypeface(CreditUtil.typefaceLatoLight);

        sortTv = (TextView) findViewById(R.id.sort_text);
        sortTv.setTypeface(CreditUtil.typefaceLatoLight);

        sort = (TextView) findViewById(R.id.sort_name);
        sort.setTypeface(CreditUtil.typefaceLatoLight);

        itemSettingTv = (TextView) findViewById(R.id.item_settings_text);
        itemSettingTv.setTypeface(CreditUtil.typefaceLatoLight);

        duesColorSwitch = (Switch) findViewById(R.id.dues_limit_switch);
        duesColorSwitch.setOnCheckedChangeListener(this);

        colorChanger = (MaterialIconView) findViewById(R.id.dues_limit_color);
        duesLimitSwitchIcon = (MaterialIconView) findViewById(R.id.dues_limit_switch_icon);
        duesLimitValueIcon = (MaterialIconView) findViewById(R.id.dues_limit_icon);
        duesLimitColorIcon = (MaterialIconView) findViewById(R.id.dues_limit_color_icon);

        versionLayout = (MaterialRippleLayout) findViewById(R.id.version_layout);
        itemSettingLayout = (MaterialRippleLayout) findViewById(R.id.items_settings_layout);
        itemSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingDialog();
            }
        });
        currencyCodeLayout = (MaterialRippleLayout) findViewById(R.id.currency_layout);
        currencyCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrency();
            }
        });
        colorChangerLayout = (MaterialRippleLayout) findViewById(R.id.limit_color_layout);
        limitValueLayout = (MaterialRippleLayout) findViewById(R.id.debit_limit_layout);
        limitValueLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SettingManager.getInstance().getIsColorRemind()) {
                    changeDueLimit();
                }

            }
        });
        limitSwitchLayout = (MaterialRippleLayout) findViewById(R.id.color_switch_layout);
        sortLayout = (MaterialRippleLayout) findViewById(R.id.sort_layout);
        sortLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSort();
            }
        });


        colorChanger.setColor(SettingManager.getInstance().getRemindColor());
        titleText.setText(SettingManager.getInstance().getNotesName());
        noteName.setText(SettingManager.getInstance().getNotesName());
        duesLimitValue.setText(SettingManager.getInstance().getDueLimit() + "");
        sort.setText(SettingManager.getInstance().getSort());
        currency.setText(SettingManager.getInstance().getCurrency());

        colorChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingManager.getInstance()
                        .setMainViewTitleShouldChange(true);
                remindColorSelectDialog.show((AppCompatActivity) mContext);
            }
        });


        noteNameLayout = (MaterialRippleLayout) findViewById(R.id.note_name_layout);
        noteNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNoteName();
            }
        });

        //All about dues limit reminder
        setIconEnable(duesLimitSwitchIcon, SettingManager.getInstance().getIsColorRemind());
        setIconEnable(duesLimitValueIcon, SettingManager.getInstance().getIsColorRemind());
        setIconEnable(duesLimitColorIcon, SettingManager.getInstance().getIsColorRemind());
        setIconEnable(colorChanger, SettingManager.getInstance().getIsColorRemind());
        colorChanger.setClickable(SettingManager.getInstance().getIsColorRemind());
        limitValueLayout.setClickable(SettingManager.getInstance().getIsColorRemind());
        duesColorSwitch.setChecked(SettingManager.getInstance().getIsColorRemind());
        if (SettingManager.getInstance().getIsColorRemind()) {
            colorChanger.setColor(SettingManager.getInstance().getRemindColor());
        }

        currentVersionTV = (TextView) findViewById(R.id.version_text);
        currentVersionTV.setTypeface(CreditUtil.getTypeface());
        currentVersionTV.setText(mContext.getResources().getString(R.string.app_name) + " " + CreditUtil.getCurrentVersion());
        canUpdateIcon = (MaterialIconView) findViewById(R.id.update_version_available);
        if (SettingManager.getInstance().getCanBeUpdated()) {
            canUpdateIcon.setVisibility(View.VISIBLE);
        } else {
            canUpdateIcon.setVisibility(View.GONE);
        }
    }

    private void setIconEnable(MaterialIconView icon, boolean enable) {
        if (enable) icon.setColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        else icon.setColor(mContext.getResources().getColor(R.color.my_gray));
    }

    @Override
    public void onColorSelection(ColorChooserDialog dialog, int selectedColor) {
        colorChanger.setColor(selectedColor);
        SettingManager.getInstance().setRemindColor(selectedColor);
    }

    ColorChooserDialog remindColorSelectDialog =
            new ColorChooserDialog.Builder(this, R.string.set_remind_color_dialog_title)
                    .titleSub(R.string.set_remind_color_dialog_sub_title)
                    .preselect(SettingManager.getInstance().getRemindColor())
                    .doneButton(R.string.submit)
                    .cancelButton(R.string.cancel)
                    .backButton(R.string.back)
                    .customButton(R.string.custom)
                    .dynamicButtonColor(true)
                    .build();


    // Get string///////////////////////////////////////////////////////////////////////////////////////
    private String getResourceString(int resourceId) {
        return CreditApplication.getAppContext().getResources().getString(resourceId);
    }

    // activity finish//////////////////////////////////////////////////////////////////////////////////
    @Override
    public void finish() {

        SuperToast.cancelAllSuperToasts();
        super.finish();
    }

    // Show toast///////////////////////////////////////////////////////////////////////////////////////
    private void showToast(int toastType, String msg) {
        Log.d("CoCoin", msg);
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(mContext);

        superToast.setAnimations(CreditUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);

        String tip = "";

        switch (toastType) {
            case 0:
                // the currency code changed successfully
                superToast.setText(CreditApplication.getAppContext().getResources().getString(
                        R.string.change_currency_successfully));
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 1:
                // the due limit is changed
                superToast.setText(CreditApplication.getAppContext().getResources().getString(
                        R.string.change_due_limit));
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 2:
                // the new account book name is changed successfully
                superToast.setText(CreditApplication.getAppContext().getResources().getString(
                        R.string.change_account_book_name_successfully));
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 3:
                // sorting order changed
                superToast.setText(CreditApplication.getAppContext().getResources().getString(
                        R.string.change_sort));
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
        }
        superToast.getTextView().setTypeface(CreditUtil.getTypeface());
        superToast.show();
    }

    private void showSettingDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingDialog sd = new SettingDialog();
        sd.setInfo(this);
        sd.show(fm, "dialogSettings");
        sd.DismissListener(onDialogCloseListener);
    }

    MyDialogListener onDialogCloseListener = new MyDialogListener() {
        @Override
        public void handleDialogDismiss(DialogInterface dialogInterface) {
            RecordManager.initRecords();
        }
    };

    @Override
    public void onCheckedChanged(Switch view, boolean checked) {
        SettingManager.getInstance().setIsColorRemind(checked);
        setIconEnable(duesLimitSwitchIcon, checked);
        setIconEnable(duesLimitValueIcon, checked);
        setIconEnable(duesLimitColorIcon, checked);
        setIconEnable(colorChanger, checked);
        colorChanger.setClickable(checked);
        if (checked) {
            colorChanger.setColor(SettingManager.getInstance().getRemindColor());
        }
    }
}
