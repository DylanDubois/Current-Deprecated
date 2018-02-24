package com.current.android.current;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by duboi on 2/4/2018.
 */

public class EventPost {

    private String eventName;
    private String eventDescription;
    private String author;
    private LatLng location;
    private String eventType;


    public EventPost(String eventName, String eventDescription, String author, LatLng location, String type) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.author = author;
        this.location = location;
        this.eventType = type;
    }
    public EventPost(){

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


}
