package com.current.android.current;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {
    private ArrayList<EventPost> mEventPostArrayList;
    private Button goBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.event_loading);
        mEventPostArrayList = EventPost.eventsArray;
        goBackButton = (Button)findViewById(R.id.back);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "go back button pressed");
                Intent goBackIntent = new Intent(getApplicationContext(), MapsActivity.class);
                finish();
                startActivity(goBackIntent);
            }
        });*/

    }

}
