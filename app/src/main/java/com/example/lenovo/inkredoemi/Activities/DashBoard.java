package com.example.lenovo.inkredoemi.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.inkredoemi.Adapters.AdapterDashboard;
import com.example.lenovo.inkredoemi.Model.EMIRecord;
import com.example.lenovo.inkredoemi.R;

import java.util.ArrayList;

import static com.activeandroid.Cache.getContext;

public class DashBoard extends AppCompatActivity {

    ArrayList<EMIRecord> emiRecordArrayList;
    private RecyclerView recyclerView;
    private AdapterDashboard adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        getSupportActionBar().setTitle("DashBoard");

        recyclerView = (RecyclerView)findViewById(R.id.recylcer_view);
        emiRecordArrayList =  new ArrayList<>();

        if(new EMIRecord().getAllRecords()!=null)
            emiRecordArrayList.addAll(new EMIRecord().getAllRecords());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AdapterDashboard(this,emiRecordArrayList);
        recyclerView.setAdapter(adapter);

    }
}
