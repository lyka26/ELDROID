package com.example.marcellano_loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    final String TAG = "FIRESTORE";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        EditText usernameEditText, passwordEditText;
        Button loginButton, goToRegisterButton;

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        goToRegisterButton = findViewById(R.id.goToRegisterButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = usernameEditText.getText().toString();
                String passwordInput = passwordEditText.getText().toString();

                if (!usernameInput.isEmpty() && !passwordInput.isEmpty()) {
                    retrieveUser(usernameInput, passwordInput);
                } else {
                    Toast.makeText(LoginActivity.this, "Please make sure there are no empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start the new activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void retrieveUser(String usernameInput, String passwordInput) {

        db.collection("users")
                .whereEqualTo("username", usernameInput)
                .whereEqualTo("password", passwordInput)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            // no matching user found, display error message
                            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        } else {
                            // login successful, go to home screen
                            startActivity(new Intent(this, WelcomeActivity.class));
                        }
                    } else {
                        // error occurred while querying Firestore
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}