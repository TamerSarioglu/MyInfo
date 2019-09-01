package com.tamersarioglu.myinfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tamersarioglu.myinfo.Activities.BatteryActivity;
import com.tamersarioglu.myinfo.Activities.CpuActivity;
import com.tamersarioglu.myinfo.Activities.DeviceIDActivity;
import com.tamersarioglu.myinfo.Activities.DisplayActivity;
import com.tamersarioglu.myinfo.Activities.GeneralActvity;
import com.tamersarioglu.myinfo.Activities.MemoryActivity;
import com.tamersarioglu.myinfo.Activities.SensorsActivity;
import com.tamersarioglu.myinfo.Activities.SimActivity;
import com.tamersarioglu.myinfo.Activities.SystemAppsActivity;
import com.tamersarioglu.myinfo.Activities.UserAppsActivity;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    ArrayList<Model> models, filterList;
    CustomFilter filter;

    MyAdapter(Context ctx, ArrayList<Model> models) {
        this.c = ctx;
        this.models = models;
        this.filterList = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //convert xml to OBJ
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);
        //Holder
        return new MyHolder(view);
    }

    //Data bound to views
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //Bind Data
        holder.nameTxt.setText(models.get(position).getName());
        holder.img.setImageResource(models.get(position).getImg());

        //handle item clicks
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //general
                if (models.get(pos).getName().equals("General")) {
                    //start general activity on click
                    Intent intent = new Intent(c, GeneralActvity.class);
                    c.startActivity(intent);
                }
                //Device ID
                if (models.get(pos).getName().equals("Device ID")) {
                    //start DeviceID activity on click
                    Intent intent = new Intent(c, DeviceIDActivity.class);
                    c.startActivity(intent);
                }
                //Display
                if (models.get(pos).getName().equals("Display")) {
                    //start Display activity on click
                    Intent intent = new Intent(c, DisplayActivity.class);
                    c.startActivity(intent);
                }
                //Battery
                if (models.get(pos).getName().equals("Battery")) {
                    //start Battery activity on click
                    Intent intent = new Intent(c, BatteryActivity.class);
                    c.startActivity(intent);
                }
                //User Apps
                if (models.get(pos).getName().equals("User Apps")) {
                    //start UserApps activity on click
                    Intent intent = new Intent(c, UserAppsActivity.class);
                    c.startActivity(intent);
                }
                //System Apps
                if (models.get(pos).getName().equals("System Apps")) {
                    //start SystemApps activity on click
                    Intent intent = new Intent(c, SystemAppsActivity.class);
                    c.startActivity(intent);
                }
                //Memory
                if (models.get(pos).getName().equals("Memory")) {
                    //start Memory activity on click
                    Intent intent = new Intent(c, MemoryActivity.class);
                    c.startActivity(intent);
                }
                //CPU
                if (models.get(pos).getName().equals("CPU")) {
                    //start CPU activity on click
                    Intent intent = new Intent(c, CpuActivity.class);
                    c.startActivity(intent);
                }
                //Sensors
                if (models.get(pos).getName().equals("Sensors")) {
                    //start Sensors activity on click
                    Intent intent = new Intent(c, SensorsActivity.class);
                    c.startActivity(intent);
                }
                //SIM
                if (models.get(pos).getName().equals("SIM")) {
                    //start SIM activity on click
                    Intent intent = new Intent(c, SimActivity.class);
                    c.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    //return filter obj
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }
        return filter;
    }
}
