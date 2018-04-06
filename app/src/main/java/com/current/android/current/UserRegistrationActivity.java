package com.current.android.current;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserRegistrationActivity extends AppCompatActivity{

    private EditText passwordText, confirmPassText, emailView, usernameView;
    private Button registerButton, cancelButton;
    private FirebaseAuth userAuth;
    private String email, password;
    public static final String SETTINGS_PREFS = "Settings", USERNAME_KEY = "username";


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
                registerUser(emailView.getText().toString(), passwordText.getText().toString());
            }
        });

        userAuth = FirebaseAuth.getInstance();

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

    private void registerUser(String email, String password){
        if (emailValid(email) && passwordValid(password)){
            Log.d("Current", "Email and password valid");
            createFirebaseUser(email,password);


            // after successful login
//            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//            startActivity(intent);
//            finish();
        }


    }

    private void createFirebaseUser(String email, String password){
        userAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Current","createFirebaseUser onComplete = " +task.isSuccessful());

                if (!task.isSuccessful()){
                    //Toast.makeText(getApplicationContext(),"Could not create account.",Toast.LENGTH_SHORT).show();
                    showErrorDialog("Cannot create account.");
                }
                else{
                    saveUserName();
                    Intent intent = new Intent(UserRegistrationActivity.this, LoginRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    public void saveUserName(){
        String userName = usernameView.getText().toString();
        SharedPreferences prefs = getSharedPreferences(SETTINGS_PREFS, 0);
        prefs.edit().putString(USERNAME_KEY, userName).apply();
    }

    private void showErrorDialog(String message){
        new AlertDialog.Builder(this).setTitle("Sorry").setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}
