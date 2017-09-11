package com.wolf.nniroula.creditrecorder.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.github.johnpersano.supertoasts.SuperToast;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.dbhelper.Recorder;
import com.wolf.nniroula.creditrecorder.model.ItemModel;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.ui.interfaces.MyDialogListener;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nniroula on 5/25/16.
 */
public class SettingDialog extends DialogFragment {
    private Button save, cancel;
    private Spinner spinner;
    private FloatingActionButton addNew;
    private EditText Name, Price, Unit;
    private TextView title;
    private Recorder db;
    private Context mContext;
    private ArrayList<ItemModel> Items;
    int SelItemId = 0;
    private boolean add = false;
    private ProgressDialog pDialog;
    private MyDialogListener closeListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setting, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        db = RecordManager.db;
        Items = RecordManager.ALL_ITEMS;

        if (Items.isEmpty()) {
            add = true;
        }

        Name = (EditText) rootView.findViewById(R.id.editName);
        Name.setTypeface(CreditUtil.typefaceLatoLight);

        Price = (EditText) rootView.findViewById(R.id.editPrice);
        Price.setTypeface(CreditUtil.typefaceLatoLight);

        Unit = (EditText) rootView.findViewById(R.id.editMeasurementUnit);
        Unit.setTypeface(CreditUtil.typefaceLatoLight);

        title = (TextView) rootView.findViewById(R.id.textTitle);
        title.setTypeface(CreditUtil.typefaceLatoLight);

        addNew = (FloatingActionButton) rootView.findViewById(R.id.floatNew);

        save = (Button) rootView.findViewById(R.id.butSave);
        save.setTypeface(CreditUtil.typefaceLatoLight);

        cancel = (Button) rootView.findViewById(R.id.butCancel);
        cancel.setTypeface(CreditUtil.typefaceLatoLight);

        spinner = (Spinner) rootView.findViewById(R.id.spinner);

        final String spinnerArray[] = new String[Items.size()];
        if (!add) {
            for (int i = 0; i < Items.size(); i++) {
                spinnerArray[i] = Items.get(i).getItem_name();
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_dropdown, spinnerArray);
            spinner.setAdapter(spinnerArrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SelItemId = Items.get(position).getId();
                    SetPrice(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {

            addNew.hide();
            spinner.setVisibility(View.INVISIBLE);
            title.setText("Add New Item");
            cancel.setText("Cancel");
            save.setText("Add");
            Name.setText(null);
            Name.setEnabled(true);
            Price.setText(null);
            Unit.setText(null);
        }

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Name.getText() == null || Price.getText() == null || Unit.getText() == null || "".equals(Name.getText().toString()) || "".equals(Price.getText().toString()) || "".equals(Unit.getText().toString())) {
                    showToast(0);
                } else {
                    new PostTask().execute();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("Id", "-----" + SelItemId);
                if (!add) {

                    final AlertDialog.Builder builder =
                            new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle(Name.getText().toString().toUpperCase());
                    builder.setMessage("Delete this item permanently ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            db.delItem(SelItemId);
                            showToast(1);
                            dismiss();

                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    });
                    builder.setIcon(R.drawable.ic_report_problem_black_24dp);
                    builder.show();
                } else if (add) {
                    dismiss();
                }

            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (RecordManager.TOTAL_ITEMS > 5) {
//                    if (SettingManager.getInstance().isVideoWatched()) {
//                        showAddNew();
//                    } else {
//                        showAdDialog();
//                    }
//                } else {
//                    showAddNew();
//                }
                showAddNew();
            }
        });
        return rootView;
    }

    private void showAdDialog() {
        new MaterialDialog.Builder(mContext)
                .theme(Theme.LIGHT)
                .typeface(CreditUtil.getTypeface(), CreditUtil.getTypeface())
                .title(R.string.ad_dialog_title)
                .content(R.string.ad_dialog_content)
                .iconRes(R.drawable.ic_movie_black_24dp)
                .negativeText(R.string.ad_dialog_positive)
                .positiveText(R.string.ad_dialog_negative)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        showAddNew();

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

    private void showAddNew() {
        add = true;
        addNew.hide();
        spinner.setVisibility(View.INVISIBLE);
        title.setText("Add New Item");
        cancel.setText("Cancel");
        save.setText("Add");
        Name.setText(null);
        Name.setEnabled(true);
        Price.setText(null);
        Unit.setText(null);
    }

    public void DismissListener(MyDialogListener closeDialogListener) {
        this.closeListener = closeDialogListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (closeListener != null) {
            closeListener.handleDialogDismiss(null);
        }
    }


    public void SetPrice(int id) {

        Name.setText(Items.get(id).getItem_name());
        Name.setEnabled(false);
        Price.setText(Items.get(id).getItem_price().toString());
        Unit.setText(Items.get(id).getItem_unit());
    }

    public ItemModel getNewPrice(int id) {
        ItemModel itemModel = new ItemModel(id);
        itemModel.setItem_name(Name.getText().toString());
        itemModel.setItem_price(Double.parseDouble(Price.getText().toString()));
        itemModel.setItem_unit(Unit.getText().toString());
        return itemModel;

    }

    public void setInfo(Context context) {
        this.mContext = context;
    }

    private class PostTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Saving ...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                if (!add) {
                    ItemModel NewPrices = getNewPrice(SelItemId);
                    db.UpdatePrice(NewPrices);
                    return true;
                } else if (add) {

                    ItemModel NewPrices = getNewPrice(SelItemId);
                    if (db.verifyItem(NewPrices.getItem_name())) {
                        return false;
                    } else {
                        db.CreatePrice(NewPrices);
                        return true;
                    }

                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            } catch (SQLException e) {
                return false;
            }

        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (pDialog != null) {
                pDialog.dismiss();
            }
            if (result)
                showToast(2);
            else
                showToast(3);
            dismiss();

        }

    }

    private void showToast(int toastType) {

        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(mContext);

        superToast.setAnimations(CreditUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);

        String tip = "";

        switch (toastType) {
            case 0:
                // Unable to save
                superToast.setText("Enter the required values!");
                superToast.setBackground(SuperToast.Background.RED);
                break;
            case 1:
                // Record update
                superToast.setText("Item Deleted Successfully.");
                superToast.setBackground(SuperToast.Background.BLUE);
                break;
            case 2:
                // Update fail
                superToast.setText("Operation Successful.");
                superToast.setBackground(SuperToast.Background.BLACK);
                break;
            case 3:
                // Entry delete
                superToast.setText("Operation Failed.");
                superToast.setBackground(SuperToast.Background.RED);
                break;
        }
        superToast.getTextView().setTypeface(CreditUtil.getTypeface());
        superToast.show();
    }
}
