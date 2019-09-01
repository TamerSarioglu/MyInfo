package com.tamersarioglu.myinfo.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tamersarioglu.myinfo.R;

public class BatteryActivity extends AppCompatActivity {

    //Views!!
    TextView textView1, textView2, batteryPercentage, mTextViewPercentage;

    private double batteryCapacity;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Battery");
            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //getApplication complex
        android.content.Context context = getApplicationContext();
        //initialize a new Filter
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //Register the broadcast receiver
        context.registerReceiver(mBroadcastReceiver, iFilter);


        //get the widgets reference from xml
        batteryPercentage = findViewById(R.id.battery_percentage);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        mTextViewPercentage = findViewById(R.id.tv_percentage);
        mProgressBar = findViewById(R.id.pb);

        Object mPowerProfile = null;
        String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            batteryCapacity = (Double)Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile,"battery.capacity");
        }catch (Exception e){

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String charging_status = "", battery_condition = "", power_source = "Unplugged";

            //get battery percentage
            int batter_level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            //get battery health
            int battery_health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

            if (battery_health == BatteryManager.BATTERY_HEALTH_COLD) {
                battery_condition = "Cold";
            }
            if (battery_health == BatteryManager.BATTERY_HEALTH_DEAD) {
                battery_condition = "Dead";
            }
            if (battery_health == BatteryManager.BATTERY_HEALTH_GOOD) {
                battery_condition = "Good";
            }
            if (battery_health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                battery_condition = "Over Heat";
            }
            if (battery_health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                battery_condition = "Over Voltage";
            }
            if (battery_health == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                battery_condition = "Unknown";
            }
            if (battery_health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                battery_condition = "Unspecified Failure";
            }

            //get Battery Temperature in celcius
            int battery_temperature_celcius = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;

            //Celcius to Fahrenheit battery temperature conversion
            int battery_temperature_fahrenheit = (int) (battery_temperature_celcius * 1.8 + 32);

            //get battery power source
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            if (chargePlug == BatteryManager.BATTERY_PLUGGED_USB) {
                power_source = "USB";
            }
            if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC) {
                power_source = "AC Adapter";
            }
            if (chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                power_source = "Wireless";
            }

            //get the status of battery i.e Charging/Discharging
            int battery_status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (battery_status == BatteryManager.BATTERY_STATUS_CHARGING) {
                charging_status = "Charging";
            }
            if (battery_status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                charging_status = "Discharging";
            }
            if (battery_status == BatteryManager.BATTERY_STATUS_FULL) {
                charging_status = "Battery Full";
            }
            if (battery_status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                charging_status = "Unknown";
            }
            if (battery_status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                charging_status = "Not Charging";
            }

            //get battery technology
            String battery_technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

            //get battery voltage
            int battery_voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            //Display the output of battery status
            batteryPercentage.setText("Battery Percentage: " + batter_level + "%");
            textView1.setText("Condition:\n" +
                    "Temperature:\n" +
                    "Power Source:\n" +
                    "Charging Status:\n" +
                    "Type:\n" +
                    "Voltage:\n" +
                    "Capacity:");

            textView2.setText(battery_condition + "\n" +
                    "" + battery_temperature_celcius + "" + (char) 0x00B0 + "C/" + battery_temperature_fahrenheit + "" + (char) 0x00B0 + "F\n" +
                    "" + power_source + "\n" +
                    "" + charging_status + "\n" +
                    "" + battery_technology + "\n" +
                    "" + battery_voltage + "mV\n" +
                    "" + batteryCapacity + " mAh");

            int levels = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float percentage = levels / (float) scale;

            //update progress bar to display current battery charged percentage
            mProgressStatus = (int) ((percentage) * 100);
            mTextViewPercentage.setText("" + mProgressStatus + "%");

            //display the battery charged percentage in progress bar
            mProgressBar.setProgress(mProgressStatus);

        }
    };
}
