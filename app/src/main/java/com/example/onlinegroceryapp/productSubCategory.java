package com.example.onlinegroceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.adapter.ProductSubCategoryAdapter;
import com.example.onlinegroceryapp.model.SubCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class productSubCategory extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView subCategoryBackButton,cartSubCategory;
    List<SubCategoryModel> subCategoryModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_sub_category);
        recyclerView = findViewById(R.id.sbCategoryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Retrieve the string value from the intent
        String receivedValue = getIntent().getStringExtra("Type");
        if(receivedValue !=null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("All Category").child("Products").child(receivedValue).child("Product Sub Category");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        String imageUrl = itemSnapshot.child("imageUrl").getValue(String.class);
                        String productPrice = itemSnapshot.child("price").getValue(String.class);
                        String productName = itemSnapshot.child("productName").getValue(String.class);
                        String productQuantity = itemSnapshot.child("quantity").getValue(String.class);

                        SubCategoryModel subCategoryModel = new SubCategoryModel(imageUrl, productName, productQuantity, productPrice);
                        subCategoryModelList.add(subCategoryModel);
                    }


                    ProductSubCategoryAdapter productSubCategoryAdapter = new ProductSubCategoryAdapter(productSubCategory.this, subCategoryModelList);
                    recyclerView.setAdapter(productSubCategoryAdapter);
                    productSubCategoryAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(productSubCategory.this, "DB Error", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            finish();
        }
        subCategoryBackButton = findViewById(R.id.subCategoryBackButton);
        subCategoryBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cartSubCategory = findViewById(R.id.cartSubCategory);
        cartSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(productSubCategory.this,Cart.class));
            }
        });
    }

}