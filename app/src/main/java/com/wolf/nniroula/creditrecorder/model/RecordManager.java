package com.wolf.nniroula.creditrecorder.model;

import android.content.Context;
import android.util.Log;

import com.wolf.nniroula.creditrecorder.BuildConfig;
import com.wolf.nniroula.creditrecorder.dbhelper.Recorder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Niraj Niroula on 8/28/17.
 */

public class RecordManager {
    private static RecordManager recordManager = null;
    private Context mContext;
    public static Recorder db;
    public static Double TOTAL_CREDITS = 0.00;
    public static Double TOTAL_DEBITS = 0.00;
    public static Double TOTAL_DUES = 0.00;
    public static int TOTAL_RECORDS = 0;
    public static int TOTAL_ITEMS = 0;

    public static ArrayList<RecordModel> ALL_RECORDS;
    public static ArrayList<PaidModel> ALL_SINGLE_PAID;
    public static HashMap<String, Double> CREDIT_BY_ITEMS = new HashMap<>();

    //Don't use this to insert in DB
    public static ArrayList<RecordModel> ALL_SINGLE_RECORDS;


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
        if (ALL_RECORDS == null || TOTAL_CREDITS == null ||
                TOTAL_DEBITS == null || recordManager == null || ALL_SINGLE_RECORDS == null || ALL_ITEMS == null) {

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
        TOTAL_CREDITS = 0.00;
        TOTAL_DEBITS = 0.00;
        TOTAL_DUES = 0.00;


        TOTAL_ITEMS = ALL_ITEMS.size();
        TOTAL_RECORDS = ALL_SINGLE_RECORDS.size();

        if (ALL_ITEMS.isEmpty()) {
            ItemModel cashModel = new ItemModel(1, "Cash", "Rs", 1.00);
            db.CreatePrice(cashModel);
            ALL_ITEMS = db.getAllPrices();
        }

        for (int i = 0; i < ALL_RECORDS.size(); i++) {
            TOTAL_CREDITS += ALL_RECORDS.get(i).getPrice();
        }

        for (int i = 0; i < ALL_PAID.size(); i++) {
            TOTAL_DEBITS += ALL_PAID.get(i).getPaid();
        }

        TOTAL_DUES = TOTAL_CREDITS - TOTAL_DEBITS;

        for (int i = 0; i < ALL_ITEMS.size(); i++) {
            CREDIT_BY_ITEMS.put(ALL_ITEMS.get(i).getItem_name(), getCreditForItem(ALL_ITEMS.get(i).getItem_name()));
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

    public static Double getPaid(String name) {
        Double totalPaid = 0.00;
        ArrayList<PaidModel> PaidModel = null;
        try {
            PaidModel = db.getAllIndPaid(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static ArrayList<PaidModel> getAllIndPaid(String Name) {
        try {
            ALL_SINGLE_PAID = db.getAllIndPaid(Name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ALL_SINGLE_PAID;
    }

    public static Double getCreditForItem(String Item) {
        Double totalCredit = 0.00;

        ArrayList<RecordModel> allIndDataForItem = new ArrayList<>();
        try {
            allIndDataForItem = db.getAllIndDataForItem(Item);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (allIndDataForItem != null) {
            for (int i = 0; i < allIndDataForItem.size(); i++) {
                totalCredit += allIndDataForItem.get(i).getPrice();
            }
        }
        return totalCredit;
    }
}


