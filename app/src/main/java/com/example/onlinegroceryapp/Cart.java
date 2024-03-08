package com.example.onlinegroceryapp;import android.content.Intent;import android.os.Bundle;import android.util.Log;import android.view.View;import android.widget.Button;import android.widget.TextView;import android.widget.Toast;import androidx.annotation.NonNull;import androidx.appcompat.app.AppCompatActivity;import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;import com.example.onlinegroceryapp.adapter.CartViewAdapter;import com.example.onlinegroceryapp.model.Orders;import com.example.onlinegroceryapp.model.UserManager;import com.google.firebase.database.DataSnapshot;import com.google.firebase.database.DatabaseError;import com.google.firebase.database.DatabaseReference;import com.google.firebase.database.FirebaseDatabase;import com.google.firebase.database.ValueEventListener;import java.util.ArrayList;import java.util.List;public class Cart extends AppCompatActivity {    TextView Total;    Button btnPlaceorder;    String grandTotal;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_cart);        Total = findViewById(R.id.total);        String userId = UserManager.getUserEmail();        if (userId != null) {            List<Orders> ordersList = new ArrayList<>();            CartViewAdapter cartViewAdapter = new CartViewAdapter(Cart.this, ordersList);            RecyclerView cartRecycler = findViewById(R.id.cartRecycler);            cartRecycler.setLayoutManager(new LinearLayoutManager(Cart.this));            cartRecycler.setAdapter(cartViewAdapter);            DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("users")                    .child(userId);            cartReference.child("Cart Facility").addValueEventListener(new ValueEventListener() {                @Override                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {                    ordersList.clear();                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {                        Orders orders = itemSnapshot.getValue(Orders.class);                        ordersList.add(orders);                    }                    cartViewAdapter.notifyDataSetChanged();                }                @Override                public void onCancelled(@NonNull DatabaseError databaseError) {                    Toast.makeText(Cart.this, "DB Error", Toast.LENGTH_SHORT).show();                }            });        } else {            Toast.makeText(this, "Invalid user information", Toast.LENGTH_SHORT).show();            finish();            return;        }        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);        databaseReference.addValueEventListener(new ValueEventListener() {            @Override            public void onDataChange(@NonNull DataSnapshot snapshot) {                String grandTotal = snapshot.child("Grand Total").getValue(String.class);                if (!snapshot.exists()) {                    Total.setText("0");                } else {                    Total.setText(grandTotal);                }            }            @Override            public void onCancelled(@NonNull DatabaseError error) {                Log.e("Firebase", "Error fetching Grand Total: " + error.getMessage());            }        });        btnPlaceorder = findViewById(R.id.btnPlaceorder);        btnPlaceorder.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                // Start the PlaceOrder activity                startActivity(new Intent(Cart.this, PlaceOrder.class));                // Move items from cart to checkout details                DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("users").child(userId);                cartReference.child("Cart Facility").addListenerForSingleValueEvent(new ValueEventListener() {                    @Override                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {                        // Clear the checkout details before adding new items                        DatabaseReference checkoutReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Checkout Details").child("Orders");                        checkoutReference.removeValue();                        // Loop through the cart items and add them to the checkout details                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {                            Orders orders = itemSnapshot.getValue(Orders.class);                            String prodName = itemSnapshot.getKey();                            assert prodName != null;                            checkoutReference.child(prodName).setValue(orders);                            // You may need to update the total here if needed                        }                    }                    @Override                    public void onCancelled(@NonNull DatabaseError databaseError) {                        Toast.makeText(Cart.this, "DB Error", Toast.LENGTH_SHORT).show();                    }                });            }        });    }}