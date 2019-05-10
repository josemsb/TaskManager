package com.appgrouplab.taskmanager.Repository.CRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.appgrouplab.taskmanager.ListTaskApplication;
import com.appgrouplab.taskmanager.Repository.Data.ListData;

import java.util.ArrayList;

public class List implements ListInterface {

    private ArrayList<ListData> lists = new ArrayList<>();

    public List(){}


    @Override
    public void add(String title, Integer state, Context context) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();

        try{
            ContentValues addContent = new ContentValues();
            addContent.put("title",title);
            addContent.put("state",state);

            db.insert("list",null,addContent);
            db.close();

        }
        catch (SQLiteException e) {
            db.close();
            throw new Error(e.getMessage());
        }

    }

    @Override
    public int count(Context context) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getReadableDatabase();

            Cursor recordSet = db.rawQuery("select count(id) from list",null);
            recordSet.moveToNext();
            int rows = recordSet.getInt(0);
            db.close();

            return rows;

    }

    @Override
    public  ArrayList<ListData> get(Context context) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();


        try{
            Cursor recordSet = db.rawQuery("select id,title,state from list",null);
            recordSet.moveToFirst();
           Log.d("Trace_View","get");
            do
            {
                ListData listData = new ListData();
                listData.setId(recordSet.getInt(0));
                listData.setTitle(recordSet.getString(1));
                listData.setState(recordSet.getInt(2));
                lists.add(listData);
            }while(recordSet.moveToNext());

            recordSet.close();
            db.close();
            return lists;

        }
        catch (SQLiteException e) {
            db.close();
            throw new Error(e.getMessage());
        }
    }

    @Override
    public ListData get(Context context, String id) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();

        try{
            Cursor recordSet = db.rawQuery("select id,title,state from list where id=?", new String[]{id});

            if(recordSet.getCount()>0) {
                recordSet.moveToFirst();

                ListData listData = new ListData();
                listData.setId(recordSet.getInt(0));
                listData.setTitle(recordSet.getString(1));
                listData.setState(recordSet.getInt(2));

                recordSet.close();
                db.close();
                return listData;
            }
            else
            {   recordSet.close();
                db.close();
                return null;
            }

        }
        catch (SQLiteException e){
            db.close();
            throw new Error(e.getMessage());
        }

    }


}
