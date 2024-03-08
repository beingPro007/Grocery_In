package com.example.onlinegroceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.adapter.AllCategoryAdapter;
import com.example.onlinegroceryapp.model.allCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllCategory extends AppCompatActivity {
    RecyclerView allCategoryRecyclerView;
    ImageView allCategoryBackButton;
    List<allCategoryModel> allCategoryModelList;
    AllCategoryAdapter allCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
        allCategoryModelList = new ArrayList<>();

        allCategoryRecyclerView = findViewById(R.id.allCategoryRecycler);
        allCategoryRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        allCategoryAdapter = new AllCategoryAdapter(this, allCategoryModelList);
        allCategoryRecyclerView.setAdapter(allCategoryAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("All Category").child("Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allCategoryModelList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String imageUrl = itemSnapshot.child("imageurl").getValue(String.class);
                    String type = itemSnapshot.child("Type").getValue(String.class);

                    allCategoryModel allCategoryModel = new allCategoryModel(imageUrl, type);
                    allCategoryModelList.add(allCategoryModel);
                }
                allCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AllCategory.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        allCategoryBackButton = findViewById(R.id.subCategoryBackButton);
        allCategoryBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllCategory.this, MainActivity.class));
            }
        });
        ImageView cartMain = findViewById(R.id.cartMain);
        cartMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
            }
        });
    }
}
