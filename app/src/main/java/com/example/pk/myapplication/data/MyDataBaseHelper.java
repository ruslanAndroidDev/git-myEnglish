package com.example.pk.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pk.myapplication.model.Word;

import java.util.ArrayList;

/**
 * Created by pk on 10.09.2016.
 */
public class MyDataBaseHelper {
    static MyDataBase myDb;
    static SQLiteDatabase db;
    private static ArrayList<Word> allWordDb;

    static String originalword;
    static String translateword;
    static int status;
    static Cursor cursor;

    static SQLiteDatabase getDatabase(Context context) {
        if (myDb == null) {
            myDb = new MyDataBase(context);
        }
        return myDb.getWritableDatabase();
    }

    public static void writetodb(Context context, String translateWord, String originalWord) {
        db = getDatabase(context);
        ContentValues values = new ContentValues();
        values.put(MyDataBase.ORIGINAL_COLUMN, originalWord);
        values.put(MyDataBase.TRANSLATE_COLUMN, translateWord);
        values.put(MyDataBase.STATUS_COLUMN, MyDataBase.STATUS_UNKNOWN);

        db.insert(MyDataBase.TABLE_NAME, null, values);
        db.close();
        Log.d("tag", "writetodb");
    }

    public static ArrayList<Word> loadWordwithDb(Context context) {
        db = getDatabase(context);
        allWordDb = new ArrayList<>();
        cursor = db.query(MyDataBase.TABLE_NAME, new String[]{MyDataBase.ORIGINAL_COLUMN, MyDataBase.TRANSLATE_COLUMN, MyDataBase.STATUS_COLUMN}, null, null, null, null, null);
        if (cursor.moveToLast()) {
            getOneWord();
            while (cursor.moveToPrevious()) {
                getOneWord();
            }
        }
        cursor.close();
        db.close();
        return allWordDb;
    }

    private static void getOneWord() {
        originalword = cursor.getString(0);
        translateword = cursor.getString(1);
        status = cursor.getInt(2);
        allWordDb.add(new Word(translateword, originalword, status));
    }

    public static void setStatus(Context context, int status, String originalword) {
        db = getDatabase(context);
        ContentValues cv = new ContentValues();
        cv.put(MyDataBase.STATUS_COLUMN, status);
        db.update(MyDataBase.TABLE_NAME, cv, MyDataBase.ORIGINAL_COLUMN + "=?", new String[]{originalword});
    }

    public static void deleteItem(int position, Context context) {
        String word = loadWordwithDb(context).get(position).getOriginalWord();
        db = getDatabase(context);
        db.delete(MyDataBase.TABLE_NAME, MyDataBase.ORIGINAL_COLUMN + "=?", new String[]{word});
    }
}
