package com.tamersarioglu.myinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdaper mAdapter;

    private TextView mManufacturerTv, mAndroidVersionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        //collapsing toolbar
        initCollapsingToolbar();

        //RecyclerView
        mRecyclerView = findViewById(R.id.myRecyclerView);

        //set recyclerviews properties
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); //gridView with 2 columns for each row
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Adapter
        mAdapter = new MyAdaper(this, getModels());
        mRecyclerView.setAdapter(mAdapter);


        //getting device manufacturer name
        String mManufacturer = Build.MANUFACTURER;
        //getting device model
        String model = Build.MODEL;
        //getting device android version
        String version = Build.VERSION.RELEASE;
        //getting android version name
        String verName = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();

        //setting these o our in collapsing toolbar
        mManufacturerTv = findViewById(R.id.name_model);
        mAndroidVersionTv = findViewById(R.id.android_version);

        mManufacturerTv.setText(mManufacturer.toUpperCase() + "" + model);
        mAndroidVersionTv.setText(version + "" + verName);

        //display android version logo/icon
        try {
            //JELLY BEAN
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN) {
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backDrop));
            }
            //JELLY BEAN
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backDrop));
            }
            //JELLY BEAN
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Glide.with(this).load(R.drawable.android41).into((ImageView) findViewById(R.id.backDrop));
            }
            //KIT KAT
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                Glide.with(this).load(R.drawable.android44).into((ImageView) findViewById(R.id.backDrop));
            }
            //LOLLIPOP
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                Glide.with(this).load(R.drawable.android50).into((ImageView) findViewById(R.id.backDrop));
            }
            //LOLLIPOP
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
                Glide.with(this).load(R.drawable.android50).into((ImageView) findViewById(R.id.backDrop));
            }
            //MARSHMALLOW
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                Glide.with(this).load(R.drawable.android60).into((ImageView) findViewById(R.id.backDrop));
            }
            //NOUGAT
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                Glide.with(this).load(R.drawable.android70).into((ImageView) findViewById(R.id.backDrop));
            }
            //NOUGAT
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                Glide.with(this).load(R.drawable.android70).into((ImageView) findViewById(R.id.backDrop));
            }
            //OREO
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
                Glide.with(this).load(R.drawable.android80).into((ImageView) findViewById(R.id.backDrop));
            }
            //OREO
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
                Glide.with(this).load(R.drawable.android80).into((ImageView) findViewById(R.id.backDrop));
            }
            //PIE
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
                Glide.with(this).load(R.drawable.android90).into((ImageView) findViewById(R.id.backDrop));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_ToolBar);
        collapsingToolbarLayout.setTitle("");

        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        appBarLayout.setExpanded(true);

        //setting collapsingToolbar title
        collapsingToolbarLayout.setTitle("Device Info");
    }

    //add models to ArrayList
    private ArrayList<Model> getModels() {
        ArrayList<Model> models = new ArrayList<>();
        Model p = new Model();

        p = new Model();
        p.setName("General");
        p.setImg(R.drawable.general);
        models.add(p);

        p = new Model();
        p.setName("Device ID");
        p.setImg(R.drawable.devid);
        models.add(p);

        p = new Model();
        p.setName("Display");
        p.setImg(R.drawable.display);
        models.add(p);

        p = new Model();
        p.setName("Battery");
        p.setImg(R.drawable.battery);
        models.add(p);

        p = new Model();
        p.setName("User Apps");
        p.setImg(R.drawable.userapps);
        models.add(p);

        p = new Model();
        p.setName("System Apps");
        p.setImg(R.drawable.systemapps);
        models.add(p);

        p = new Model();
        p.setName("Memory");
        p.setImg(R.drawable.memory);
        models.add(p);

        p = new Model();
        p.setName("CPU");
        p.setImg(R.drawable.cpu);
        models.add(p);

        p = new Model();
        p.setName("Sensors");
        p.setImg(R.drawable.sensor);
        models.add(p);

        p = new Model();
        p.setName("SIM");
        p.setImg(R.drawable.sim);
        models.add(p);

        return models;
    }
}
