package com.tresbontech.gpstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private static final int PERMISSIONS_FINE_LOCATION = 99;

    //REFERENCE  to UI elements
    TextView tv_labellat, tv_lat, tv_labellon, tv_lon,tv_labelaltitude,tv_altitude,tv_labelaccuracy, tv_accuracy,tv_labelspeed,tv_speed,tv_labelsensor,tv_sensor,tv_labelupdates,tv_updates,tv_address,tv_lbladdress;
    Switch sw_locationsupdates, sw_gps;

    //Variable to remember if we are tracking location or not.
    Boolean updateOn = false;

    //Location is the config file for all setting related to fusedLocationProviderClient
    LocationRequest locationRequest;


    //Google API for location services. Majority of the Apps feature depends on it.
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_labellat = findViewById(R.id.tv_labellat);
        tv_lat = findViewById(R.id.tv_lat);
        tv_labellon = findViewById(R.id.tv_labellon);
        tv_lon = findViewById(R.id.tv_lon);
        tv_labelaltitude = findViewById(R.id.tv_labelaltitude);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_labelaccuracy = findViewById(R.id.tv_labelaccuracy);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_labelspeed = findViewById(R.id.tv_labelspeed);
        tv_speed = findViewById(R.id.tv_speed);
        tv_labelsensor = findViewById(R.id.tv_labelsensor);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_labelupdates = findViewById(R.id.tv_labelupdates);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_labelsensor);
        tv_lbladdress = findViewById(R.id.tv_lbladdress);
        sw_locationsupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);

        //Set all  properties of Locationrequest

        locationRequest = new LocationRequest();

        // How often does the default location check error?

        locationRequest .setInterval(1000 * DEFAULT_UPDATE_INTERVAL);

        // How often does the location check occur when set to the most frequent update?
        locationRequest.setFastestInterval(1000 *  FAST_UPDATE_INTERVAL);


        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_gps.isChecked()){
                    //most relaible - GPS
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("Using GPS sensors");
                }
                else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("Using Towers + WIFI ");
                }
            }
        });
    }// end OnCreate method

    private void updateGPS(){
        //Get permission from User to track GPS
        //Get the current location from the fused client
        //Update UI - i.e set all properties in their associated text view

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //User provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                }
            });

        }else {
            // Permission not granted Yet.

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }

        }
    }
}