package com.example.onlinegroceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class UserAuthentication extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView loginEmail, loginPassword;
    private Button loginButton, registerButtonLogin;
    private ProgressBar loginProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is already authenticated, redirect to main activity
            startActivity(new Intent(UserAuthentication.this, MainActivity.class));
            finish(); // Finish the login activity
        }

        // Initialize views
        loginProgress = findViewById(R.id.loginProgress);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButon);
        registerButtonLogin = findViewById(R.id.registerButonlogin);

        // Check if the user is already logged in

        // Login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(UserAuthentication.this, "Enter Email and Password!", Toast.LENGTH_SHORT).show();
                } else {
                    loginProgress.setVisibility(View.VISIBLE);
                    // Sign in user with Firebase Auth
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(UserAuthentication.this, task -> {
                                loginProgress.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startActivity(new Intent(UserAuthentication.this, MainActivity.class));
                                    finish(); // Close the LoginActivity
                                } else {
                                    // If sign in fails, display a message to the user.
                                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                        // Handle invalid user (user not registered)
                                        Toast.makeText(UserAuthentication.this, "User not registered. Please sign up.", Toast.LENGTH_SHORT).show();
                                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        // Handle invalid credentials (wrong password)
                                        Toast.makeText(UserAuthentication.this, "Invalid password. Please try again.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle other exceptions
                                        Toast.makeText(UserAuthentication.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        // Register button click listener
        registerButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAuthentication.this, UserRegistration.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if the user is already authenticated
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is already authenticated, redirect to main activity
            startActivity(new Intent(UserAuthentication.this, MainActivity.class));
            finish(); // Finish the login activity
        }
    }
}
