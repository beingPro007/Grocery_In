package com.example.onlinegroceryapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.adapter.OrderAdapter;
import com.example.onlinegroceryapp.model.OrdersModel;
import com.example.onlinegroceryapp.model.UserManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    List<OrdersModel> orders;
    RecyclerView orderRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        orders = new ArrayList<>();
        OrderAdapter orderAdapter = new OrderAdapter(orders, getApplicationContext());
        orderRecycler = findViewById(R.id.orderRecycler);
        orderRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        orderRecycler.setAdapter(orderAdapter);

        String userId = UserManager.getUserEmail();

        if (userId != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Checkout Details").child("Orders");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    orders.clear();
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        OrdersModel ordersModel = itemSnapshot.getValue(OrdersModel.class);
                        orders.add(ordersModel);
                    }
                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "DB Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Invalid user information", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
