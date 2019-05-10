package com.appgrouplab.taskmanager;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.appgrouplab.taskmanager.Repository.SQLiteHelper;

public class ListTaskApplication extends Application {

    SQLiteOpenHelper sqLiteOpenHelper;

    public SQLiteOpenHelper getSqLiteOpenHelper() {
        return sqLiteOpenHelper;
    }

    public void setSqLiteOpenHelper(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sqLiteOpenHelper = new SQLiteHelper(this,null);

    }

}
