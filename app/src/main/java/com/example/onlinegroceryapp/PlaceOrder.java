package com.example.onlinegroceryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinegroceryapp.model.Orders;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order);

        // Initializing views
        placeorderName = findViewById(R.id.placeorderName);
        placeorderAddress = findViewById(R.id.placeorderAddress);
        placeorderPhone = findViewById(R.id.placeorderPhone);
        placeorderEmail = findViewById(R.id.placeorderEmail);

        // Retrieving current user's email
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = currentUser.getEmail();
        Log.d("UserAuthentication", "User email: " + currentUserEmail);
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
                if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || email.isEmpty()) {
                    // Show error message if any field is empty
                    Toast.makeText(PlaceOrder.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with placing the order
                    DatabaseReference Source = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility");
                    DatabaseReference destination = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Checkout Details");

                    Source.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Orders orders = snapshot.getValue(Orders.class);
                            PlaceOrderModel placeOrderModel = new PlaceOrderModel(name, phone, address, email);

                            destination.setValue(placeOrderModel)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            showSuccessDialog();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(PlaceOrder.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                                            Log.e("PlaceOrder", "Error adding data to database: " + e.getMessage());
                                        }
                                    });

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("PlaceOrder", "Cancelled: " + error.getMessage());
                        }
                    });
                }
                finish();
            }

        });
    }

    // Method to show a success dialog
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        TextView textView = dialogView.findViewById(R.id.textView10);
        ImageView imageView = dialogView.findViewById(R.id.dialog_image);
        imageView.setImageResource(R.drawable.checkout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }).setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
