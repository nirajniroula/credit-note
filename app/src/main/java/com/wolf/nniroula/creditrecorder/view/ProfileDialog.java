package com.wolf.nniroula.creditrecorder.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.dbhelper.Recorder;
import com.wolf.nniroula.creditrecorder.model.MyDialogListener;
import com.wolf.nniroula.creditrecorder.model.PaidModel;
import com.wolf.nniroula.creditrecorder.model.PreferenceCredit;
import com.wolf.nniroula.creditrecorder.model.RecordAdapter;
import com.wolf.nniroula.creditrecorder.model.RecordModel;
import com.wolf.nniroula.creditrecorder.utils.FormatLargeNumber;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nniroula on 5/19/16.
 */
public class ProfileDialog extends DialogFragment {
    private TextView Name, Price, TPaid, currencyPrice, currencyPaid;
    private EditText Date, Paid;
    private String balItem = "";
    private CheckBox check_date;
    private Button save, clear;
    private FloatingActionButton add;
    private ImageView share;
    private Recorder db;
    private Context mContext;
    private RecordModel cModel;
    private RecordAdapter mAdapter;
    private PreferenceCredit preferenceCredit;

    private ListView mylist;
    private List<String> ListInd, ListPaid;
    private List<RecordModel> recordModels = null;
    private MyDialogListener closeListener;
    private ArrayAdapter<String> adapter;
    private String shareData = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ind_view, container, true);

        db = new Recorder(mContext);
        mAdapter = new RecordAdapter(mContext);
        preferenceCredit = new PreferenceCredit(mContext);
        shareData = "";


        mylist = (ListView) rootView.findViewById(R.id.listview);
        add = (FloatingActionButton) rootView.findViewById(R.id.floatAdd);
        share = (ImageView) rootView.findViewById(R.id.share);


        Name = (TextView) rootView.findViewById(R.id.textNa);
        Price = (TextView) rootView.findViewById(R.id.textPr);
        TPaid = (TextView) rootView.findViewById(R.id.textPay);
        Date = (EditText) rootView.findViewById(R.id.editDt);
        Paid = (EditText) rootView.findViewById(R.id.editPd);

        currencyPrice = (TextView) rootView.findViewById(R.id.total_text_nrs);
        currencyPaid = (TextView) rootView.findViewById(R.id.total_paid_nrs);

        currencyPrice.setText("Total Credit Amount: " + preferenceCredit.getCurrency() + " ");
        currencyPaid.setText("Total Paid Amount: " + preferenceCredit.getCurrency() + " ");

        check_date = (CheckBox) rootView.findViewById(R.id.checkBox);

        save = (Button) rootView.findViewById(R.id.butSave);
        clear = (Button) rootView.findViewById(R.id.butClear);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        Name.setText(cModel.getName());

        final String prevDate = Date.getText().toString();
        check_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_date.isChecked()) {
                    Calendar cal = Calendar.getInstance();
                    String sTime = cal.get(Calendar.YEAR) + " " + cal.getTime().toString().substring(4, 11);
                    Date.setText(sTime);
                } else {
                    Date.setText(prevDate);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (Date.getText() == null || Paid.getText() == null || "".equals(Date.getText().toString()) || "".equals(Paid.getText().toString())) {
                    Toast.makeText(mContext, "Enter the required values!", Toast.LENGTH_SHORT).show();
                } else {
                    updateRecords();
                    dismiss();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(cModel.getName().toUpperCase());
                builder.setMessage("Clear " + cModel.getName() + "'s account ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delEntries(cModel.getName());
                        db.delPaidEntries(cModel.getName());
                        Toast.makeText(mContext, "Record Deleted.", Toast.LENGTH_SHORT).show();
                        dismiss();

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.setIcon(R.mipmap.ic_alert_del);
                builder.show();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyActivity.class);
                intent.putExtra("key", cModel.getName());
                dismiss();
                startActivity(intent);

            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareData);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));

            }
        });
        //TODO: Editing User name on long click.

