package com.example.pharmafind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText usernameEditText;
    private TextView emailTextView;
    private Button updateButton, uploadPhotoButton;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profileImage);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailTextView = findViewById(R.id.emailTextView);
        updateButton = findViewById(R.id.updateButton);
        uploadPhotoButton = findViewById(R.id.uploadPhotoButton);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String email = user.getEmail();
            emailTextView.setText(email);

            // Fetch username and profile image
            mDatabase.child("users").child(userId).get().addOnSuccessListener(dataSnapshot -> {
                String username = dataSnapshot.child("username").getValue(String.class);
                String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);

                usernameEditText.setText(username);

                // Load profile image using Glide
                if (profileImageUrl != null) {
                    Glide.with(this)
                            .load(profileImageUrl) // URL fetched from Firebase Storage
                            .placeholder(R.drawable.profile) // Placeholder image
                            .circleCrop() // Applies a circular crop to the image
                            .into(profileImageView);
                }
            });
        }

        // Upload photo button click
        uploadPhotoButton.setOnClickListener(v -> openFileChooser());

        // Update button click
        updateButton.setOnClickListener(v -> updateUserProfile());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri); // Display the selected image temporarily
        }
    }

    private void updateUserProfile() {
        String newUsername = usernameEditText.getText().toString().trim();

        if (newUsername.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = mDatabase.child("users").child(userId);

            userRef.child("username").setValue(newUsername).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Username updated successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to update username.", Toast.LENGTH_SHORT).show();
                }
            });

            if (imageUri != null) {
                uploadProfileImage(userId);
            }
        }
    }

    private void uploadProfileImage(String userId) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading image...");
        progressDialog.show();

        try {
            // Convert image to JPEG format
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Convert to JPEG format
            byte[] jpegData = baos.toByteArray();

            // Upload the JPEG data to Firebase Storage
            StorageReference fileReference = mStorage.child("profile_images/" + userId + ".jpg");
            UploadTask uploadTask = fileReference.putBytes(jpegData);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            mDatabase.child("users").child(userId).child("profileImageUrl").setValue(uri.toString());
                            Toast.makeText(ProfileActivity.this, "Profile image uploaded successfully.", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(ProfileActivity.this, "Failed to get image URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to upload image.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(e -> {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } catch (IOException e) {
            progressDialog.dismiss();
            Toast.makeText(this, "Failed to process image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
