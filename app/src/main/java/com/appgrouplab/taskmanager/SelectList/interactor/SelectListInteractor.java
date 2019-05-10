package com.appgrouplab.taskmanager.SelectList.interactor;

import android.content.Context;

import com.appgrouplab.taskmanager.Repository.Data.ListData;

import java.util.ArrayList;

public interface SelectListInteractor {
    ArrayList<ListData>  getList( Context context);
    void addList(String title,Integer state, Context context);

}
