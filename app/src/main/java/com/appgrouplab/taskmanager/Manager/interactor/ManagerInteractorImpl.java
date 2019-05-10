package com.appgrouplab.taskmanager.Manager.interactor;

import android.content.Context;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenter;
import com.appgrouplab.taskmanager.Repository.CRUD.List;
import com.appgrouplab.taskmanager.Repository.CRUD.ListInterface;
import com.appgrouplab.taskmanager.Repository.CRUD.Task;
import com.appgrouplab.taskmanager.Repository.CRUD.TaskInterface;
import com.appgrouplab.taskmanager.Repository.Data.*;

import java.util.ArrayList;

public class ManagerInteractorImpl implements ManagerInteractor {

    private ManagerPresenter managerPresenter;
    private ListInterface listInterface;
    private TaskInterface taskInterface;


    public ManagerInteractorImpl(ManagerPresenter managerPresenter)
    {

        this.managerPresenter = managerPresenter;
        listInterface = new List();
        taskInterface = new Task();
    }

    /////////////////Functions List///////////////
    @Override
    public void addList(String title, Integer state, Context context) {listInterface.add( title,  state,  context);}

    @Override
    public int countList(Context context) {
        return listInterface.count(context);
    }

    @Override
    public ArrayList<ListData> getList(Context context) {
       return listInterface.get(context);
    }

    @Override
    public ListData getList(Context context, String id) {
        return listInterface.get(context,id);
    }


    ////////////////Functions Task///////////////////
    @Override
    public void addTask(Integer idList, String title, String description, String dateCreate, String dateRemider, Integer orderNumber, String hourRemider, Integer state, Context context) {
        taskInterface.add( idList,  title,  description,  dateCreate,  dateRemider,  orderNumber,  hourRemider,  state,  context);
    }

    @Override
    public int countTask(Context context, String idList) {return taskInterface.count(context,idList);}

    @Override
    public int countTaskTerminate(Context context, String idList) {return taskInterface.countTerminate(context,idList);}

    @Override
    public ArrayList<TaskData> getListTask(Context context, String idList,String order) {return taskInterface.getList(context,idList,order);}

    @Override
    public ArrayList<TaskData> getListTaskTerminate(Context context, String idList) {return taskInterface.getListTerminate(context,idList);}

    @Override
    public TaskData getTask(Context context, String id) {
        return taskInterface.get(context,id);
    }

    @Override
    public void deleteTask(Context context, String id) {
        taskInterface.delete(context,id);
    }

    @Override
    public void terminateTask(Context context, String id) {
        taskInterface.terminate(context,id);
    }

    @Override
    public void editTask(String id, String title, String description, String dateRemider, Integer orderNumber, String hourRemider, Integer state, Context context) {
        taskInterface.edit( id,  title,  description,  dateRemider,  orderNumber,  hourRemider,  state,  context);
    }

}
