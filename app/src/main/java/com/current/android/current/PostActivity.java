package com.current.android.current;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PostActivity extends AppCompatActivity {
    private LatLng userLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            userLocation = new LatLng(extras.getDouble("USER_LONGITUDE"),
                    extras.getDouble("USER_LATITUDE"));
        }
        Log.d("Current", "User is at " + userLocation);
    }
}
