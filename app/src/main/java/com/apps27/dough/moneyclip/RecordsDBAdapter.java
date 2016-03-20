package com.apps27.dough.moneyclip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ionutz on 3/17/2016.
 * Adapter for records
 */
public class RecordsDBAdapter {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TYPE = "type";

    private static final String TAG = "RecordsDBAdapter";
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDB;

    private static final String DATABASE_NAME = "DoughDB.db";
    private static final String TABLE_NAME = "records";
    private static final int DATABASE_VERSION = 2;

    private final Context mContext;

    private static final String DB_CREATE_STRING = "CREATE TABLE if not exists " +
            TABLE_NAME + " (" + COLUMN_ID + " integer PRIMARY KEY autoincrement," +
            COLUMN_DATE + "," + COLUMN_AMOUNT + "," + COLUMN_TYPE + ");";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_STRING);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public RecordsDBAdapter(Context ctx) {
        this.mContext = ctx;
    }

    public RecordsDBAdapter open() {
        mDBHelper = new DatabaseHelper(mContext);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDBHelper != null) {
            mDBHelper.close();
        }
    }

    public long createRecord(String date, String amount, String type) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_DATE, date);
        initialValues.put(COLUMN_AMOUNT, amount);
        initialValues.put(COLUMN_TYPE, type);

        return mDB.insert(TABLE_NAME, null, initialValues);
    }

    public boolean deleteAllRecords() {
        int doneDelete = 0;
        doneDelete = mDB.delete(TABLE_NAME, null, null);
        return doneDelete > 0;
    }

    public Cursor readAllRecords() {
        Cursor mCursor = mDB.query(TABLE_NAME, new String[] {
                        COLUMN_ID, COLUMN_DATE, COLUMN_AMOUNT, COLUMN_TYPE},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public void insertSomeRecords() {
        createRecord("20/12/2012 12:00", "2000.01", "Plata 1");
        createRecord("21/12/2012 12:01", "2000.02", "Plata 2");
        createRecord("22/12/2012 12:02", "2000.03", "Plata 3");
        createRecord("23/12/2012 12:03", "2000.04", "Plata 4");
        createRecord("24/12/2012 12:04", "2000.05", "Plata 5");
        createRecord("25/12/2012 12:05", "2000.06", "Plata 6");
        createRecord("26/12/2012 12:06", "2000.07", "Plata 7");
        createRecord("27/12/2012 12:07", "2000.08", "Plata 8");
        createRecord("28/12/2012 12:08", "2000.09", "Plata 9");
        createRecord("29/12/2012 12:09", "2000.11", "Plata 10");
    }
}

