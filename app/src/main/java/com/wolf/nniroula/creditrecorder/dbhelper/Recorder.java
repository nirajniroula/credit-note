package com.wolf.nniroula.creditrecorder.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wolf.nniroula.creditrecorder.model.ItemModel;
import com.wolf.nniroula.creditrecorder.model.PaidModel;
import com.wolf.nniroula.creditrecorder.model.RecordModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Niraj on 1/30/2015.
 */
public class Recorder extends SQLiteOpenHelper {

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "_name";
    public static final String KEY_WT = "_wt";
    public static final String KEY_ITEM = "_item";
    public static final String KEY_UNIT = "_unit";
    public static final String KEY_PRICE = "_price";
    public static final String KEY_DATE = "_date";

    public static final String KEY_IMAGE = "_image";

    public static final String KEY_PAID_DATE = "_date";
    public static final String KEY_PAID = "_paid";

    public static final String KEY_RICE = "Rice";
    public static final String KEY_WHEAT = "Wheat";
    public static final String KEY_MUSTARD = "Mustard";
    public static final String KEY_FLAX = "Flax";

    private static final String DATABASE_NAME = "MillRecord";
    private ItemModel itemModel;

    private static final String TABLE_ENTRY = "EntryRecord";
    private static final String TABLE_PRICE = "PriceRecord";
    private static final String TABLE_PAID = "PaidRecord";
    private static final String TABLE_USER = "UserRecord";

    public static Double TOTAL_CREDITS = 0.00;
    public static Double TOTAL_DEBITS = 0.00;
    public static int TOTAL_USERS = 0;

    private static final int DATABASE_VERSION = 1;
    private static Recorder db;

    private Context ourContext;
    private static final String CREATE_TABLE_RECORD = "CREATE TABLE " + TABLE_ENTRY +
            " (" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_WT + " REAL NOT NULL, "
            + KEY_ITEM + " TEXT, "
            + KEY_UNIT + " TEXT, "
            + KEY_PRICE + " REAL NOT NULL, "
            + KEY_DATE + " TEXT" + ")";

    private static final String CREATE_TABLE_PAID = "CREATE TABLE " + TABLE_PAID +
            " (" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_PAID + " REAL, "
            + KEY_PAID_DATE + " TEXT" + ")";

    private static final String CREATE_TABLE_PRICE = "CREATE TABLE " + TABLE_PRICE +
            " (" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ITEM + " TEXT NOT NULL, "
            + KEY_UNIT + " TEXT NOT NULL, "
            + KEY_PRICE + " REAL NOT NULL, "
            + KEY_IMAGE + " TEXT" + ")";


