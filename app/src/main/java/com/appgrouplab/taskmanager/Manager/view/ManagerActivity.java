package com.appgrouplab.taskmanager.Manager.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenter;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenterImpl;
import com.appgrouplab.taskmanager.Repository.Data.ListData;
import com.appgrouplab.taskmanager.SelectList.view.SelectList;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity implements ManagerActivityView, DialogTaskNew.OnSaveListener, DialogTaskEdit.OnEditListener {

    ManagerPresenter managerPresenter;
    TextView txtListTask,txtTaskCompledd;
    CardView cvListSpinner;
    FloatingActionButton fbAddTask;
    AdapterTasks mAdapter;
    AdapterTaskTerminate mAdapterTerminate;
    RecyclerView rvTask,rvTaskCompleted;
    LinearLayout lyFondo,lvTaskCompleted;
    CheckBox chkOrder,chkNatural;
    String order="natural";

    private static  final int LIST_SEND=900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        //iniciatilize controls
        txtListTask = findViewById(R.id.txtListTask);
        txtTaskCompledd = findViewById(R.id.txtTaskCompledd);
        cvListSpinner = findViewById(R.id.cvListSpinner);
        fbAddTask = findViewById(R.id.fbAddTask);
        rvTask = findViewById(R.id.rvTask);
        rvTaskCompleted= findViewById(R.id.rvTaskCompleted);
        lyFondo = findViewById(R.id.lyFondo);
        lvTaskCompleted= findViewById(R.id.lvTaskCompleted);
        chkOrder =  findViewById(R.id.chkOrder);
        chkNatural =  findViewById(R.id.chkNatural);

        //Inicialize Implements
        managerPresenter = new ManagerPresenterImpl(this);

        //Inicialize RecyclerView
        rvTask.setHasFixedSize(false);
        rvTask.setNestedScrollingEnabled(false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTask.setLayoutManager(layoutManager);
        mAdapter = new AdapterTasks(this);
        rvTask.setAdapter(mAdapter);

        rvTaskCompleted.setHasFixedSize(false);
        rvTaskCompleted.setNestedScrollingEnabled(false);
        final LinearLayoutManager layoutManager01 = new LinearLayoutManager(this);
        rvTaskCompleted.setLayoutManager(layoutManager01);
        mAdapterTerminate = new AdapterTaskTerminate(this);
        rvTaskCompleted.setAdapter(mAdapterTerminate);

        eventClick();
        initializeView();


    }

    private void initializeView() {
        if(managerPresenter.countList(this)>0) {
            ArrayList<ListData> lists = managerPresenter.getList(this);
            txtListTask.setText(lists.get(0).getTitle());
            txtListTask.setTag(lists.get(0).getId());
            viewTask();
        }
        else {
            managerPresenter.addList("Mis tareas", 1, this);
            ArrayList<ListData> lists = managerPresenter.getList(this);
            txtListTask.setText(lists.get(0).getTitle());
            txtListTask.setTag(lists.get(0).getId());
            lvTaskCompleted.setVisibility(View.GONE);
            viewTask();
        }
    }

    private void eventClick() {
        fbAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTaskNew dialog = new DialogTaskNew();
                dialog.idList = Integer.valueOf(txtListTask.getTag().toString());
                dialog.show(getSupportFragmentManager(),"DialogTaskNew");
            }
        });

        lvTaskCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mAdapterTerminate.limpiar();
               mAdapterTerminate.setDataset(managerPresenter.getListTaskTerminate(v.getContext(),txtListTask.getTag().toString()));
               rvTaskCompleted.setVisibility(View.VISIBLE);
            }
        });

        cvListSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SelectList.class);
                startActivityForResult(i,LIST_SEND);
            }
        });

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogTaskEdit dialog = new DialogTaskEdit();
                dialog.id = Integer.valueOf(view.getTag().toString());
                dialog.show(getSupportFragmentManager(),"DialogTaskEdit");

            }
        });
         chkOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if(isChecked) {
                     order = "fecha";
                     chkNatural.setChecked(false);
                     viewTask();
                 }

             }
         });
        chkNatural.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    order = "natural";
                    chkOrder.setChecked(false);
                    viewTask();
                }

            }
        });

    }

    private void viewTask() {
        if(managerPresenter.countTask(this,txtListTask.getTag().toString())>0)
        {   lyFondo.setVisibility(View.GONE);
            mAdapter.setDataset(managerPresenter.getListTask(this,txtListTask.getTag().toString(),order));

            int taskComplete = managerPresenter.countTaskTerminate(this,txtListTask.getTag().toString());

            if(taskComplete>0)
            {
                lvTaskCompleted.setVisibility(View.VISIBLE);
                txtTaskCompledd.setText("Tareas Completadas (" + String.valueOf(taskComplete) + ")");
            }
            else {
                lvTaskCompleted.setVisibility(View.GONE);
                rvTaskCompleted.setVisibility(View.GONE);
            }
        }
        else {
            lyFondo.setVisibility(View.VISIBLE);
            lvTaskCompleted.setVisibility(View.GONE);
            rvTaskCompleted.setVisibility(View.GONE);
        }
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==LIST_SEND && resultCode == RESULT_OK)
        {
            ListData listData = managerPresenter.getList(this,data.getDataString());
           if(listData != null) {
               txtListTask.setText(listData.getTitle());
               txtListTask.setTag(listData.getId());
               sendSaveTask();
           }
        }
    }

    @Override
    public void sendSaveTask() {
        mAdapter.limpiar();
        viewTask();
    }

    @Override
    public void sendEditTask() {
        mAdapter.limpiar();
        viewTask();
    }
}
