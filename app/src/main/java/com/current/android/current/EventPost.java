package com.current.android.current;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by duboi on 2/4/2018.
 */

public class EventPost extends AppCompatActivity{

    private static HashMap<String, BitmapDescriptor> markerColors = new HashMap<>();
    static {
        Log.d("Current", "EventPost static called.");

        // Assigns color to markers based on their event type.
        markerColors.put("Academic", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        markerColors.put("Entertainment", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        markerColors.put("Social", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        markerColors.put("Other", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    private String eventName;
    private String eventDescription;
    private String author;
    private LatLng location;
    private String eventType;
    private float eventRatings, numberOfVotes;
    public static ArrayList<EventPost> eventsArray = new ArrayList<>();


    public EventPost(String eventName, String eventDescription, String author, LatLng location, String type) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.author = author;
        this.location = location;
        this.eventType = type;
    }

    public static void placeEventMarkers(GoogleMap mMap){
        EventPost event;
        for (int i = 0; i < eventsArray.size(); i++){
            event = eventsArray.get(i);
            mMap.addMarker(new MarkerOptions().position(event.getLocation()).
                    title(event.getEventName()).icon(markerColors.get(event.getEventType())))
                    .setTag(event);
        }
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public float getEventRatings() {
        return eventRatings;
    }

    public void setEventRatings(float eventRatings) {
        this.eventRatings = eventRatings;
    }

    public float getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(float numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public float getEventStars(){
        return eventRatings / numberOfVotes;
    }

}
