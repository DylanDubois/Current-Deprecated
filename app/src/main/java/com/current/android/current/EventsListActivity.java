package com.current.android.current;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.ImageButton;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EventsListActivity extends AppCompatActivity {

    private ListView eventListView;
    private ImageButton goBackButton,searchButton,filterButton;
    private TextView event_name_view,event_des_view;
    private ImageView color;
    private EventAdapter adapter;

    private CharSequence[] types = new CharSequence[]{"Academic","Entertainment","Social","Other"};
    private boolean[] checked = new boolean[]{false,false,false,false};

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
        goBackButton = (ImageButton)findViewById(R.id.go_back_ImageButton);
        filterButton = (ImageButton)findViewById(R.id.filter_ImageButton);
        searchButton = (ImageButton)findViewById(R.id.search_ImageButton);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current","GoBack button pressed in the EventListActivity class");
                Intent cancelIntent = new Intent(getApplicationContext(), MapsActivity.class);
                finish();
                startActivity(cancelIntent);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current","Filter button pressed in the EventListActivity class");
                AlertDialog.Builder builder = new AlertDialog.Builder(EventsListActivity.this);
                builder.setTitle("Filter Events by");
                builder.setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),types[which],Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


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





