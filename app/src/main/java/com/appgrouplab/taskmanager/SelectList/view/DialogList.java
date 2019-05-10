package com.appgrouplab.taskmanager.SelectList.view;

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
import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.SelectList.presenter.SelectListPrensenterImpl;
import com.appgrouplab.taskmanager.SelectList.presenter.SelectListPresenter;

public class DialogList extends DialogFragment implements SelectListView {


    EditText etTitle;
    TextView txtCancel, txtSave;
    SelectListPresenter selectListPresenter;

    public interface OnSaveListener{
        void sendSave();
    }
    public OnSaveListener mOnSAveListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_list,container,false);

        etTitle = view.findViewById(R.id.etTitle);
        txtCancel = view.findViewById(R.id.txtCancel);
        txtSave = view.findViewById(R.id.txtSave);

        selectListPresenter = new SelectListPrensenterImpl(this);


        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etTitle.getText().toString().equals("")) {
                    selectListPresenter.addList(etTitle.getText().toString(), 1, v.getContext());
                    mOnSAveListener.sendSave();
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
