package com.current.android.current;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventsListActivity extends AppCompatActivity {

    private static ArrayList<EventPost> eventsArray = new ArrayList<>();

    // FireBase fields
    private static DatabaseReference databaseReference;
    private static ChildEventListener databaseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);


        //TODO: instantiate all layout fields here



    }

    public void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("events");

        initEventsArray();
    }

    public void onStop(){
        super.onStop();

        // Frees resources like MapsActivity
        databaseReference.removeEventListener(databaseListener);
    }

    /*
     *  This will search FireBase and fill an arraylist with new events.
     */
    public void initEventsArray(){
        databaseListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot == null) return;
                EventPost event = dataSnapshot.getValue(EventPost.class);
                if (!eventsArray.contains(event)) eventsArray.add(event);

                Log.d("Current", "Event found! Event name: " + event.getEventName());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.orderByChild("events").addChildEventListener(databaseListener);}
    }



