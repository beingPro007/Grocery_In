package com.example.onlinegroceryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinegroceryapp.model.PlaceOrderModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlaceOrder extends AppCompatActivity {
    EditText placeorderName, placeorderPhone, placeorderAddress, placeorderEmail;
    Button Done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        // Initializing views
        placeorderName = findViewById(R.id.placeorderName);
        placeorderAddress = findViewById(R.id.placeorderAddress);
        placeorderPhone = findViewById(R.id.placeorderPhone);
        placeorderEmail = findViewById(R.id.placeorderEmail);

        // Retrieving current user's email
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // If user is not authenticated, redirect to login
            startActivity(new Intent(this, UserAuthentication.class));
            finish();
            return;
        }
        String currentUserEmail = currentUser.getEmail();
        String[] parts = currentUserEmail.split("@");
        String userId = parts[0];

        // Setting up click listener for Done button
        Done = findViewById(R.id.Done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieving user input data
                String name = placeorderName.getText().toString();
                String phone = placeorderPhone.getText().toString();
                String address = placeorderAddress.getText().toString();
                String email = placeorderEmail.getText().toString();

                // Check if any of the fields are empty
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(email)) {
                    // Show error message if any field is empty
                    Toast.makeText(PlaceOrder.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with placing the order
                    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility");
                    DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Checkout Details");

                    // Retrieve cart data
                    cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Check if cart is not empty
                            if (snapshot.exists()) {
                                PlaceOrderModel placeOrderModel = new PlaceOrderModel(name, phone, address, email);
                                // Move cart items to checkout details
                                orderRef.setValue(placeOrderModel)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Show success dialog
                                                showSuccessDialog();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Show error message on failure
                                                Toast.makeText(PlaceOrder.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                                                Log.e("PlaceOrder", "Error placing order: " + e.getMessage());
                                            }
                                        });
                            } else {
                                // Show error message if cart is empty
                                Toast.makeText(PlaceOrder.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("PlaceOrder", "Cancelled: " + error.getMessage());
                        }
                    });
                }
            }
        });
    }

    // Method to show a success dialog
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        ImageView imageView = dialogView.findViewById(R.id.dialog_image);
        imageView.setImageResource(R.drawable.checkout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirect to main activity
                startActivity(new Intent(PlaceOrder.this, CheckoutActivity.class));
                finish();
            }
        }).setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
