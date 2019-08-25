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
                    Toast.makeText(c, "Battery", Toast.LENGTH_SHORT).show();
                }
                //User Apps
                if (models.get(pos).getName().equals("User Apps")) {
                    Toast.makeText(c, "User Apps", Toast.LENGTH_SHORT).show();
                }
                //System Apps
                if (models.get(pos).getName().equals("System Apps")) {
                    Toast.makeText(c, "System Apps", Toast.LENGTH_SHORT).show();
                }
                //Memory
                if (models.get(pos).getName().equals("Memory")) {
                    Toast.makeText(c, "Memory", Toast.LENGTH_SHORT).show();
                }
                //CPU
                if (models.get(pos).getName().equals("CPU")) {
                    Toast.makeText(c, "CPU", Toast.LENGTH_SHORT).show();
                }
                //Sensors
                if (models.get(pos).getName().equals("Sensors")) {
                    Toast.makeText(c, "Sensors", Toast.LENGTH_SHORT).show();
                }
                //SIM
                if (models.get(pos).getName().equals("SIM")) {
                    Toast.makeText(c, "SIM", Toast.LENGTH_SHORT).show();
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
