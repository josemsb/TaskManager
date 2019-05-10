package com.appgrouplab.taskmanager.SelectList.presenter;

import android.content.Context;

import com.appgrouplab.taskmanager.Repository.Data.ListData;
import com.appgrouplab.taskmanager.SelectList.interactor.SelectListInteractor;
import com.appgrouplab.taskmanager.SelectList.interactor.SelectListInteractorImpl;
import com.appgrouplab.taskmanager.SelectList.view.SelectListView;

import java.util.ArrayList;

public class SelectListPrensenterImpl implements  SelectListPresenter {

    private SelectListInteractor selectListInteractor;
    private SelectListView selectListView;

    public SelectListPrensenterImpl(SelectListView selectListView)
    {
        this.selectListView = selectListView;
        selectListInteractor = new SelectListInteractorImpl(this) {
        };
    }

    @Override
    public ArrayList<ListData>   getList(Context context) {return selectListInteractor.getList(context);}

    @Override
    public void addList(String title, Integer state, Context context) { selectListInteractor.addList(title,state,context);}


}
