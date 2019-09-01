package com.tamersarioglu.myinfo.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tamersarioglu.myinfo.R;

import java.util.ArrayList;
import java.util.List;

public class UserAppsActivity extends AppCompatActivity {

    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;

    //ListView
    ListView userInstalledAppLV;

    List<PackageInfo> packs;
    List<AppList> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_apps);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setTitle("User Apps");

            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //init
        userInstalledAppLV = findViewById(R.id.installed_app_list);

        //call method to get installed apps
        installedApps = getInstalledApps();

        //adapter
        installedAppAdapter = new AppAdapter(UserAppsActivity.this, installedApps);

        //set adapter
        userInstalledAppLV.setAdapter(installedAppAdapter);

        //list item click listener
        userInstalledAppLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //we are going to pop up an alert dialog with 3 options.
                //1- Open App 2-App Info 3-Uninstall

                //options
                String[] options = {"Open App", "App Info", "Uninstall"};

                //Alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(UserAppsActivity.this);
                //set title to alert dialog
                builder.setTitle("Choose Action");
                //set options to alert dialog
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {
                            //0 means Open App clicked
                            Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packages);
                            if (intent != null) {
                                startActivity(intent);
                            } else {
                                //if anything went wrong pop a toast message
                                Toast.makeText(UserAppsActivity.this, "Error, Please Tyr Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (i == 1) {
                            //1 means App Info clicked
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + installedApps.get(i).packages));

                            //show package name in toast
                            Toast.makeText(UserAppsActivity.this, installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }

                        if (i == 2) {
                            //2 means Uninstall clicked
                            String packages = installedApps.get(i).packages; //get package name to uninstall
                            Intent intent = new Intent(Intent.ACTION_DELETE); //intent to delete/uninstall app
                            intent.setData(Uri.parse("package:" + packages));
                            startActivity(intent);
                            recreate();//restart activity to update app list after uninstalling
                        }
                    }
                });

                //show dialog
                builder.show();
            }
        });

        //getting total number of apps
        String size = userInstalledAppLV.getCount() + "";
        //show in textview above our listview
        TextView countApps = findViewById(R.id.countApps);
        countApps.setText("Total Instralled Apps: " + size);

    }

    //creating AppList class
    public class AppList {
        String name;
        Drawable icon;
        String packages;
        String version;

        AppList(String name, Drawable icon, String packages, String version) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackages() {
            return packages;
        }

        public String getVersion() {
            return version;
        }
    }

    //creating AppAdapter class
    public class AppAdapter extends BaseAdapter {

        LayoutInflater layoutInflater;
        List<AppList> listStorage;

        AppAdapter(Context context, List<AppList> customizedListView) {
            //layout inflator
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if (convertView == null) {
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.modelapps, parent, false);

                listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView = convertView.findViewById(R.id.app_package);
                listViewHolder.versionInListView = convertView.findViewById(R.id.app_version);

                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }

            //set data to our views
            listViewHolder.textInListView.setText(listStorage.get(i).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(i).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(i).getPackages());
            listViewHolder.versionInListView.setText(listStorage.get(i).getVersion());

            return convertView;
        }

        class ViewHolder {
            //our views from modelapps.xml
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
            TextView versionInListView;
        }

    }

    //get app information
    private List<AppList> getInstalledApps() {
        apps = new ArrayList<>();
        packs = getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packs.size(); i++) {

            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {

                //get application name
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();

                //get application icon
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());

                //get application package
                String packages = p.applicationInfo.packageName;

                //get application version
                String version = p.versionName;

                //add data
                apps.add(new AppList(appName, icon, packages, version));

            }
        }

        return apps;
    }

    //check if app is not system app because we will display only user apps
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //when backbutton in actionbar is clicked, go to activity
        return true;
    }
}
