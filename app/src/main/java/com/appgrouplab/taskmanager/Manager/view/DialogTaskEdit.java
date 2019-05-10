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
import android.widget.ImageButton;
import android.widget.TextView;

import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenter;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenterImpl;
import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.Repository.Data.TaskData;

public class DialogTaskEdit extends DialogFragment implements ManagerActivityView {


    EditText etTitleTaskEdit,etDescriptionTaskEdit;
    ImageButton ibCompleteTask,ibDeleteTask;
    TextView txtCancelTasEdit,txtEditTaskEdit;
    ManagerPresenter managerPresenter;
    public Integer id;

    public interface OnEditListener{
        void sendEditTask();
    }
    public DialogTaskEdit.OnEditListener mOnEditListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_task_edit,container,false);

        getDialog().setTitle(R.string.titleTaskEdit);

        etTitleTaskEdit = view.findViewById(R.id.etTitleTaskEdit);
        etDescriptionTaskEdit = view.findViewById(R.id.etDescriptionTaskEdit);
        txtCancelTasEdit = view.findViewById(R.id.txtCancelTasEdit);
        txtEditTaskEdit = view.findViewById(R.id.txtEditTaskEdit);
        ibDeleteTask = view.findViewById(R.id.ibDeleteTask);
        ibCompleteTask = view.findViewById(R.id.ibCompleteTask);

        managerPresenter = new ManagerPresenterImpl(this);

        eventClick();
        viewInfoTask();

        return view;
    }

    private void viewInfoTask() {
        TaskData taskData = managerPresenter.getTask(getContext(),String.valueOf(id));
        if(taskData!=null)
        {
            etTitleTaskEdit.setText(taskData.getTitle());
            etDescriptionTaskEdit.setText(taskData.getDescription());
        }
    }

    private void eventClick() {
        txtCancelTasEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        ibDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerPresenter.deleteTask(v.getContext(),String.valueOf(id));
                mOnEditListener.sendEditTask();
                getDialog().dismiss();
            }
        });

        ibCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerPresenter.terminateTask(v.getContext(),String.valueOf(id));
                mOnEditListener.sendEditTask();
                getDialog().dismiss();
            }
        });



        txtEditTaskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etTitleTaskEdit.getText().toString().equals("")) {
                    managerPresenter.editTask(String.valueOf(id),etTitleTaskEdit.getText().toString(),etDescriptionTaskEdit.getText().toString(),"",0,"",1,v.getContext());
                    mOnEditListener.sendEditTask();
                }else
                    alert("Ingrese título antes de editar.");
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnEditListener = (DialogTaskEdit.OnEditListener)getActivity();
        }catch (ClassCastException e){}
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(getContext());
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }
}
