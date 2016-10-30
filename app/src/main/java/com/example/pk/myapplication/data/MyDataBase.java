package com.example.pk.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pk on 10.09.2016.
 */
public class MyDataBase extends SQLiteOpenHelper {

    public final static String DB_NAME = "mydb";
    public final static String TABLE_NAME = "dictionary";
    public final static String ORIGINAL_COLUMN = "original";
    public final static String TRANSLATE_COLUMN = "translate";
    public final static int DB_VERSION = 1;

    public MyDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("
                + "id integer primary key autoincrement,"
                + ORIGINAL_COLUMN + " text,"
                + TRANSLATE_COLUMN + " text" + ");");
        Log.d("tag", "db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
