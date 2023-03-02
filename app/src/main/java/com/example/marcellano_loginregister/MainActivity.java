package com.example.marcellano_loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    final String TAG = "FIRESTORE";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        EditText fullnameEditText, usernameEditText, ageEditText, addressEditText, passwordEditText;
        Button registerButton, goToLoginButton;

        fullnameEditText = findViewById(R.id.fullnameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        addressEditText = findViewById(R.id.addressEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        goToLoginButton = findViewById(R.id.goToLoginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullnameInput = fullnameEditText.getText().toString();
                String usernameInput = usernameEditText.getText().toString();
                String ageInput = ageEditText.getText().toString();
                String addressInput = addressEditText.getText().toString();
                String passwordInput = passwordEditText.getText().toString();

                if (!fullnameInput.isEmpty() && !usernameInput.isEmpty() && !ageInput.isEmpty() && !addressInput.isEmpty() && !passwordInput.isEmpty()) {
                    addUser(fullnameInput, usernameInput, ageInput, addressInput, passwordInput);
                } else {
                    Toast.makeText(MainActivity.this, "Please make sure there are no empty fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start the new activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addUser(String fullnameInput, String usernameInput, String ageInput, String addressInput, String passwordInput)
    {
        Map<String, Object> user = new HashMap<>();
        user.put("fullname", fullnameInput);
        user.put("username", usernameInput);
        user.put("age", ageInput);
        user.put("address", addressInput);
        user.put("password", passwordInput);

        // Add a new document with a generated ID
        db.collection("users").document(usernameInput)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + usernameInput);
                        Toast.makeText(MainActivity.this,"Successfully Added " + usernameInput, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Error adding user " + e, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Error adding document", e);
                    }
                });
    }
}
