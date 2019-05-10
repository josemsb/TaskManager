package com.appgrouplab.taskmanager.Manager.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenter;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenterImpl;
import com.appgrouplab.taskmanager.R;

public class DialogTaskNew extends DialogFragment implements ManagerActivityView {

    EditText etTitleTask,etDescriptionTask;
    TextView txtCancelTaskNew, txtSaveTaskNew;
    ManagerPresenter managerPresenter;
    public Integer idList;

    public interface OnSaveListener{
        void sendSaveTask();
    }
    public DialogTaskNew.OnSaveListener mOnSAveListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_task_new,container,false);

        etTitleTask = view.findViewById(R.id.etTitleTask);
        etDescriptionTask = view.findViewById(R.id.etDescriptionTask);
        txtCancelTaskNew = view.findViewById(R.id.txtCancelTaskNew);
        txtSaveTaskNew = view.findViewById(R.id.txtSaveTaskNew);

        managerPresenter = new ManagerPresenterImpl(this);


        txtCancelTaskNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        txtSaveTaskNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etTitleTask.getText().toString().equals("")) {
                    managerPresenter.addTask(idList,etTitleTask.getText().toString(),etDescriptionTask.getText().toString(),"","",0,"",1,v.getContext());
                    mOnSAveListener.sendSaveTask();
                }else
                    alert("Ingrese título antes de guardar.");
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnSAveListener = (OnSaveListener)getActivity();
        }catch (ClassCastException e){}
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(getContext());
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }
}
