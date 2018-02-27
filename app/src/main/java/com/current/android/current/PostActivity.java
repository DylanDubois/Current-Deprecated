package com.current.android.current;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class PostActivity extends AppCompatActivity{
    private LatLng userLocation;
    private Spinner eventTypeSpinner;
    private Button cancelButton;
    private Button postButton;
    private EditText eventName;
    private EditText eventDescription;
    private String eventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            userLocation = new LatLng(extras.getDouble("USER_LATITUDE"),
                    extras.getDouble("USER_LONGITUDE"));
        }
        Log.d("Current", "User is at " + userLocation);
        eventTypeSpinner = (Spinner) findViewById(R.id.eventTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventTypeSpinner.setAdapter(adapter);

        //initialize post screen buttons/text fields
        cancelButton = (Button) findViewById(R.id.cancelButton);
        postButton = (Button) findViewById(R.id.postButton);
        eventName = (EditText) findViewById(R.id.eventNameText);
        eventDescription = (EditText) findViewById(R.id.eventDescriptionText);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "cancel button clicked in post class.");
                Intent cancelIntent = new Intent(getApplicationContext(), MapsActivity.class);
                finish();
                startActivity(cancelIntent);
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPressed();
            }
        });

        eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Current", "Event Type: " + parent.getItemAtPosition(position));
                eventType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventType = parent.getItemAtPosition(0).toString();
            }
        });
    }
    // Random location used for testing. Not permanent!!!
    Random random = new Random();
    private void postPressed(){
        Log.d("Current","Event name: "+eventName.getText().toString() +
                "\nEvent Description: "+eventDescription.getText().toString()+
                "\nEvent Type: " + eventType);
        EventPost eventPost = new EventPost(eventName.getText().toString(),
                // Remove!!!
                eventDescription.getText().toString(), "Mike Mikerson", new LatLng(30 + (1) *random.nextDouble(), -91 + 1 *random.nextDouble()),
                eventType);
        Log.d("Current", "Event Posted? " + eventPost.getEventName());

        // Adds directly from post class only for testing purposes.
        EventPost.eventsArray.add(eventPost);

        Intent postIntent = new Intent(getApplicationContext(), MapsActivity.class);

        finish();
        startActivity(postIntent);


    }

}
