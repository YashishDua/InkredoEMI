package com.example.lenovo.inkredoemi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.inkredoemi.Adapters.AdapterEMITable;
import com.example.lenovo.inkredoemi.Model.EMIRecord;
import com.example.lenovo.inkredoemi.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;

import static com.activeandroid.Cache.getContext;

public class EMIActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText enteredAmount,enteredTenure;
    private RecyclerView recyclerView;
    private ArrayList<EMIRecord> emiRecords;
    private AdapterEMITable adapter;
    private Button calculateButton;
    public final Double INTEREST_RATE = 36.0;
    private LinearLayout tableHeadings;
    private Button submitButton;
    String userNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userNumber = getIntent().getStringExtra("Number");

        enteredAmount = (EditText)findViewById(R.id.edit_text_amount);
        enteredTenure = (EditText)findViewById(R.id.edit_text_tenure);
        recyclerView = (RecyclerView)findViewById(R.id.recylcer_view);
        calculateButton = (Button)findViewById(R.id.button_calculate);
        tableHeadings = (LinearLayout)findViewById(R.id.table_headings);
        submitButton = (Button)findViewById(R.id.button_submit);
        emiRecords = new ArrayList<>();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emiRecords.clear();
                tableHeadings.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.VISIBLE);

                int tenureCounterStart = Integer.parseInt(enteredTenure.getText().toString()) - 3 ;
                int tenureCounterEnd = Integer.parseInt(enteredTenure.getText().toString()) + 3 ;

                for(int i=tenureCounterStart;i<=tenureCounterEnd;i++){
                    if(i>0) {
                        double tempEMI = calculateEMI(i, Double.parseDouble(enteredAmount.getText().toString()));
                        EMIRecord emiRecord = new EMIRecord();
                        emiRecord.setEmi(round(tempEMI, 2));
                        emiRecord.setTenure(i);
                        emiRecord.setTotal(round(tempEMI * i, 2));
                        emiRecords.add(emiRecord);
                        Log.i("----", "Record added ");
                        emiRecord.setUUID(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                        if(userNumber!=null)
                        emiRecord.setNumber(userNumber);
                        emiRecord.save();
                    }
                    //adding to database..
                }

                adapter.notifyDataSetChanged();

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer tenure = Integer.valueOf(enteredTenure.getText().toString());
                double tempEMI = calculateEMI(tenure, Double.parseDouble(enteredAmount.getText().toString()));
                EMIRecord emiRecord = new EMIRecord();
                emiRecord.setEmi(round(tempEMI, 2));
                emiRecord.setTenure(tenure);
                emiRecord.setTotal(round(tempEMI * tenure, 2));
                emiRecords.add(emiRecord);
                Log.i("----", "Record added ");
                emiRecord.setUUID(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                if(userNumber!=null)
                    emiRecord.setNumber(userNumber);
                emiRecord.save();
                Toast.makeText(EMIActivity.this,"Submitted !",Toast.LENGTH_SHORT).show();

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AdapterEMITable(this,emiRecords);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EMIActivity.this,DashBoard.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.emiactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.activity_dash_board) {
            // Handle the camera action
            Intent i = new Intent(EMIActivity.this,DashBoard.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Double calculateEMI(Integer installmentNumber,Double principalAmount) {
        double R = (INTEREST_RATE / 12) / 100;
        return (principalAmount * R * (Math.pow((1 + R), installmentNumber)) / ((Math.pow((1 + R), installmentNumber)) - 1));
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
