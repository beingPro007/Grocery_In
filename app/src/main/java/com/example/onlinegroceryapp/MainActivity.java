    package com.example.onlinegroceryapp;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.FrameLayout;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.onlinegroceryapp.adapter.CategoryAdapter;
    import com.example.onlinegroceryapp.adapter.DiscountedProductAdapter;
    import com.example.onlinegroceryapp.adapter.RecentlyViewedAdapter;
    import com.example.onlinegroceryapp.model.Category;
    import com.example.onlinegroceryapp.model.Discountedproducts;
    import com.example.onlinegroceryapp.model.RecentlyViewed;
    import com.example.onlinegroceryapp.model.UserManager;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.navigation.NavigationView;
    import com.google.firebase.FirebaseApp;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Locale;
    import java.util.Set;

    public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

        RecyclerView discountedRecyclerView , categoryRecyclerView , recentlyViewedRecyclerView;
        DiscountedProductAdapter discountedProductAdapter;
        List<Discountedproducts> discountedproductsList;
        ImageView allCategory;
        CategoryAdapter categoryAdapter ;
        List<Category> categoryList;
        ImageView cartMain;
        List<RecentlyViewed> recentlyViewedList;

        private BottomNavigationView bottomNavigationView;
        private FrameLayout fragmentContainer;

        private DrawerLayout drawerLayout;
        private NavigationView navigationView;

        private ActionBarDrawerToggle actionBarDrawerToggle;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            FirebaseApp.initializeApp(this);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null){
                String email = currentUser.getEmail();
                Log.d("UserAuthentication", "User email: " + email);
                String[] parts = email.split("@");
                String finalEmail = parts[0];
                UserManager.setUserEmail(finalEmail);
            }
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Set up the navigation drawer
            drawerLayout = findViewById(R.id.drawer_layout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setCustomView(R.layout.navbar);
            navigationView = findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);

            // Access views within the header view
            TextView textViewUsername = headerView.findViewById(R.id.textViewUsername);
            TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);
            // Set text or perform other logic
            textViewUsername.setText(UserManager.getUserEmail().toUpperCase(Locale.ROOT));
            assert currentUser != null;
            textViewEmail.setText(currentUser.getEmail());
            navigationView.setNavigationItemSelectedListener(this);

            // Check Firebase Authentication state
            //all recycler View....
            discountedRecyclerView = findViewById(R.id.discountedRecyler);
            categoryRecyclerView = findViewById(R.id.categoryRecyler);
            recentlyViewedRecyclerView = findViewById(R.id.recentRecycler);

            //all array list instantiation....
            discountedproductsList = new ArrayList<>();
            categoryList = new ArrayList<>();
            recentlyViewedList = new ArrayList<>();


            //Top Discovered Items
            discountedproductsList.add(new Discountedproducts(1, R.drawable.discountberry));
            discountedproductsList.add(new Discountedproducts(2, R.drawable.discountbrocoli));
            discountedproductsList.add(new Discountedproducts(3, R.drawable.discountmeat));
            discountedproductsList.add(new Discountedproducts(4, R.drawable.discountberry));
            discountedproductsList.add(new Discountedproducts(5, R.drawable.discountbrocoli));

            //select Categories
            categoryList.add(new Category(1, R.drawable.ic_home_fruits));
            categoryList.add(new Category(1, R.drawable.ic_home_meats));
            categoryList.add(new Category(1, R.drawable.ic_home_veggies));
            categoryList.add(new Category(1, R.drawable.ic_home_fish));
            categoryList.add(new Category(1, R.drawable.ic_home_fruits));

            //Recently Viewed Items

            //Recently Viewed Items
            setRecentlyViewedRecycler(recentlyViewedList);
            //Top Discovered Items
            setDiscountedRecycler(discountedproductsList);
            //select Categories
            setCategoryRecycler(categoryList);
            //all caategory intent creation...
            allCategory = findViewById(R.id.allCategoryImage);
            allCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AllCategory.class);
                    startActivity(intent);
                }
            });
            // Write a message to the database
            cartMain = findViewById(R.id.cartMain);
            cartMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, Cart.class));
                }
            });
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.home) {

                if (this.getClass() == MainActivity.class) {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            } else if (id == R.id.profile) {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                if (getApplicationContext().getClass() == ProfileActivity.class) {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            } else if (id == R.id.logOut) {
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, UserAuthentication.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.orders) {
                startActivity(new Intent(getApplicationContext(),OrdersActivity.class));
            }
            else if (id == R.id.admin){
                startActivity(new Intent(getApplicationContext(), AdminLoginActivity.class));
            }
            drawerLayout.closeDrawers();
            return true;
        }
        //Top Discovered Items
        private void setRecentlyViewedRecycler(List<RecentlyViewed> recentlyViewedList) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recentlyViewedRecyclerView.setLayoutManager(layoutManager);

            String email = new SessionManager(this).getUserEmail();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Fruits");

            // Use a Set to ensure uniqueness
            Set<RecentlyViewed> uniqueItems = new HashSet<>();

            // Use addListenerForSingleValueEvent instead of addValueEventListener
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    uniqueItems.clear(); // Clear the set before adding new data
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        // Retrieve data from dataSnapshot and create RecentlyViewed object
                        String imageUrl = itemSnapshot.child("imageUrl").getValue(String.class);
                        String price = itemSnapshot.child("price").getValue(String.class);
                        String prodId = itemSnapshot.child("prodId").getValue(String.class);
                        String productName = itemSnapshot.child("productName").getValue(String.class);
                        String quantity = itemSnapshot.child("quantity").getValue(String.class);
                        String Description = itemSnapshot.child("Description").getValue(String.class);

                        RecentlyViewed recentlyViewed = new RecentlyViewed();

                        recentlyViewed.setImageurl(imageUrl);
                        recentlyViewed.setPrice(price);
                        recentlyViewed.setProdId(prodId);
                        recentlyViewed.setName(productName);
                        recentlyViewed.setQuantity(quantity);
                        recentlyViewed.setDescription(Description);
                        // Add the item to the Set
                        uniqueItems.add(recentlyViewed);
                    }
                    // Convert the Set back to a List
                    recentlyViewedList.clear();
                    recentlyViewedList.addAll(uniqueItems);

                    // Update RecyclerView adapter outside the onDataChange method
                    RecentlyViewedAdapter recentlyViewedAdapter = new RecentlyViewedAdapter(MainActivity.this, recentlyViewedList);
                    recentlyViewedRecyclerView.setAdapter(recentlyViewedAdapter);
                    recentlyViewedAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "DB Error", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //select Categories
        private void setDiscountedRecycler(List<Discountedproducts> datalist) {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            discountedRecyclerView.setLayoutManager(layoutManager);

            discountedProductAdapter = new DiscountedProductAdapter(this,datalist);
            discountedRecyclerView.setAdapter(discountedProductAdapter);
        }
    //Recently Viewed Items
        private void setCategoryRecycler(List<Category> categoryList) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            categoryRecyclerView.setLayoutManager(layoutManager);
            categoryAdapter = new CategoryAdapter(this,categoryList);
            categoryRecyclerView.setAdapter(categoryAdapter);
        }
        @Override
        public void onBackPressed() {
            // Display a toast message indicating the app is closing
            super.onBackPressed();
            Toast.makeText(this, "Closing the application", Toast.LENGTH_SHORT).show();

            // Finish all activities in the task associated with MainActivity
            finishAffinity();
        }
    }