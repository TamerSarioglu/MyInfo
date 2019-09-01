package com.tamersarioglu.myinfo.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tamersarioglu.myinfo.R;

import java.io.File;
import java.text.NumberFormat;

public class MemoryActivity extends AppCompatActivity {

    TextView mTvTotalRam, mTvFreeRam, mTvUsedRam;
    TextView mTvTotalRom, mTvFreeRom, mTvUsedRom;
    TextView mTvTotalHeap;
    TextView mTvPercRam, mTvPercRom;
    ProgressBar pBarRam, pBarRom;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Memory");
            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //inits
        mTvTotalRam = findViewById(R.id.totalRam);
        mTvTotalRom = findViewById(R.id.totalRom);
        mTvTotalHeap = findViewById(R.id.totalHeap);

        mTvFreeRam = findViewById(R.id.freeRam);
        mTvFreeRom = findViewById(R.id.freeRom);

        mTvUsedRam = findViewById(R.id.usedRam);
        mTvUsedRom = findViewById(R.id.usedRom);

        mTvPercRam = findViewById(R.id.tv_perc_ram);
        mTvPercRom = findViewById(R.id.tv_perc_rom);

        pBarRam = findViewById(R.id.pbRam);
        pBarRom = findViewById(R.id.pbRom);


        //RAM getting info
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }

        float totalMem = memoryInfo.totalMem / (1024 * 1024); //total ram
        float freeMem = memoryInfo.availMem / (1024 * 1024); //avilable ram
        float usedMem = totalMem - freeMem; //used ram

        //percentage of free mem
        float freeMemPerc = (freeMem / totalMem) * 100;

        //percentage of used mem
        float usedMemPerc = (usedMem / totalMem) * 100;

        //Free RAM Percentage decimal point conversion
        NumberFormat numFormFreePerc = NumberFormat.getNumberInstance();
        numFormFreePerc.setMinimumFractionDigits(1);
        numFormFreePerc.setMaximumFractionDigits(1);
        String mFreeMemPerc = numFormFreePerc.format(freeMemPerc);

        //Used RAM Percentage decimal point conversion
        NumberFormat numFormUsedPerc = NumberFormat.getNumberInstance();
        numFormUsedPerc.setMinimumFractionDigits(1);
        numFormUsedPerc.setMaximumFractionDigits(1);
        String mUsedMemPerc = numFormFreePerc.format(usedMemPerc);

        // ROM, getting Information
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSize();
        float totalBlocks = stat.getBlockCount();
        float availableBlocks = stat.getAvailableBlocks();//free ROM
        float totalRom = (totalBlocks * blockSize) / (1024 * 1024); // value of total ROM
        float freeRom = (availableBlocks * blockSize) / (1024 * 1024); //Value of free ROM
        float usedRom = totalRom - freeRom; //used ROM

        // ROM Percentage
        float freeRomPerc = (freeRom / totalRom) * 100;
        float usedRomPerc = (usedRom / totalRom) * 100;

        //Total ROM decimal point conversion
        NumberFormat numFormTotalRom = NumberFormat.getNumberInstance();
        numFormTotalRom.setMinimumFractionDigits(1);
        numFormTotalRom.setMaximumFractionDigits(1);
        String mTotalRom = numFormFreePerc.format(totalRom);

        //Free ROM decimal point conversion
        NumberFormat numFormFreeRom = NumberFormat.getNumberInstance();
        numFormFreeRom.setMinimumFractionDigits(1);
        numFormFreeRom.setMaximumFractionDigits(1);
        String mFreeRom = numFormFreePerc.format(freeRom);

        //Used ROM decimal point conversion
        NumberFormat numFormUsedRom = NumberFormat.getNumberInstance();
        numFormUsedRom.setMinimumFractionDigits(1);
        numFormUsedRom.setMaximumFractionDigits(1);
        String mUsedRom = numFormFreePerc.format(usedRom);

        //Free ROM Percentage decimal point conversion
        NumberFormat numFormFreeRomPerc = NumberFormat.getNumberInstance();
        numFormFreeRomPerc.setMinimumFractionDigits(1);
        numFormFreeRomPerc.setMaximumFractionDigits(1);
        String mFreeRomPerc = numFormFreePerc.format(freeRomPerc);

        //Free ROM Percentage decimal point conversion
        NumberFormat numFormUsedRomPerc = NumberFormat.getNumberInstance();
        numFormUsedRomPerc.setMinimumFractionDigits(1);
        numFormUsedRomPerc.setMaximumFractionDigits(1);
        String mUsedRomPerc = numFormFreePerc.format(usedRomPerc);


        //setting RAM Info
        mTvTotalRam.setText(" " + totalMem + " MB");
        mTvFreeRam.setText(" " + freeMem + " MB" + " ( " + mFreeMemPerc + " %)");
        mTvUsedRam.setText(" " + usedMem + " MB " + " ( " + mUsedMemPerc + " %)");

        //setting ROM Info
        mTvTotalRom.setText(" " + mTotalRom + " MB");
        mTvFreeRom.setText(" " + mFreeRom + " MB" + " ( " + mFreeRomPerc + " %)");
        mTvUsedRom.setText(" " + mUsedRom + " MB" + " ( " + mUsedRomPerc + " %)");

        //getting Java Heap
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();

        //Setting Java Heap Info
        mTvTotalHeap.setText(" " + maxMemory / (1024 * 1024) + " MB");

        //Setting RAM info to ProgressBar and TextView onProgressBar
        mTvPercRam.setText(mUsedMemPerc + "% Used");
        pBarRam.setProgress((int) usedMemPerc);


        //Setting ROM info to ProgressBar and TextView onProgressBar
        mTvPercRom.setText(mUsedRomPerc + " % Used");
        pBarRom.setProgress((int) usedRomPerc);


    }

    //to go back previous activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
