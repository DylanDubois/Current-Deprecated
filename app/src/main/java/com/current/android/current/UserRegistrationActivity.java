package com.current.android.current;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegistrationActivity extends AppCompatActivity{

    private EditText passwordText, confirmPassText, emailView, usernameView;
    private Button registerButton, cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        emailView = findViewById(R.id.user_email_text);
        usernameView = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.user_password_text);
        confirmPassText = findViewById(R.id.confirm_password_text);

        registerButton = findViewById(R.id.register_button);
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                startActivity(intent);
                finish();
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
        String confirmPassword = confirmPassText.getText().toString();
        if (!confirmPassword.equals(password) || password.length() <= 6){
            Log.d("Current","Invalid Password");
            Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
        }
        return (confirmPassword.equals(password) && password.length() > 6);
    }

    private void registerUser(){
        if (emailValid(emailView.getText().toString()) && passwordValid(passwordText.getText().toString())){
            Log.d("Current", "Email and password valid");



            // after successful login
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
