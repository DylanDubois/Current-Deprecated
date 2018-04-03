package com.current.android.current;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginRegisterActivity extends AppCompatActivity {

    private FirebaseAuth userAuth;
    private EditText emailText;
    private EditText password;

    //TODO: Add user account creation/login

    //TODO: Add user event post creation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        userAuth = FirebaseAuth.getInstance();


        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);

        emailText = findViewById(R.id.user_email_text);
        password = findViewById(R.id.user_password_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailValid(emailText.getText().toString()) && passwordValid(password.getText().toString()))
                    loginUser(emailText.getText().toString(), password.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private boolean emailValid(String email){
        if (!email.contains("@")){
            Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            Log.d("Current", "Email is invalid");
        }
        return email.contains("@");
    }

    private boolean passwordValid(String password){
        if (password.length() <= 6){
            Log.d("Current","Invalid Password");
            Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
        }
        return password.length() > 6;
    }

    private void loginUser(String email, String password){
        Log.d("Current","Login called\nEmail = " +email +" Password = " +password);

    }

    private void registerUser(){
        Log.d("Current", "Register Clicked");
        Intent intent = new Intent(getApplicationContext(), UserRegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}
