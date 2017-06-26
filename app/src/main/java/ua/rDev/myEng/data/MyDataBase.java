package ua.rDev.myEng.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pk on 10.09.2016.
 */
public class MyDataBase extends SQLiteOpenHelper {

    public final static String DB_NAME = "mydb";
    public final static String TABLE_NAME = "dictionary";
    public final static String ORIGINAL_COLUMN = "original";
    public final static String TRANSLATE_COLUMN = "translate";
    public final static String STATUS_COLUMN = "status";
    public final static int STATUS_UNKNOWN = 0;
    public final static int STATUS_STUDIED = 1;
    public final static int STATUS_NEED_TO_REPEAT = 2;
    public final static int STATUS_STUDING = 4;
    public final static int DB_VERSION = 1;

    public MyDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("
                + "id integer primary key autoincrement,"
                + ORIGINAL_COLUMN + " text,"
                + TRANSLATE_COLUMN + " text," + STATUS_COLUMN + " integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
