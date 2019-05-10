package com.appgrouplab.taskmanager.Repository.CRUD;

import android.content.Context;

import com.appgrouplab.taskmanager.Repository.Data.TaskData;

import java.util.ArrayList;

public interface TaskInterface {

    void add(Integer idList, String title,String description,String dateCreate,String dateRemider,Integer orderNumber,String hourRemider,Integer state, Context context);
    int count(Context context, String idList);
    ArrayList<TaskData> getList (Context context,String idList);
    TaskData get(Context context,String id);
    void delete(Context context, String id);
    void terminate(Context context, String id);
    void edit(String id, String title,String description,String dateRemider,Integer orderNumber,String hourRemider,Integer state, Context context);
}
