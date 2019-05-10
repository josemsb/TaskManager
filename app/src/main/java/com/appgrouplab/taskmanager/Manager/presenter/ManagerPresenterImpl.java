package com.appgrouplab.taskmanager.Manager.presenter;

import android.content.Context;

import com.appgrouplab.taskmanager.Manager.interactor.ManagerInteractor;
import com.appgrouplab.taskmanager.Manager.interactor.ManagerInteractorImpl;
import com.appgrouplab.taskmanager.Manager.view.ManagerActivityView;
import com.appgrouplab.taskmanager.Repository.Data.ListData;
import com.appgrouplab.taskmanager.Repository.Data.TaskData;

import java.util.ArrayList;

public class ManagerPresenterImpl implements ManagerPresenter {

    private ManagerInteractor managerInteractor;
    private ManagerActivityView managerActivityView;

    public ManagerPresenterImpl(ManagerActivityView managerActivityView)
    {
        this.managerActivityView = managerActivityView;
        managerInteractor = new ManagerInteractorImpl(this);
    }


    /////////////////Functions List///////////////
    @Override
    public void addList(String title, Integer state, Context context) {managerInteractor.addList(title,state,context);}

    @Override
    public int countList(Context context) {
        return managerInteractor.countList(context);
    }

    @Override
    public ArrayList<ListData>  getList(Context context) {return managerInteractor.getList(context);}

    @Override
    public ListData getList(Context context, String id) {return managerInteractor.getList(context,id);}


    /////////////////Functions Task///////////////
    @Override
    public ArrayList<TaskData> getListTaskTerminate(Context context, String idList) {return managerInteractor.getListTaskTerminate(context,idList);}

    @Override
    public void addTask(Integer idList, String title, String description, String dateCreate, String dateRemider, Integer orderNumber, String hourRemider, Integer state, Context context) {
        managerInteractor.addTask( idList,  title,  description,  dateCreate,  dateRemider,  orderNumber,  hourRemider,  state,  context);
    }

    @Override
    public int countTask(Context context, String idList) {return managerInteractor.countTask(context,idList);}

    @Override
    public int countTaskTerminate(Context context, String idList) {return managerInteractor.countTaskTerminate(context,idList);}

    @Override
    public ArrayList<TaskData> getListTask(Context context, String idList,String order) {return managerInteractor.getListTask(context,idList,order);}

    @Override
    public TaskData getTask(Context context, String id) {return managerInteractor.getTask(context,id);}

    @Override
    public void deleteTask(Context context, String id) {
        managerInteractor.deleteTask(context,id);
    }

    @Override
    public void terminateTask(Context context, String id) {managerInteractor.terminateTask(context,id);}

    @Override
    public void editTask(String id, String title, String description, String dateRemider, Integer orderNumber, String hourRemider, Integer state, Context context) {
        managerInteractor.editTask( id,  title,  description,  dateRemider,  orderNumber,  hourRemider,  state,  context);
    }


}
