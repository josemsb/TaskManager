package com.appgrouplab.taskmanager.SelectList.interactor;

import android.content.Context;

import com.appgrouplab.taskmanager.Repository.CRUD.List;
import com.appgrouplab.taskmanager.Repository.CRUD.ListInterface;
import com.appgrouplab.taskmanager.Repository.Data.ListData;
import com.appgrouplab.taskmanager.SelectList.presenter.SelectListPresenter;

import java.util.ArrayList;

public class SelectListInteractorImpl implements SelectListInteractor {

    private ListInterface listInterface;
    private SelectListPresenter selectListPresenter;

    public SelectListInteractorImpl(SelectListPresenter selectListPresenter )
    {

        this.selectListPresenter = selectListPresenter;
        listInterface = new List();
    }

    @Override
    public ArrayList<ListData>  getList(Context context) {return listInterface.get(context);}

    @Override
    public void addList(String title, Integer state, Context context) {listInterface.add(title,state,context);}

}
