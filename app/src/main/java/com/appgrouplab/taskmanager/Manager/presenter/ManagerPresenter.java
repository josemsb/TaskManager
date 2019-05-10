package com.appgrouplab.taskmanager.Manager.presenter;

import android.content.Context;

import com.appgrouplab.taskmanager.Repository.Data.ListData;
import com.appgrouplab.taskmanager.Repository.Data.TaskData;

import java.util.ArrayList;

public interface ManagerPresenter {

    /////////////////Functions List///////////////
    void addList(String title, Integer state, Context context);
    int countList(Context context);
    ArrayList<ListData>  getList(Context context);
    ArrayList<TaskData> getListTaskTerminate (Context context,String idList);
    ListData getList(Context context,String id);

    /////////////////Functions Task///////////////
    void addTask(Integer idList, String title,String description,String dateCreate,String dateRemider,Integer orderNumber,String hourRemider,Integer state, Context context);
    int countTask(Context context, String idList);
    int countTaskTerminate(Context context, String idList);
    void deleteTask(Context context, String id);
    void terminateTask(Context context, String id);
    void editTask(String id, String title,String description,String dateRemider,Integer orderNumber,String hourRemider,Integer state, Context context);
    ArrayList<TaskData> getListTask (Context context, String idList,String order);
    TaskData getTask(Context context,String id);
}
