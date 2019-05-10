package com.appgrouplab.taskmanager.Manager.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.Repository.Data.TaskData;

import java.util.ArrayList;

public class AdapterTaskTerminate extends RecyclerView.Adapter<AdapterTaskTerminate.ViewHolder>  {

    private ArrayList<TaskData> mDataSet;
    private Context context;

    public AdapterTaskTerminate(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<TaskData> dataset){
        mDataSet = dataset;
        notifyItemRangeRemoved(0,mDataSet.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_task_terminate,parent,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(AdapterTaskTerminate.ViewHolder holder, int position) {
        TaskData taskData = mDataSet.get(position);

        holder.itemView.setTag(taskData.getId().toString());
        holder.txtTaskName.setText(taskData.getTitle());




    }

    public void limpiar(){
        notifyItemRangeRemoved(0,mDataSet.size());
        mDataSet.clear();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtTaskName;

        private ViewHolder(View v){
            super(v);
            txtTaskName = v.findViewById(R.id.txtTaskNameTerminate);
        }

    }
}
