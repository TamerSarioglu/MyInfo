package com.tamersarioglu.myinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class DeviceIDActivity extends AppCompatActivity {

    private String[] titles;
    private String[] descriptions;

    //phone state permission code
    private static final int READ_PHONE_STATE_PERMISSION = 1;

    private TelephonyManager tm;
    private String imei, simCardSerial, simSubscriberID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Device ID");
            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //android Device ID
        String deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //IMEI number
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // if os is marshmallow or above, user should manually set the permissions
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_DENIED) { // if os is below marshmallow we grant permissions here
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions, READ_PHONE_STATE_PERMISSION);
            } else {
                //permissions were granted
                imei = tm.getDeviceId();
                simCardSerial = tm.getSimSerialNumber();
                simSubscriberID = tm.getSubscriberId();
            }
        } else {
            //permissions were granted
            imei = tm.getDeviceId();
            simCardSerial = tm.getSimSerialNumber();
            simSubscriberID = tm.getSubscriberId();
        }

        //Device IP Adress
        String ipAddress = "There is no Internet Connection";
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnabled = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo info = connectivityManager.getNetworkInfo(network);
                if (info != null) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        ipAddress = getMobileIPAddress();
                    }
                }
            }
        } else {
            NetworkInfo mMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobile != null) {
                ipAddress = is3GEnabled + "";
            }
        }

        //device WiFi MAC Adress
        String wifiAddress = "There is no Wifi Connection";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo info = connectivityManager.getNetworkInfo(network);
                if (info != null) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        wifiAddress = getWiFiIPAddress();
                    }
                }
            }
        } else {
            NetworkInfo mMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mMobile != null) {
                ipAddress = is3GEnabled + "";
            }
        }

        //Bluetooth MAC Address
        String bt = Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");


        //arrays containing data
        titles = new String[]{"Android Device ID", "IMEI, MEID or ESN", "Hardware Serial Number",
                "SIM Card Serial No", "SIM Subscriber ID", "IP Address", "Wi-Fi Mac Address",
                "Bluetooth Mac Address", "Build Fingerprint"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            descriptions = new String[]{deviceid, imei, Build.getSerial(), simCardSerial, simSubscriberID, ipAddress, wifiAddress, bt, Build.FINGERPRINT};
        } else {
            descriptions = new String[]{deviceid, imei, Build.SERIAL, simCardSerial, simSubscriberID, ipAddress, wifiAddress, bt, Build.FINGERPRINT};

        }

        ListView listView = findViewById(R.id.devIdList);
        //set adapter to listView
        MyAdapter adapter = new MyAdapter(this, titles, descriptions);
        listView.setAdapter(adapter);
    }

    private String getWiFiIPAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        return Formatter.formatIpAddress(ip);
    }

    private static String getMobileIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }

    //create custom adapter class
    private class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String[] myTitles;
        String[] myDescriptions;

        //constructor
        public MyAdapter(@NonNull Context c, String[] titles, String[] descriptions) {
            super(c, R.layout.tworow, R.id.title_TextView, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Inflating he tworow.xml layout
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
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case READ_PHONE_STATE_PERMISSION: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recreate();
                    //permissions were granted
                    imei = tm.getDeviceId();
                    simCardSerial = tm.getSimSerialNumber();
                    simSubscriberID = tm.getSubscriberId();
                } else {
                    Toast.makeText(this, "Y0u Need To Enable READ_PHONE_SETTINGS Permission...", Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);

        return true;
    }
}
