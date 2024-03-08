package com.example.onlinegroceryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.R;
import com.example.onlinegroceryapp.model.SubCategoryModel;
import com.example.onlinegroceryapp.model.UserManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductSubCategoryAdapter extends RecyclerView.Adapter<ProductSubCategoryAdapter.ViewHolder> {
    Context context;
    List<SubCategoryModel> subCategoryModelList;

    public ProductSubCategoryAdapter(Context context, List<SubCategoryModel> subCategoryModelList) {
        this.context = context;
        this.subCategoryModelList = subCategoryModelList;
    }

    @NonNull
    @Override
    public ProductSubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_category_styles,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSubCategoryAdapter.ViewHolder holder, int position) {
        Picasso.get().load(subCategoryModelList.get(position).getImageUrl()).placeholder(R.drawable.johnny_automatic_broccoli).into(holder.ProductImage);
        holder.ProductName.setText(subCategoryModelList.get(position).getProductName());
        holder.ProductQuantity.setText(subCategoryModelList.get(position).getQuantity());
        holder.ProductPrice.setText(subCategoryModelList.get(position).getPrice());
        String userId = UserManager.getUserEmail();

        holder.addtocartSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility");
                String prodImage = subCategoryModelList.get(position).getImageUrl();
                String prodname = subCategoryModelList.get(position).getProductName();
                String prodQuantity = subCategoryModelList.get(position).getQuantity();
                String prodPrice = subCategoryModelList.get(position).getPrice();
                SubCategoryModel subCategoryModel = new SubCategoryModel(prodImage,prodname,prodQuantity,prodPrice);
                databaseReference.child(prodname).setValue(subCategoryModel);
                Toast.makeText(context, prodname+" Added To Your Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage,addtocartSubCategory;

        TextView ProductName,ProductQuantity,ProductPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage = itemView.findViewById(R.id.productImage);
            ProductName = itemView.findViewById(R.id.productNamestyle);
            ProductQuantity= itemView.findViewById(R.id.productQuantity);
            ProductPrice = itemView.findViewById(R.id.productPrice);
            addtocartSubCategory = itemView.findViewById(R.id.addtocartSubCategory);

        }
    }
}
