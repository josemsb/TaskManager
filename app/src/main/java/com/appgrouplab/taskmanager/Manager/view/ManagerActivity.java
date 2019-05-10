package com.appgrouplab.taskmanager.Manager.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenter;
import com.appgrouplab.taskmanager.Manager.presenter.ManagerPresenterImpl;
import com.appgrouplab.taskmanager.Repository.Data.ListData;
import com.appgrouplab.taskmanager.SelectList.view.SelectList;

public class ManagerActivity extends AppCompatActivity implements ManagerActivityView, DialogTaskNew.OnSaveListener, DialogTaskEdit.OnEditListener {

    ManagerPresenter managerPresenter;
    TextView txtListTask;
    CardView cvListSpinner;
    FloatingActionButton fbAddTask;
    AdapterTasks mAdapter;
    RecyclerView rvTask;
    LinearLayout lyFondo;

    private static  final int LIST_SEND=900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        txtListTask = findViewById(R.id.txtListTask);
        cvListSpinner = findViewById(R.id.cvListSpinner);
        fbAddTask = findViewById(R.id.fbAddTask);
        rvTask = findViewById(R.id.rvTask);
        lyFondo = findViewById(R.id.lyFondo);

        fbAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTaskNew dialog = new DialogTaskNew();
                dialog.idList = Integer.valueOf(txtListTask.getTag().toString());
                dialog.show(getSupportFragmentManager(),"DialogTaskNew");
            }
        });

        cvListSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SelectList.class);
                startActivityForResult(i,LIST_SEND);
            }
        });


        managerPresenter = new ManagerPresenterImpl(this);

        rvTask.setHasFixedSize(false);
        rvTask.setNestedScrollingEnabled(false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTask.setLayoutManager(layoutManager);
        mAdapter = new AdapterTasks(this);
        rvTask.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogTaskEdit dialog = new DialogTaskEdit();
                dialog.id = Integer.valueOf(view.getTag().toString());
                dialog.show(getSupportFragmentManager(),"DialogTaskEdit");

            }
        });


        if(managerPresenter.countList(this)>0) {
            txtListTask.setText(managerPresenter.getList(this).get(0).getTitle());
            txtListTask.setTag(managerPresenter.getList(this).get(0).getId());

            if(managerPresenter.countTask(this,txtListTask.getTag().toString())>0)
            {   lyFondo.setVisibility(View.GONE);
                mAdapter.setDataset(managerPresenter.getListTask(this,txtListTask.getTag().toString()));
            }
            else
                lyFondo.setVisibility(View.VISIBLE);


        }
        else
            managerPresenter.addList("Mis tareas", 1, this);

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
               //mAdapter.setDataset(managerPresenter.getListTask(this,txtListTask.getTag().toString()));
           }
        }


    }

    @Override
    public void sendSaveTask() {
        mAdapter.limpiar();
        if(managerPresenter.countTask(this,txtListTask.getTag().toString())>0)
        {   lyFondo.setVisibility(View.GONE);
            mAdapter.setDataset(managerPresenter.getListTask(this,txtListTask.getTag().toString()));
        }
        else
            lyFondo.setVisibility(View.VISIBLE);

    }

    @Override
    public void sendEditTask() {
        mAdapter.limpiar();
        if(managerPresenter.countTask(this,txtListTask.getTag().toString())>0)
        {   lyFondo.setVisibility(View.GONE);
            mAdapter.setDataset(managerPresenter.getListTask(this,txtListTask.getTag().toString()));
        }
        else
            lyFondo.setVisibility(View.VISIBLE);

    }
}
