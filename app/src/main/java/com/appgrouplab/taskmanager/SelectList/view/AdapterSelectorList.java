package com.appgrouplab.taskmanager.SelectList.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.Repository.Data.ListData;

import java.util.ArrayList;

public class AdapterSelectorList extends RecyclerView.Adapter<AdapterSelectorList.ViewHolder> implements View.OnClickListener {

    private ArrayList<ListData> mDataSet;
    private View.OnClickListener listener;
    private Context context;

    public AdapterSelectorList(Context context){
        this.context = context;
        mDataSet = new ArrayList<>();
    }

    public void setDataset(ArrayList<ListData> dataset){
        mDataSet = dataset;
        notifyItemRangeRemoved(0,mDataSet.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list,parent,false);

        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener=listener;

    }

    @Override
    public void onBindViewHolder(AdapterSelectorList.ViewHolder holder, int position) {
        ListData listData = mDataSet.get(position);

        holder.itemView.setTag(listData.getId().toString());
        holder.txtList.setText(listData.getTitle());
    }


    public void limpiar(){
        notifyItemRangeRemoved(0,mDataSet.size());
        mDataSet.clear();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtList;
        ImageView imgSelector;

        private ViewHolder(View v){
            super(v);
            txtList = v.findViewById(R.id.txtList);
            imgSelector= v.findViewById(R.id.imgSelector);
        }

    }

}
