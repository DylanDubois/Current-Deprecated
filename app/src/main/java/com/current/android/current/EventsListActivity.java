package com.current.android.current;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class EventsListActivity extends AppCompatActivity {

    private ListView eventListView;
    private Button goBackButton;
    private TextView event_name_view,event_des_view;
    private ImageView color;
    private EventAdapter adapter;
    //ArrayList to store the vents
    public static ArrayList<EventPost> eventsArray = new ArrayList<>();

    // FireBase fields
    private static DatabaseReference databaseReference;
//    private static ChildEventListener databaseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        event_name_view = (TextView)findViewById(R.id.textView_name);
        event_des_view = (TextView)findViewById(R.id.textView_description);
        eventListView= (ListView)findViewById(R.id.listView_events);
        color = (ImageView)findViewById(R.id.color_imageView);
        goBackButton = (Button)findViewById(R.id.go_back);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "cancel button clicked in post class.");
                Intent cancelIntent = new Intent(getApplicationContext(), MapsActivity.class);
                finish();
                startActivity(cancelIntent);
            }
        });

        //TODO: instantiate all layout fields here



    }


    public void onStart() {
        super.onStart();


        adapter = new EventAdapter(this,databaseReference,eventsArray);
        eventListView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("events");

    }

    public void onStop(){
        super.onStop();
        // Frees resources like MapsActivity
        adapter.cleanup();
    }

    }





