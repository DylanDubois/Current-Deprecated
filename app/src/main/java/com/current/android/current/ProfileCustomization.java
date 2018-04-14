package com.current.android.current;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ProfileCustomization extends AppCompatActivity {


    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private FirebaseAuth userAuth;
    private User userInfo;
    private TextView usernameText;
    private Button saveButton;
    private ImageView avatarImage;
    private Spinner avatarSpinner;
    private String avatarSelection = "default";
    private HashMap<String, Integer> avatarHash;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_customization);
        usernameText = findViewById(R.id.username_text);
        saveButton = findViewById(R.id.saveButton);
        avatarImage = findViewById(R.id.avatar_view);

        avatarHash = MapsActivity.createAvatarHash();

        updateUser();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewInfo();
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        avatarSpinner = findViewById(R.id.avatar_select_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.avatar_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        avatarSpinner.setAdapter(adapter);
        avatarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    avatarSelection = parent.getItemAtPosition(position).toString();
                    Log.d("Current", "New avatar selected: " + avatarSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                avatarSelection = userInfo.getAvatarType();
            }
        });

        updateUser();
    }

    private void saveNewInfo(){
        if (!avatarSelection.equals(userInfo.getAvatarType())){
            databaseReference
                    .child("users")
                    .child(currentUser.getUid())
                    .child("avatarType")
                    .setValue(avatarSelection);
            Log.d("Current", "New avatar selected!");
    }}

    private void updateUser(){
        userAuth = FirebaseAuth.getInstance();
        currentUser = userAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(User.class);
                Log.d("Current", "Profile cust. Current user: " + userInfo.getUserName());
                usernameText.setText(userInfo.getUserName());
                avatarImage.setImageResource(avatarHash.get(userInfo.getAvatarType()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
