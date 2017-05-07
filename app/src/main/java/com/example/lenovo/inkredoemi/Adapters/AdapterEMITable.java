package com.example.lenovo.inkredoemi.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.inkredoemi.Model.EMIRecord;
import com.example.lenovo.inkredoemi.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 07-05-2017.
 */

public class AdapterEMITable extends RecyclerView.Adapter<AdapterEMITable.OurHolder>{

    Context context;
    ArrayList<EMIRecord> emiRecords;

    public AdapterEMITable(Context context, ArrayList<EMIRecord> emiRecords) {
        this.context = context;
        this.emiRecords = emiRecords;
    }

    public class OurHolder extends RecyclerView.ViewHolder{

        TextView tenure,emi,total;
        public OurHolder(View itemView) {
            super(itemView);
            tenure = (TextView)itemView.findViewById(R.id.tenure);
            emi = (TextView)itemView.findViewById(R.id.emi);
            total = (TextView)itemView.findViewById(R.id.total);
             }
    }
    @Override
    public AdapterEMITable.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.item_emi_table,parent , false);
        return new OurHolder(v);
    }


    @Override
    public void onBindViewHolder(final AdapterEMITable.OurHolder holder, final int position) {


            holder.tenure.setText(String.valueOf(emiRecords.get(position).getTenure()));
            holder.emi.setText(String.valueOf(emiRecords.get(position).getEmi()));
            holder.total.setText(String.valueOf(emiRecords.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return emiRecords.size();
    }

}
