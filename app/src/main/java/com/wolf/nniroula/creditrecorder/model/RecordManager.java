package com.wolf.nniroula.creditrecorder.model;

import android.content.Context;
import android.util.Log;

import com.wolf.nniroula.creditrecorder.BuildConfig;
import com.wolf.nniroula.creditrecorder.dbhelper.Recorder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Niraj Niroula on 8/28/17.
 */

public class RecordManager {
    private static RecordManager recordManager = null;
    private Context mContext;
    private static Recorder db;
    public static Double TOTAL_CREDITS = 0.00;
    public static Double TOTAL_DEBITS = 0.00;
    public static int TOTAL_RECORDS = 0;
    public static int TOTAL_ITEMS = 0;

    public static ArrayList<RecordModel> ALL_RECORDS;
    public static ArrayList<RecordModel> ALL_RECORDS_BY_NAME;

    //Don't use this to insert in DB
    public static ArrayList<RecordModel> ALL_SINGLE_RECORDS;
    public static ArrayList<RecordModel> ALL_RECORDS_BY_CREDITS;


    public static ArrayList<PaidModel> ALL_PAID;
    public static ArrayList<ItemModel> ALL_ITEMS;

    public RecordManager(Context context) {
        this.mContext = context;
        try {
            db = db.getInstance(context);
            if (BuildConfig.DEBUG)
                if (BuildConfig.DEBUG) Log.d("CreditNote", "db.getInstance(context) S");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static RecordManager getInstance(Context context) {
        if (ALL_RECORDS == null || ALL_ITEMS == null || TOTAL_CREDITS == null ||
                TOTAL_DEBITS == null || recordManager == null) {

            recordManager = new RecordManager(context);
            initRecords();


            if (BuildConfig.DEBUG) {
                if (BuildConfig.DEBUG) Log.d("credit", "Load " + ALL_RECORDS.size() + " records S");
                if (BuildConfig.DEBUG) Log.d("Credit", "Load " + ALL_ITEMS.size() + " tags S");
            }
        }
        return recordManager;
    }


    public static void initRecords() {
        ALL_RECORDS = db.getAllEntries();
        ALL_PAID = db.getAllPaid();
        ALL_ITEMS = db.getAllPrices();
        ALL_SINGLE_RECORDS = checkDuplicateList(ALL_RECORDS);

        TOTAL_ITEMS = ALL_ITEMS.size();
        TOTAL_RECORDS = ALL_SINGLE_RECORDS.size();

        if (ALL_ITEMS.isEmpty()) {
            ItemModel cashModel = new ItemModel(1, "Cash", "Rs", 1.00);
            db.CreatePrice(cashModel);
            ALL_ITEMS = db.getAllPrices();
        }

        ALL_RECORDS_BY_NAME = ALL_SINGLE_RECORDS;
        ALL_RECORDS_BY_CREDITS = ALL_SINGLE_RECORDS;

        Collections.sort(ALL_RECORDS_BY_NAME, new Comparator<RecordModel>() {
            public int compare(RecordModel r1, RecordModel r2) {
                return r1.getName().compareTo(r2.getName());
            }
        });

        Collections.sort(ALL_RECORDS_BY_CREDITS, new Comparator<RecordModel>() {
            public int compare(RecordModel r1, RecordModel r2) {
                return r1.getPrice().compareTo(r2.getPrice());
            }
        });

        Collections.reverse(ALL_RECORDS_BY_CREDITS);

        for(RecordModel recordModel:ALL_RECORDS_BY_CREDITS){
            Log.e("SortCheck", recordModel.getPrice()+"...."+recordModel.getName());
        }
    }


    //Check whether there are more than 1 records for a user.
    private static ArrayList<RecordModel> checkDuplicateList(ArrayList<RecordModel> mRecordModel) {
        ArrayList<RecordModel> cleanRecordModel;
        String Items;
        cleanRecordModel = mRecordModel;
        for (int j = 0; j < cleanRecordModel.size(); j++) {
            //Log.e("sRemoves", mRecordModel.get(j).getName());
            Items = cleanRecordModel.get(j).getItem();
            for (int k = j + 1; k < cleanRecordModel.size(); k++) {
                if (j != k && (cleanRecordModel.get(k).getName()).equals(cleanRecordModel.get(j).getName())) {
                    if (!cleanRecordModel.get(j).getItem().contains(cleanRecordModel.get(k).getItem())) {

                        Items = Items + cleanRecordModel.get(k).getItem();

                    }
                    cleanRecordModel.get(j).setPrice(cleanRecordModel.get(j).getPrice() + cleanRecordModel.get(k).getPrice());
                    cleanRecordModel.remove(k);
                } else {
                    // Log.e("Not Removed", cleanRecordModel.get(k).getName());
                }
            }
            cleanRecordModel.get(j).setItem(managedItems(Items));

        }
        return cleanRecordModel;
    }

    private static String managedItems(String allItems) {

        String filteredItems = "";
        for (int i = 0; i < ALL_ITEMS.size(); i++) {
            if (allItems.contains(ALL_ITEMS.get(i).getItem_name()))
                filteredItems = filteredItems + " + " + ALL_ITEMS.get(i).getItem_name();
        }
        if (filteredItems.startsWith(" + ")) filteredItems = filteredItems.substring(3);
        return filteredItems;
    }

    public Double getPaid(String name) {
        Double totalPaid = 0.00;
        ArrayList<PaidModel> PaidModel = db.getAllIndPaid(name);
        for (int i = 0; i < PaidModel.size(); i++) {
            totalPaid = totalPaid + PaidModel.get(i).getPaid();
        }
        db.close();
        return totalPaid;
    }

    public boolean nameDuplicates(String name) {
        try {
            return db.verification(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createEntry(String Name, Double Wt, String item, String Unit, Double Price, String Date) {
        try {
            db.CreateEntry(Name, Wt, item, Unit, Price, Date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPaid(String Name, Double Paid, String Date) {
        try {
            db.CreatePaid(Name, Paid, Date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


