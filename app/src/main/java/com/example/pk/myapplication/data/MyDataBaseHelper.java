package com.example.pk.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

    public static void loadWordwithDb(Context context, DataLoadListener listener) {
        DBLoader dbLoader = new DBLoader(context, listener);
        dbLoader.execute();
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

    public static void deleteItem(final int position, final Context context) {
        loadWordwithDb(context, new DataLoadListener() {
            @Override
            public void onLoad(ArrayList<Word> words) {
                String word = words.get(position).getOriginalWord();
                db = getDatabase(context);
                db.delete(MyDataBase.TABLE_NAME, MyDataBase.ORIGINAL_COLUMN + "=?", new String[]{word});
            }
        });
    }

    static class DBLoader extends AsyncTask<Void, Void, ArrayList<Word>> {
        Context context;
        DataLoadListener listener;

        public DBLoader(Context context, DataLoadListener listener) {
            this.context = context;
            this.listener = listener;
        }

        @Override
        protected ArrayList<Word> doInBackground(Void... voids) {
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

        @Override
        protected void onPostExecute(ArrayList<Word> words) {
            listener.onLoad(words);
        }
    }

    public abstract static class DataLoadListener {
        public abstract void onLoad(ArrayList<Word> words);
    }
}
