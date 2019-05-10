package com.appgrouplab.taskmanager.SelectList.presenter;

import android.content.Context;
import com.appgrouplab.taskmanager.Repository.Data.ListData;
import java.util.ArrayList;

public interface SelectListPresenter {

    void addList(String title,Integer state, Context context);
    ArrayList<ListData>  getList(Context context);
}
