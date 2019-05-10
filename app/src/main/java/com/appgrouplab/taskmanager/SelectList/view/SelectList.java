package com.appgrouplab.taskmanager.SelectList.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.appgrouplab.taskmanager.R;
import com.appgrouplab.taskmanager.SelectList.presenter.SelectListPrensenterImpl;
import com.appgrouplab.taskmanager.SelectList.presenter.SelectListPresenter;

public class SelectList extends AppCompatActivity implements  SelectListView, DialogList.OnSaveListener {


    ImageButton imgBackListSelector;
    AdapterSelectorList mAdapter;
    RecyclerView rvList;
    LinearLayout lvAddList;
    SelectListPresenter selectListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list);

        imgBackListSelector = findViewById(R.id.imgBackListSelector);
        lvAddList = findViewById(R.id.lvAddList);
        rvList = findViewById(R.id.rvList);

        selectListPresenter = new SelectListPrensenterImpl(this);

        rvList.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        mAdapter = new AdapterSelectorList(this);
        rvList.setAdapter(mAdapter);

        mAdapter.setDataset(selectListPresenter.getList(this));

        eventClick();

    }

    private void eventClick() {
        imgBackListSelector.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                Intent data = new Intent();
                data.setData(Uri.parse("regresar"));
                setResult(RESULT_CANCELED,data);
                finish();

            }
        });

        lvAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogList dialog = new DialogList();
                dialog.show(getSupportFragmentManager(),"DialogList");
            }
        });

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent data = new Intent();
                data.setData(Uri.parse(String.valueOf(view.getTag()) ));
                setResult(RESULT_OK,data);
                finish();

            }
        });

    }

    @Override
    public void sendSave() {
        mAdapter.limpiar();
        mAdapter.setDataset(selectListPresenter.getList(this));
    }
}
