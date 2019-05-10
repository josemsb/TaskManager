package com.appgrouplab.taskmanager.Manager.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.Repository.Data.TaskData;

import java.util.ArrayList;

public class AdapterTasks extends RecyclerView.Adapter<AdapterTasks.ViewHolder> implements View.OnClickListener {
    private ArrayList<TaskData> mDataSet;
    private View.OnClickListener listener;
    private Context context;

    public AdapterTasks(Context context){
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
                .inflate(R.layout.view_task,parent,false);

        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener=listener;

    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }


    @Override
    public void onBindViewHolder(AdapterTasks.ViewHolder holder, int position) {
        TaskData taskData = mDataSet.get(position);

        holder.itemView.setTag(taskData.getId().toString());
        holder.txtTaskName.setText(taskData.getTitle());

        if(taskData.getDescription().equals(""))
            holder.txtTaskDescription.setVisibility(View.GONE);
        else {
            holder.txtTaskDescription.setVisibility(View.GONE);
            holder.txtTaskDescription.setText(taskData.getDescription());
        }
        if(taskData.getDateReminder().equals("")) {
            holder.imgDateTaskNew.setVisibility(View.GONE);
            holder.txtDateTaskNew.setVisibility(View.GONE);
        }
        else
        {
            holder.imgDateTaskNew.setVisibility(View.VISIBLE);
            holder.txtDateTaskNew.setVisibility(View.VISIBLE);
            holder.txtDateTaskNew.setText(taskData.getDateReminder());
        }

        if(taskData.getHourRemider().equals(""))
        {
            holder.imgAlarmTaskNew.setVisibility(View.GONE);
        }
        else
        {
            holder.imgAlarmTaskNew.setVisibility(View.VISIBLE);
            holder.txtDateTaskNew.setText(taskData.getDateReminder() + " " + taskData.getHourRemider());
        }

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
        TextView txtTaskName,txtTaskDescription,txtDateTaskNew;
        ImageView imgAlarmTaskNew,imgDateTaskNew;

        private ViewHolder(View v){
            super(v);
            txtTaskName = v.findViewById(R.id.txtTaskName);
            txtTaskDescription = v.findViewById(R.id.txtTaskDescription);
            txtDateTaskNew = v.findViewById(R.id.txtDateTaskNew);
            imgAlarmTaskNew= v.findViewById(R.id.imgAlarmTaskNew);
            imgDateTaskNew= v.findViewById(R.id.imgDateTaskNew);
        }

    }

}
