package com.example.pharmafind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private Button findHospitalsButton, aboutButton, profileButton, logoutButton;
    private FirebaseAuth mAuth;
    private static final String TAG = "MenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();
        welcomeTextView = findViewById(R.id.welcomeTextView);
        findHospitalsButton = findViewById(R.id.findHospitalsButton);
        aboutButton = findViewById(R.id.aboutButton);
        profileButton = findViewById(R.id.profileButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Try to fetch username from intent
        String username = getIntent().getStringExtra("username");
        Log.d(TAG, "Received username: " + username);

        if (username == null || username.isEmpty()) {
            // Fetch username from Firebase Realtime Database
            fetchUsernameFromDatabase();
        } else {
            welcomeTextView.setText("Welcome, " + username + "!");
        }

        // Set click listeners for the buttons
        findHospitalsButton.setOnClickListener(view -> startActivity(new Intent(MenuActivity.this, MapsActivity.class)));

        profileButton.setOnClickListener(view -> startActivity(new Intent(MenuActivity.this, ProfileActivity.class)));

        aboutButton.setOnClickListener(view -> startActivity(new Intent(MenuActivity.this, WebActivity.class)));

        logoutButton.setOnClickListener(v -> logoutUser());
    }

    private void fetchUsernameFromDatabase() {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        userRef.child("username").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String username = task.getResult().getValue(String.class);
                Log.d(TAG, "Fetched username: " + username);
                if (username != null) {
                    welcomeTextView.setText("Welcome, " + username + "!");
                } else {
                    welcomeTextView.setText("Welcome, Guest!");
                }
            } else {
                Log.e(TAG, "Failed to fetch username: " + task.getException().getMessage());
                Toast.makeText(MenuActivity.this, "Failed to fetch username.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutUser() {
        mAuth.signOut();
        Intent intent = new Intent(MenuActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(MenuActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
    }
}