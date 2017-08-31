package com.wolf.nniroula.creditrecorder.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import com.wolf.nniroula.creditrecorder.dbhelper.Recorder;
import com.wolf.nniroula.creditrecorder.model.ItemModel;
import com.wolf.nniroula.creditrecorder.R;

import java.io.IOException;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setting, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        try {
            db = new Recorder(mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Items = db.getAllPrices();

        if (Items.isEmpty()) {
            add = true;
        }

        Name = (EditText) rootView.findViewById(R.id.editName);
        Price = (EditText) rootView.findViewById(R.id.editPrice);
        Unit = (EditText) rootView.findViewById(R.id.editMeasurementUnit);
        title = (TextView) rootView.findViewById(R.id.textTitle);
        addNew = (FloatingActionButton) rootView.findViewById(R.id.floatNew);

        save = (Button) rootView.findViewById(R.id.butSave);
        cancel = (Button) rootView.findViewById(R.id.butCancel);
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
            Price.setText(null);
            Unit.setText(null);
        }

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Name.getText() == null || Price.getText() == null || Unit.getText() == null || "".equals(Name.getText().toString()) || "".equals(Price.getText().toString()) || "".equals(Unit.getText().toString())) {
                    Toast.makeText(mContext, "Enter the required values!", Toast.LENGTH_SHORT).show();
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
                    builder.setMessage("Delete this item ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            db.delItem(SelItemId);
                            Toast.makeText(mContext, "Item Deleted Successfully.", Toast.LENGTH_SHORT).show();
                            dismiss();

                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setIcon(R.mipmap.ic_alert_del);
                    builder.show();
                }

                dismiss();
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add = true;
                addNew.hide();
                spinner.setVisibility(View.INVISIBLE);
                title.setText("Add New Item");
                cancel.setText("Cancel");
                save.setText("Add");
                Name.setText(null);
                Price.setText(null);
                Unit.setText(null);
            }
        });
        return rootView;
    }


    public void SetPrice(int id) {

        Name.setText(Items.get(id).getItem_name());
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


            if (!add) {
                ItemModel NewPrices = getNewPrice(SelItemId);
                db.UpdatePrice(NewPrices);
                return true;
            }
            if (add) {

                ItemModel NewPrices = getNewPrice(SelItemId);
                db.CreatePrice(NewPrices);
                return true;
            } else {
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
                Toast.makeText(mContext, "Operation Successful.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mContext, "Operation Failed.", Toast.LENGTH_SHORT).show();
            dismiss();

        }
    }
}
