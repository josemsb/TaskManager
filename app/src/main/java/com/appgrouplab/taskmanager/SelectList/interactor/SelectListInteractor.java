package com.appgrouplab.taskmanager.SelectList.interactor;

import android.content.Context;

import com.appgrouplab.taskmanager.Repository.Data.ListData;

import java.util.ArrayList;

public interface SelectListInteractor {

    void addList(String title,Integer state, Context context);
    ArrayList<ListData>  getList( Context context);
}
