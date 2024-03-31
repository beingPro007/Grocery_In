package com.example.onlinegroceryapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.adapter.AdminPanelAdapter;
import com.example.onlinegroceryapp.model.adminPanelModel;

import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends AppCompatActivity {
    List<adminPanelModel> adminPanelModelList;
    RecyclerView adminRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_panel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adminPanelModelList = new ArrayList<>();
        addAdminPanelModel(R.drawable.ic_fruits, "Fruits");
        addAdminPanelModel(R.drawable.ic_juce, "Juice");
        addAdminPanelModel(R.drawable.ic_cookies, "Cookies");
        addAdminPanelModel(R.drawable.ic_veggies, "Vegetables");

        adminRecycler = findViewById(R.id.adminRecycler);
        adminRecycler.setLayoutManager(new GridLayoutManager(AdminPanel.this,2));
        AdminPanelAdapter adminPanelAdapter = new AdminPanelAdapter(this,adminPanelModelList);
        adminRecycler.setAdapter(adminPanelAdapter);
    }

    private void addAdminPanelModel(int drawableId, String productType) {
        Uri imageUri = getDrawableUri(drawableId);
        adminPanelModelList.add(new adminPanelModel(imageUri, productType));
    }

    // Helper function to convert drawable resource ID to URI
    private Uri getDrawableUri(int drawableId) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + drawableId);
    }
}