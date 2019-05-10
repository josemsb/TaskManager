package com.appgrouplab.taskmanager.Repository.CRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.appgrouplab.taskmanager.ListTaskApplication;
import com.appgrouplab.taskmanager.Repository.Data.TaskData;

import java.util.ArrayList;

public class Task implements  TaskInterface {

    private ArrayList<TaskData> tasks = new ArrayList<>();

    public Task(){}


    @Override
    public void add(Integer idList, String title, String description, String dateCreate, String dateRemider, Integer orderNumber, String hourRemider, Integer state, Context context) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();

        try{
            ContentValues addContent = new ContentValues();
            addContent.put("idList",idList);
            addContent.put("title",title);
            addContent.put("description",description);
            addContent.put("dateCreate",dateCreate);
            addContent.put("dateReminder",dateRemider);
            addContent.put("orderNumber",orderNumber);
            addContent.put("hourRemider",hourRemider);
            addContent.put("state",state);

            db.insert("task",null,addContent);
            db.close();


        }
        catch (SQLiteException e) {
            db.close();
            throw new Error(e.getMessage());
        }
    }

    @Override
    public int count(Context context, String idList) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getReadableDatabase();

        Cursor recordSet = db.rawQuery("select count(id) from task where idList=?",new String[]{idList});
        recordSet.moveToNext();
        int rows = recordSet.getInt(0);
        db.close();

        return rows;

    }

    @Override
    public ArrayList<TaskData> getList(Context context, String idList) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();


        try{
            Cursor recordSet = db.rawQuery("select id,idlist,title,description,dateCreate,dateReminder,orderNumber,hourRemider,state from task where state=1 and idList=?",new String[]{idList});
            recordSet.moveToFirst();
            Log.d("Trace_View",recordSet.getString(0));
            do
            {
                TaskData taskData = new TaskData();
                taskData.setId(recordSet.getInt(0));
                taskData.setIdList(recordSet.getInt(1));
                taskData.setTitle(recordSet.getString(2));
                taskData.setDescription(recordSet.getString(3));
                taskData.setDateCreate(recordSet.getString(4));
                taskData.setDateReminder(recordSet.getString(5));
                taskData.setOrderNumber(recordSet.getInt(6));
                taskData.setHourRemider(recordSet.getString(7));
                taskData.setState(recordSet.getInt(8));
                tasks.add(taskData);
            }while(recordSet.moveToNext());

            recordSet.close();
            db.close();
            return tasks;

        }
        catch (SQLiteException e) {
            db.close();
            throw new Error(e.getMessage());
        }
    }



    @Override
    public TaskData get(Context context, String id) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();

        try{
            Cursor recordSet = db.rawQuery("select id,idlist,title,description,dateCreate,dateReminder,orderNumber,hourRemider,state from task where id=?", new String[]{id});

            if(recordSet.getCount()>0) {
                recordSet.moveToFirst();

                TaskData taskData = new TaskData();
                taskData.setId(recordSet.getInt(0));
                taskData.setIdList(recordSet.getInt(1));
                taskData.setTitle(recordSet.getString(2));
                taskData.setDescription(recordSet.getString(3));
                taskData.setDateCreate(recordSet.getString(4));
                taskData.setDateReminder(recordSet.getString(5));
                taskData.setOrderNumber(recordSet.getInt(6));
                taskData.setHourRemider(recordSet.getString(7));
                taskData.setState(recordSet.getInt(8));

                recordSet.close();
                db.close();
                return taskData;
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

    @Override
    public void delete(Context context, String id) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();

        try{
            db.delete("task","id=?", new String[]{id});
            db.close();
        }
        catch (SQLiteException e){
            db.close();
            throw new Error(e.getMessage());
        }
    }

    @Override
    public void terminate(Context context, String id) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();

        try{
            ContentValues addContent = new ContentValues();
            addContent.put("state",0);

            db.update("task",addContent,"id=?",new String[]{id});
            db.close();


        }
        catch (SQLiteException e) {
            db.close();
            throw new Error(e.getMessage());
        }
    }

    @Override
    public void edit(String id, String title, String description, String dateRemider, Integer orderNumber, String hourRemider, Integer state, Context context) {
        SQLiteDatabase db = ((ListTaskApplication) context.getApplicationContext()).getSqLiteOpenHelper().getWritableDatabase();

        try{
            ContentValues addContent = new ContentValues();
            addContent.put("title",title);
            addContent.put("description",description);
            //addContent.put("dateReminder",dateRemider);
            //addContent.put("orderNumber",orderNumber);
            //addContent.put("hourRemider",hourRemider);
            //addContent.put("state",state);

            db.update("task",addContent,"id=?",new String[]{id});
            db.close();


        }
        catch (SQLiteException e) {
            db.close();
            throw new Error(e.getMessage());
        }
    }


}
