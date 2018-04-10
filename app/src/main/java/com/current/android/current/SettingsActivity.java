package com.current.android.current;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

    private Button loginButton,backButton, logoutButton, avatarButton;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        loginButton = findViewById(R.id.login_button);
        backButton = findViewById(R.id.back_button);
        logoutButton = findViewById(R.id.logout_button);
        avatarButton = findViewById(R.id.avatar_button);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "Login pressed in Settings");
                Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "Back button clicked in settings class.");
                Intent cancelIntent = new Intent(getApplicationContext(), MapsActivity.class);
                finish();
                startActivity(cancelIntent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "Logout pressed");
                FirebaseAuth userAuth = FirebaseAuth.getInstance();
                userAuth.signOut();
                Intent logout = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                finish();
                startActivity(logout);
            }
        });

        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Current", "Avatar pressed");
                //PopupWindowCreator.createAvatarPopup((FrameLayout) findViewById(R.id.settings), getApplicationContext());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                databaseReference.child("users").child(user.getUid()).setValue(new User("bill", "duck"));
            }
        });
    }
}
