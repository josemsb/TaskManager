package com.appgrouplab.taskmanager.SelectList.presenter;

import android.content.Context;

import com.appgrouplab.taskmanager.Repository.Data.ListData;

import java.util.ArrayList;

public interface SelectListPresenter {

    ArrayList<ListData>  getList(Context context);
    void addList(String title,Integer state, Context context);
}
