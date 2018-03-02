package com.current.android.current;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.EventLog;
import android.util.Log;
import android.view.FrameMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button eventsButton;
    private Button postButton;
    private Button settingsButton;

    private LocationManager locationManager;
    private LocationListener locationListener;


    // Change to network on mobile
    private String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    private final int REQUEST_CODE = 123;
    private final long MIN_TIME = 500;
    private final float MIN_DISTANCE = 2;
    private final float DEFAULT_ZOOM = 15;
    private EventPost eventTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventsButton = findViewById(R.id.eventsButton);
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "Events Clicked");
            }
        });

        postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "Post Clicked");
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                if (userLocation != null){
                    intent.putExtra("USER_LONGITUDE",userLocation.getLongitude());
                    intent.putExtra("USER_LATITUDE",userLocation.getLatitude());
                }
                finish();
                startActivity(intent);


            }
        });

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "Settings Clicked");
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Tester.postRandomEvents(mMap);
        // Places all stored markers when called map is ready.
        EventPost.placeEventMarkers(mMap);
        initMarkerListener();
        getCurrentUserLocation();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Current", "resume() Called");
        getCurrentUserLocation();
    }

    public void initMarkerListener(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("Current", "Marker Clicked: " + marker.getTitle());

                // TEST!!
                PopupWindowCreator.createPopUpWindow(marker, (FrameLayout) findViewById(R.id.map),
                        getApplicationContext());

                return false;
            }
        });
    }


    private Location userLocation;

    private void getCurrentUserLocation() {
        //Log.d("Current", "getCurrentUserLocation() Called");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d("Current", "onLocationChanged()");
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());
                //Log.d("Current", "Latitude = " + latitude + "\nLongitude = " + longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Current", "providerDisabled()");
            }
        };

        // TODO: Change Fine to Coarse when using physical device
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
        if (mMap != null){
            Log.d("Current", "Map != null");
            userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d("Current Test", "Latitude = " + userLocation.getLatitude()
                    + "\nLongitude = " + userLocation.getLongitude());
            //mMap.setMyLocationEnabled(true);
            LatLngBounds userBounds = new LatLngBounds(new LatLng(userLocation.getLatitude() - 0.05,
                    userLocation.getLongitude() -0.05),
                    new LatLng(userLocation.getLatitude() + 0.05,
                            userLocation.getLongitude() +0.05));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userBounds.getCenter(),DEFAULT_ZOOM));



        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Current", "onReqPermission = granted");
            }
            else{
                Log.d("Current", "Permission denied");
            }
        }
    }
}
