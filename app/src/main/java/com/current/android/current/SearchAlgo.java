package com.current.android.current;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by William Woodfin and Cody Falcon on 4/6/2018.
 */

public class SearchAlgo {

    public ArrayList searchAlgo(ArrayList<EventPost> eventList, String title, String description, String author, int radius, String type, Location location)
    {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double degreeLat = radius/69;
        double maxLat = latitude + degreeLat;
        double minLat = latitude - degreeLat;
        double maxLong = longitude + (Math.cos(maxLat)*degreeLat);
        double minLong = longitude - (Math.cos(minLat)*degreeLat);
        //eventList.orderByChild("latitude").startAt(minLat).endAt(maxLat).addChildEventListener(new ArrayList<EventPost>() {});
        //eventList.orderByChild("longitude").startAt(minLong).endAt(maxLong).addChildEventListener(new ArrayList<EventPost>() {});
        for(int i=0; i<eventList.size();i++)
        {
            EventPost event = eventList.get(i);
            if(maxLat < event.latitude || minLat > event.latitude || maxLong < event.longitude || minLong > event.longitude)
                eventList.remove(i);
        }
        ParameterTester(eventList, title, description, author, type);
        return eventList;
    }


    public void ParameterTester(ArrayList<EventPost> eventList, String title, String description, String author, String type) {
        if(title != null && description != null && author != null) {
            for (int index = 0; index < eventList.size(); index++) {
                if (!TitleTester(eventList.get(index), title)) {
                    eventList.remove(index);
                }
                if (!DescriptionTester(eventList.get(index), description)) {
                    eventList.remove(index);
                }
                if (!AuthorTester(eventList.get(index), author)) {
                    eventList.remove(index);
                }
                if(eventList.get(index).eventType != type) {
                    eventList.remove(index);
                }
            }
        }
        else if(title != null && description != null && author == null) {
            for (int index = 0; index < eventList.size(); index++) {
                if (!TitleTester(eventList.get(index), title)) {
                    eventList.remove(index);
                }
                if (!DescriptionTester(eventList.get(index), description)) {
                    eventList.remove(index);
                }
                if(eventList.get(index).eventType != type) {
                    eventList.remove(index);
                }
            }
        }
        else if(title != null && description == null && author != null) {
            for (int index = 0; index < eventList.size(); index++) {
                if (!TitleTester(eventList.get(index), title)) {
                    eventList.remove(index);
                }
                if (!AuthorTester(eventList.get(index), author)) {
                    eventList.remove(index);
                }
                if(eventList.get(index).eventType != type) {
                    eventList.remove(index);
                }
            }
        }
        else if(title == null && description != null && author != null) {
            for (int index = 0; index < eventList.size(); index++) {
                if (!DescriptionTester(eventList.get(index), description)) {
                    eventList.remove(index);
                }
                if (!AuthorTester(eventList.get(index), author)) {
                    eventList.remove(index);
                }
                if(eventList.get(index).eventType != type) {
                    eventList.remove(index);
                }
            }
        }
        else if(title != null && description == null && author == null) {
            for (int index = 0; index < eventList.size(); index++) {
                if (!TitleTester(eventList.get(index), title)) {
                    eventList.remove(index);
                }
                if(eventList.get(index).eventType != type) {
                    eventList.remove(index);
                }
            }
        }
        else if(title == null && description != null && author == null) {
            for (int index = 0; index < eventList.size(); index++) {
                if (!DescriptionTester(eventList.get(index), description)) {
                    eventList.remove(index);
                }
                if(eventList.get(index).eventType != type) {
                    eventList.remove(index);
                }
            }
        }
        else if(title == null && description == null && author != null) {
            for (int index = 0; index < eventList.size(); index++) {
                if (!AuthorTester(eventList.get(index), author)) {
                    eventList.remove(index);
                }
                if(eventList.get(index).eventType != type) {
                    eventList.remove(index);
                }
            }
        }
    }

    public boolean TitleTester(EventPost event, String title) {
        if(event.eventName.contains(title))
            return true;
        else
            return false;
    }
    public boolean DescriptionTester(EventPost event, String description) {
        if(event.eventDescription.contains(description))
            return true;
        else
            return false;
    }
    public boolean AuthorTester(EventPost event, String author) {
        if(event.author.contains(author))
            return true;
        else
            return false;
    }
}
