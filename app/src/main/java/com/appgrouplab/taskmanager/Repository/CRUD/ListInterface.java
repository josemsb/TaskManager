package com.appgrouplab.taskmanager.Repository.CRUD;

import android.content.Context;

import com.appgrouplab.taskmanager.Repository.Data.ListData;

import java.util.ArrayList;

public interface ListInterface {

    void add(String title,Integer state, Context context);
    int count(Context context);
    ArrayList<ListData> get(Context context);
    ListData get(Context context,String id);
}