    public Recorder(Context context) throws IOException {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static Recorder getInstance(Context context)
            throws IOException {
        if (db == null) db = new Recorder(context);
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PRICE);
        db.execSQL(CREATE_TABLE_RECORD);
        db.execSQL(CREATE_TABLE_PAID);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ENTRY);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PRICE);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PAID);
        onCreate(db);
    }


    public long CreateEntry(String Name, Double Wt, String item, String Unit, Double Price, String Date) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, Name);
        cv.put(KEY_WT, Wt);
        cv.put(KEY_ITEM, item);
        cv.put(KEY_DATE, Date);
        cv.put(KEY_UNIT, Unit);
        cv.put(KEY_PRICE, Price);

        long res = db.insert(TABLE_ENTRY, null, cv);
        Log.d("tag", "Insert: " + res);
        return res;
    }

    public long CreatePaid(String Name, Double Paid, String Date) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, Name);
        cv.put(KEY_PAID, Paid);
        cv.put(KEY_PAID_DATE, Date);

        long res = db.insert(TABLE_PAID, null, cv);
        Log.d("tag", "Insert: " + res);
        db.close();
        return res;
    }

    public long CreatePrice(ItemModel itemModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_ITEM, itemModel.getItem_name());
        cv.put(KEY_PRICE, itemModel.getItem_price());
        cv.put(KEY_UNIT, itemModel.getItem_unit());

        long res = db.insert(TABLE_PRICE, null, cv);
        db.close();
        Log.d("tag", "Insert: " + res);
        return res;
    }

    public long UpdatePrice(ItemModel itemModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_ITEM, itemModel.getItem_name());
        cv.put(KEY_PRICE, itemModel.getItem_price());
        cv.put(KEY_UNIT, itemModel.getItem_unit());

        return db.update(TABLE_PRICE, cv, KEY_ID + "=" + itemModel.getId(), null);
    }

    public long UpdateEntrysItem(String Old, String New) throws IOException, SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ITEM, New);
        return db.update(TABLE_ENTRY, cv, KEY_ITEM + "=" + Old, null);
    }

    public ArrayList<ItemModel> getAllPrices() {
        ArrayList<ItemModel> ItemList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRICE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Fetch the data from first row
        if (cursor.moveToFirst()) {

            do {
                ItemModel itemModel = new ItemModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));

                ItemList.add(itemModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ItemList;
    }

    public ItemModel getPrice(int id) {
        //ItemModel itemModel;
        String selectQuery = "SELECT  * FROM " + TABLE_PRICE + " WHERE " + KEY_ID + id;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
        ItemModel itemModel = new ItemModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
//            }
//
        db.close();

        return itemModel;
    }

    public void delItem(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause =
                String.format("%s = %s;",
                        KEY_ID,
                        Id);
        String[] whereClauseargs = null;
        db.delete(TABLE_PRICE, whereClause, whereClauseargs);

        db.close();
        Log.e("DB", "Deleted a row from Itemss.");
    }

    public ArrayList<RecordModel> getAllEntries() {
        ArrayList<RecordModel> ContactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Fetch the data from first row
        if (cursor.moveToFirst()) {

            do {
                RecordModel contMod = new RecordModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6));
                ContactList.add(contMod);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ContactList;
    }

    public ArrayList<PaidModel> getAllPaid() {
        ArrayList<PaidModel> PaidList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PAID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Fetch the data from first row
        if (cursor.moveToFirst()) {

            do {
                PaidModel contMod = new PaidModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
                Log.v("Last", "update_date" + cursor.getString(1));
                PaidList.add(contMod);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return PaidList;
    }


    public ArrayList<RecordModel> getAllIndData(String UserName) {
        ArrayList<RecordModel> RecordList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY + " WHERE " + KEY_NAME + "=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{UserName});
        // Fetch the data from first row
        if (cursor.moveToFirst()) {

            do {
                RecordModel contMod = new RecordModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6));

                RecordList.add(contMod);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return RecordList;
    }

    public ArrayList<RecordModel> getAllIndDataForItem(String Item) throws IOException {
        ArrayList<RecordModel> RecordList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY + " WHERE " + KEY_ITEM + "=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{Item});
        // Fetch the data from first row
        if (cursor.moveToFirst()) {

            do {
                RecordModel contMod = new RecordModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6));

                RecordList.add(contMod);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return RecordList;
    }

    public ArrayList<PaidModel> getAllIndPaid(String UserName) throws IOException {
        ArrayList<PaidModel> PaidList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PAID + " WHERE " + KEY_NAME + "=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{UserName});
        // Fetch the data from first row
        if (cursor.moveToFirst()) {

            do {
                PaidModel contMod = new PaidModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
                Log.v("Last", "update_date" + cursor.getString(1));

                PaidList.add(contMod);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return PaidList;
    }

    public void updateUserName(String oldName, String newName) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        try {
            values.put(KEY_NAME, newName);

            db.update(TABLE_ENTRY, values, KEY_NAME + "=" + oldName, null);
            db.update(TABLE_PAID, values, KEY_NAME + "=" + oldName, null);
            Log.v("Last", "update_no");
            //db.close();
        } finally {
            db.close();
        }
    }


    //Delete one item from Table
    public void delEntry(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause =
                String.format("%s = %s;",
                        KEY_ID,
                        Id);
        String[] whereClauseargs = null;
        db.delete(TABLE_ENTRY, whereClause, whereClauseargs);

        db.close();
        Log.v("DB", "Deleted a row from Records.");
    }

    //Deletes Individual's Single Paid Record.
    public void delPaidEntry(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause =
                String.format("%s = %s;",
                        KEY_ID,
                        Id);
        String[] whereClauseargs = null;
        db.delete(TABLE_PAID, whereClause, whereClauseargs);

        db.close();
        Log.v("DB", "Deleted a row from Paid Table");
    }

    public boolean verification(String _username) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = -1;
        Cursor c = null;
        try {
            String query = "SELECT COUNT(*) FROM "
                    + TABLE_ENTRY + " WHERE " + KEY_NAME + " = ?";
            c = db.rawQuery(query, new String[]{_username});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            return count > 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public boolean verifyItem(String _item) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = -1;
        Cursor c = null;
        try {
            String query = "SELECT COUNT(*) FROM "
                    + TABLE_PRICE + " WHERE " + KEY_ITEM + " = ?";
            c = db.rawQuery(query, new String[]{_item});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            return count > 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    //Deletes all rows from entries with the given name.
    public void delEntries(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ENTRY, KEY_NAME + "=?", new String[]{name});

        db.close();
        Log.v("DB", "Deleted all rows from entries with the given name.");
    }

    //Deletes all rows from paid with the given name.
    public void delPaidEntries(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PAID, KEY_NAME + "=?", new String[]{name});
        db.close();
        Log.v("DB", "Deleted rows from favourite.");
    }

}
