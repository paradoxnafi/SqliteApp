package com.paradoxnafi.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABSAE_NAME = "schedule.db";
    public static final String TABLE_NAME = "schedule_table";
    public static final String col_1_serial = "NUMBER";
    public static final String col_2_devieID = "DEVICE";
    public static final String col_3_mdeicineID = "MEDICINE";
    public static final String col_4_quantity = "QUANTITY";
    public static final String col_5_time = "TIME";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABSAE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, DEVICE INTEGER, MEDICINE TEXT, QUANTITY INTEGER, TIME STRING) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String device, String medicine, String quantity, String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(col_2_devieID, device);
        contentValues.put(col_3_mdeicineID, medicine);
        contentValues.put(col_4_quantity, quantity);
        contentValues.put(col_5_time, time);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) {
            return  false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return  res;
    }
}
