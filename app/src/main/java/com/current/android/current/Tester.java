package com.current.android.current;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by duboi on 2/27/2018.
 */


// Class is used to implement various tests of the main application
public class Tester {
    private static HashMap<String, BitmapDescriptor> markerColors = new HashMap<>();
    static {
        Log.d("Current", "EventPost static called.");

        // Assigns color to markers based on their event type.
        markerColors.put("Academic", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        markerColors.put("Entertainment", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        markerColors.put("Social", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        markerColors.put("Other", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    // i.e. eventsPosterTester(), etc
    private static String[] eventNames = {"Art Study Session", "Hannah Montana Concert", "Sorority Hazing", "UFO Sighting"};
    private static String[] eventTypes = {"Academic", "Entertainment", "Social", "Other"};
    private static Random random = new Random();

    public static void postRandomEvents(GoogleMap map){
        int randomIndex;
        for (int i = 0; i < 10; i++){
            randomIndex = random.nextInt(4);
            EventPost eventPost = new EventPost(eventNames[randomIndex], "Description",
                    "Author", new LatLng(30 + .5 * random.nextDouble(), -90 -.5 * random.nextDouble()),
                    eventTypes[randomIndex]);
            map.addMarker(new MarkerOptions().title(eventPost.getEventName()).
                    position(eventPost.getLocation())).setIcon(markerColors.get(eventPost.getEventType()));
        }

        Log.d("Current", "Random Events posted");

    }
}
