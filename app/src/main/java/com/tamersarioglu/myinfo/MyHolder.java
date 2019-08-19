package com.tamersarioglu.myinfo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyHolder extends RecyclerView.ViewHolder {

    //Our views
    ImageView img;
    TextView nameTxt;

    MyHolder(@NonNull View itemView) {
        super(itemView);
        this.img = itemView.findViewById(R.id.modelImage);
        this.nameTxt = itemView.findViewById(R.id.modelTxt);

    }
}
