package com.example.onlinegroceryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.R;
import com.example.onlinegroceryapp.adminAddProduct;
import com.example.onlinegroceryapp.model.adminPanelModel;

import java.util.List;

public class AdminPanelAdapter extends RecyclerView.Adapter<AdminPanelAdapter.ViewHolder>{
    Context context;
    List<adminPanelModel> adminPanelModelList ;

    public AdminPanelAdapter(Context context, List<adminPanelModel> adminPanelModelList) {
        this.context = context;
        this.adminPanelModelList = adminPanelModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_panel_style,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.adminImage.setImageURI(adminPanelModelList.get(position).getImageUrl());
        holder.adminName.setText(adminPanelModelList.get(position).getProdType());
        holder.adminImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the data associated with the clicked item
                adminPanelModel selectedItem = adminPanelModelList.get(position);

                // Create an intent to pass data to the next screen
                Intent intent = new Intent(context, adminAddProduct.class);
                // Pass data using intent extras
                intent.putExtra("imageUrl", selectedItem.getImageUrl());
                intent.putExtra("productName", selectedItem.getProdType());
                // Start the next activity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminPanelModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView adminImage;
        TextView adminName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adminImage = itemView.findViewById(R.id.adminImage);
            adminName = itemView.findViewById(R.id.adminName);
        }
    }
}
