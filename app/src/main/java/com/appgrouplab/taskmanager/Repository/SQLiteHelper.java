package com.appgrouplab.taskmanager.Repository;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "DBTASK";
    private static int DATABASE_VERSION = 1;
    private final Context myContext;

    public SQLiteHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Log.d("SQLiteHelper","onCreate");
            db.execSQL("create table  list (id integer primary key autoincrement, title text, state integer)");
            db.execSQL("create table  task (id integer primary key autoincrement, idList integer, title text, description text, dateCreate text, dateReminder text, orderNumber integer, hourRemider text, state integer)");

        } catch (SQLiteException e) {
            Log.d("SQLiteHelper",e.getMessage());
            throw new Error(e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
