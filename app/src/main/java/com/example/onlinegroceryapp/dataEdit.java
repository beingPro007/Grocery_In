package com.example.onlinegroceryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinegroceryapp.model.UserManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class dataEdit extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_edit);

        imageView = findViewById(R.id.imageUpload);
        storageReference = FirebaseStorage.getInstance().getReference();

        imageView.setOnClickListener(v -> openImagePicker());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String userId = UserManager.getUserEmail();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            if (imageUri != null) {
                StorageReference fileReference = storageReference.child("Uploads").child(userId).child("Profile Photo" + System.currentTimeMillis() + ".jpg");
                fileReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Image uploaded successfully
                            Toast.makeText(dataEdit.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Error occurred during upload
                            Toast.makeText(dataEdit.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage() {

    }
}