package com.tamersarioglu.myinfo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tamersarioglu.myinfo.R;

import java.text.NumberFormat;

public class DisplayActivity extends AppCompatActivity {

    //Array Items that shown in ListView
    private String[] titles;
    private String[] descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Display");
            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //screen size in pixel, Width, height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);



        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        String resolution = width + " x " + height + " Pixel";


        //physical size in inch
        double x = Math.pow(width / displayMetrics.xdpi, 2);
        double y = Math.pow(height / displayMetrics.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        String screenInchesOutPut = format.format(screenInches);

        //refresh Rate
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshRating = display.getRefreshRate();
        NumberFormat format2 = NumberFormat.getNumberInstance();
        format2.setMaximumFractionDigits(2);
        format2.setMinimumFractionDigits(2);
        String outPutRefreshRating = format2.format(refreshRating);

        //data contains
        titles = new String[]{"Resolution", "Density", "Physical Size", "Refresh Rate", "Orientation"};
        descriptions = new String[]{resolution, DisplayMetrics.DENSITY_XHIGH + "dpi (xhdpi)", screenInchesOutPut +
                "inch", outPutRefreshRating + "Hz", this.getResources().getConfiguration().orientation + ""};

        //ListView
        ListView list = findViewById(R.id.displayList);

        //Adapter
        MyAdapter adapter = new MyAdapter(this, titles, descriptions);
        list.setAdapter(adapter);

    }

    public int GetScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int GetScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    //Custom Adapter class for listView
    private class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String[] myTitles;
        String[] myDescriptions;

        public MyAdapter(@NonNull Context c, String[] titles, String[] descriptions) {
            super(c, R.layout.tworow, R.id.title_TextView, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            //Inflate layout tworow.xml
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.tworow, parent, false);

            //Views in tworow.xml
            TextView myTitle = row.findViewById(R.id.title_TextView);
            TextView myDescr = row.findViewById(R.id.description_TextView);

            //set text to these views
            myTitle.setText(titles[position]);
            myDescr.setText(descriptions[position]);

            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);

        return true;
    }
}
