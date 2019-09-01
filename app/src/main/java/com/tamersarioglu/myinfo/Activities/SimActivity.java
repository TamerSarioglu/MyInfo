package com.tamersarioglu.myinfo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tamersarioglu.myinfo.R;

public class SimActivity extends AppCompatActivity {

    //code for run time permission
    private static final int READ_PHONE_STATE_CODE = 1;

    //Array Items that shown in ListView
    private String[] titles;
    private String[] descriptions;

    //ListView
    ListView mListView;
    TelephonyManager tm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("SIM");
            actionBar.setSubtitle("SIM 1");

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //listview from xml
        mListView = findViewById(R.id.simListView);

        //telephony manager
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        //handling runtime permission for OS marshmallow or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                //this will called when permission was not allowed
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                //show popup for runtime permission
                requestPermissions(permissions, READ_PHONE_STATE_CODE);
            } else {
                //this will called when permission was allowed
                getPhoneInfo();
            }
        } else {
            //this will called when os is < marshmallow
            getPhoneInfo();
        }
    }

    //method for getting sim info
    private void getPhoneInfo() {
        //Sim State
        int ss = tm.getSimState();

        String simState = "";

        switch (ss) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simState = "Absent";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simState = "Network Locked";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simState = "Pin Required";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simState = "Puk Required";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simState = "Ready";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simState = "Unknown";
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                simState = "Card IO Error";
                break;
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                simState = "Card Restricted";
                break;
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                simState = "PERM Diabled";
                break;
        }

        //service provider
        String serviceProvider = tm.getSimOperatorName();

        //mobile operator name
        String mobOprName = tm.getNetworkOperatorName();

        //integrated circuit card identifier (ICCID)
        String simID = tm.getSimSerialNumber();

        //Unique device id(IMEI)
        String imei = tm.getDeviceId();

        //international mobile subscriber id(IMSI)
        String tmSunscriberId = tm.getSubscriberId();

        //Device software version
        String softVersion = tm.getDeviceSoftwareVersion();

        //get Country Code(MCC)
        String county = tm.getNetworkCountryIso();

        //mobile country code (MCC) + Mobile network code (MNC)
        String mcc_mnc = tm.getSimOperator();

        //voice mail tag
        String voiceMailTag = tm.getVoiceMailAlphaTag();

        //Roaming
        boolean roamingStatus = tm.isNetworkRoaming();


        //adding this info to arrays
        titles = new String[]{
                "SIM State",
                "Service Provider",
                "Mobile Operator Name",
                "Integrated Circuit Card Identifier (ICCID)",
                "Unique Device ID (IMEI)",
                "International Mobile Subscriber ID (IMSI)",
                "Device Software Version",
                "Mobile Country Code (MCC)",
                "Mobile Country Code (MCC) + Mobile Network Code (MNC)",
                "Voicemail",
                "Roaming"
        };

        descriptions = new String[]{
                "" + simState,
                "" + serviceProvider,
                "" + mobOprName,
                "" + simID,
                "" + imei,
                "" + tmSunscriberId,
                "" + softVersion,
                "" + county,
                "" + mcc_mnc,
                "" + voiceMailTag,
                "" + roamingStatus,
        };

        //setting adapter
        mListView.setAdapter(new MyAdapter(this, titles, descriptions));


    }

    //creating custom adapter class for listView
    private class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String[] myTitles;
        String[] myDescryiptions;

        public MyAdapter(@NonNull Context c, String[] myTitles, String[] myDescryiptions) {
            super(c, R.layout.tworow, R.id.title, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescryiptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.tworow, parent, false);

            //textviews from tworow.xml
            TextView myTitleTv = view.findViewById(R.id.title_TextView);
            TextView myDescrTv = view.findViewById(R.id.description_TextView);
            //set text to these views
            myTitleTv.setText(myTitles[position]);
            myDescrTv.setText(myDescryiptions[position]);
            return view;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    getPhoneInfo();
                } else {
                    //permission denied
                    Toast.makeText(this, "READ_PHONE_STATE Permission is Required", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
