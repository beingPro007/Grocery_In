package com.example.onlinegroceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLoginActivity extends AppCompatActivity {
    private static final String TAG = "AdminLoginActivity";

    private EditText emailEditText;
    private EditText passwordEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();

        // Handle login button click
        findViewById(R.id.adminlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =
                        emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                findViewById(R.id.adminlogin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = emailEditText.getText().toString();
                        String password = passwordEditText.getText().toString();

                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(AdminLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, check if the user is an admin
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            db.collection("admins").document(email).get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> adminTask) {
                                                            if (adminTask.isSuccessful()) {
                                                                DocumentSnapshot document = adminTask.getResult();
                                                                if (document.exists()) {
                                                                    // User is an admin, grant access
                                                                    // Navigate to admin dashboard or perform admin tasks
                                                                    startActivity(new Intent(AdminLoginActivity.this, AdminPanel.class));
                                                                } else {
                                                                    // User is not an admin, display error message
                                                                    Toast.makeText(AdminLoginActivity.this, "Admin not found! Contact Developer", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                // Failed to query admin document
                                                                Log.d(TAG, "Failed to query admin document: ", adminTask.getException());
                                                            }
                                                        }
                                                    });
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                            Toast.makeText(AdminLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

            }
        });
    }
}
