package com.example.onlinegroceryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onlinegroceryapp.model.Orders;
import com.example.onlinegroceryapp.model.UserManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    ImageView img;
    Button buyNow;
    int bigimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        Intent intent = getIntent();
        String productId = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        String description = intent.getStringExtra("description");
        String bigImage = intent.getStringExtra("image");

        img = findViewById(R.id.img);

        String userId =intent.getStringExtra("email");
        String quantity = intent.getStringExtra("qty");
        TextView prodname = findViewById(R.id.productName);
        TextView prodprice = findViewById(R.id.prodPrice);
        TextView proddescription = findViewById(R.id.desc);
        Picasso.get().load(Uri.parse(bigImage)).into(img);
        prodname.setText(name);
        prodprice.setText(price);
        proddescription.setText(description);
        ImageView backButtonProductDetails = findViewById(R.id.productDetailsBackButton);
        buyNow = findViewById(R.id.buyNow);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PlaceOrder.class));
            }
        });
        backButtonProductDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetails.this, MainActivity.class);
                startActivity(i);
            }
        });

        ImageView cartProd = findViewById(R.id.addtocartProd);
        cartProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = UserManager.getUserEmail();

                if (userId != null) {
                    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility");

                    if (name != null) {
                        Toast.makeText(ProductDetails.this, "Added To Cart Successfully. Enjoy!", Toast.LENGTH_SHORT).show();

                        // Save the image URL to the Realtime Database
                        Orders product = new Orders(productId, name, quantity, price,bigImage);
                        cartRef.child(name).setValue(product);
                    } else {
                        // Handle the case where productId is null
                        Toast.makeText(ProductDetails.this, "Failed to add to cart. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where userId is null
                    Toast.makeText(ProductDetails.this, "Failed to add to cart. User ID not available.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView cartMain = findViewById(R.id.cartMain);
        cartMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, Cart.class));
            }
        });
    }
}