//        Name.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//
//                final EditText input = new EditText(mContext);
//                AlertDialog dialog = new AlertDialog.Builder(mContext)
//                        .setMessage("Update User's name?")
//                        .setCancelable(false)
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //do things
//                                dialog.dismiss();
//                            }
//                        })
//                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //do things
//                                try {
//                                    db.updateUserName(cModel.getName(), input.getText().toString());
//                                    Toast.makeText(mContext, "User's name updated.", Toast.LENGTH_SHORT).show();
//
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                }
//                                dismiss();
//                            }
//                        })
//                        .create();
//
//                input.setText(cModel.getName());
//
//                input.setGravity(Gravity.CENTER_HORIZONTAL);
//                dialog.setView(input);
//                dialog.show();
//
//
//                return false;
//            }
//        });

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new RecordAdapter(mContext);

    }

    public void updateRecords() {
        //cModel.setPaid(Double.parseDouble(Paid.getText().toString()));
        cModel.setItem(balItem);
        try {
            db.CreatePaid(cModel.getName(), Double.parseDouble(Paid.getText().toString()), Date.getText().toString());
            Toast.makeText(mContext, "Record Updated.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Update Failed.", Toast.LENGTH_SHORT).show();

        }

    }

    public void DismissListener(MyDialogListener closeDialogListener) {
        //updateRecords();
        this.closeListener = closeDialogListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (closeListener != null) {
            closeListener.handleDialogDismiss(null);
        }
    }


    //You are here.... Don't let it go.

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final ArrayList<RecordModel> IndList = db.getAllIndData(cModel.getName());
        ListInd = new ArrayList<>();
        for (int i = 0; i < IndList.size(); i++) {
            ListInd.add(i, i + 1 + ")" + " " + IndList.get(i).getItem() + " " + IndList.get(i).getWeight() + " " + IndList.get(i).getUnit() + "  " + "[" + IndList.get(i).getCreated_at() + "]");
            shareData = shareData + ListInd.get(i) + "\n";
        }
        Double totalPaid = 0.00;
        final ArrayList<PaidModel> PaidList = db.getAllIndPaid(cModel.getName());
        ListPaid = new ArrayList<>();
        for (int i = 0; i < PaidList.size(); i++) {
            totalPaid = totalPaid + PaidList.get(i).getPaid();
            ListPaid.add(i, i + 1 + ")" + " " + "Paid " + preferenceCredit.getCurrency() + " " + PaidList.get(i).getPaid() + "  " + "[" + PaidList.get(i).getPaid_date() + "]");
            shareData = shareData + ListPaid.get(i) + "\n";
        }

        final List<String> ListIndPaid = new ArrayList<>(ListInd);
        ListIndPaid.addAll(ListPaid);


        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_layout, R.id.listText1, ListIndPaid) {

            @Override
            public View getView(final int position, final View convertView, final ViewGroup parent) {

                final View view = super.getView(position, convertView, parent);
                final TextView textView = (TextView) view.findViewById(R.id.listText1);
                ImageButton delete = (ImageButton) view.findViewById(R.id.listDel);

                textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        AlertDialog dialog = new AlertDialog.Builder(mContext)
                                .setTitle(cModel.getName())
                                .setMessage(ListIndPaid.get(position).substring(2))
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                        dialog.dismiss();
                                    }
                                })
                                .create();

                        dialog.show();

                        return false;
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO:Delete the clicked entry from database.

                        final AlertDialog.Builder builder =
                                new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle("Delete " + cModel.getName() + "'s Entry ?");
                        builder.setMessage(ListIndPaid.get(position).substring(2));
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (position < IndList.size()) {
                                    Log.e("Check", IndList.get(position).getWeight() + "...");
                                    db.delEntry(IndList.get(position).getId());
                                }
                                if (position >= IndList.size()) {
                                    Log.e("Check", PaidList.get(position - IndList.size()).getPaid() + "...");
                                    db.delPaidEntry(PaidList.get(position - IndList.size()).getId());
                                }
                                Toast.makeText(mContext, "Entry Deleted.", Toast.LENGTH_SHORT).show();
                                dismiss();


                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                        builder.setIcon(R.mipmap.ic_alert_del);
                        builder.show();

                    }
                });
                if (position < IndList.size())
                    textView.setTextColor(getResources().getColor(R.color.colorExtra1));
                if (position >= IndList.size())
                    textView.setTextColor(getResources().getColor(R.color.colorExtra2));
                return view;
            }
        };

        mylist.setAdapter(adapter);
        TPaid.setText(FormatLargeNumber.formattedNumber(totalPaid));
        Price.setText(FormatLargeNumber.formattedNumber(cModel.getPrice()));

        shareData = shareData + "\n" + currencyPrice.getText() + FormatLargeNumber.formattedNumber(cModel.getPrice()) + "\n";
        shareData = shareData + currencyPaid.getText() + FormatLargeNumber.formattedNumber(totalPaid) + "\n\n";
        shareData = shareData + "                 From: " + new PreferenceCredit(mContext).getBusiness() + "\n\n";
        shareData = shareData + "Find the app in Google Play Store. " +
                "\nhttps://play.google.com/store/apps/details?id=com.wolf.nniroula.creditrecorder " +
                "\n\nThank you.";


    }


    public void setInfo(RecordModel contactModel, Context context) {
        this.cModel = contactModel;
        this.mContext = context;
    }
}
