package com.tamersarioglu.myinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdaper extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Model> models;

    public MyAdaper(Context ctx, ArrayList<Model> models) {
        this.c = ctx;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //convert xml to OBJ
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
        //Holder
        return new MyHolder(view);
    }

    //Data bound to views
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //Bind Data
        holder.nameTxt.setText(models.get(position).getName());
        holder.img.setImageResource(models.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
