package com.tamersarioglu.myinfo.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tamersarioglu.myinfo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class CpuActivity extends AppCompatActivity {

    ProcessBuilder mProcessBuilder;
    String holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream mInputStream;
    Process mProcess;
    byte[] mByteArray;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("CPU");

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        //ListView
        mListView = findViewById(R.id.cpuListView);

        //getting information of cpu
        mByteArray = new byte[1024];
        try {
            mProcessBuilder = new ProcessBuilder(DATA);
            mProcess = mProcessBuilder.start();
            mInputStream = mProcess.getInputStream();

            while (mInputStream.read(mByteArray) != -1) {
                holder = holder + new String(mByteArray);
            }
            //close input stream
            mInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                Collections.singletonList(holder));

        //set adapter to listView
        mListView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
