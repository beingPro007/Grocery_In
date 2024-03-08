package com.example.onlinegroceryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.R;
import com.example.onlinegroceryapp.model.allCategoryModel;
import com.example.onlinegroceryapp.productSubCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {
    Context context;
    List<allCategoryModel> allCategoryModelList;

    public AllCategoryAdapter(Context context, List<allCategoryModel> allCategoryModelList) {
        this.context = context;
        this.allCategoryModelList = allCategoryModelList;
    }

    @NonNull
    @Override
    public AllCategoryAdapter.AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_category_row_items, parent, false);
        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryAdapter.AllCategoryViewHolder holder, int position) {
        Picasso.get().load(allCategoryModelList.get(position).getImageUrl()).into(holder.allCategoryImage);
        String Type = allCategoryModelList.get(position).getType();
        holder.allCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,productSubCategory.class));
                Intent intent = new Intent(context,productSubCategory.class);
                intent.putExtra("Type", Type);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allCategoryModelList.size();
    }

    public static class AllCategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView allCategoryImage;
        public AllCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            allCategoryImage = itemView.findViewById(R.id.allCategoryImage);
        }
    }
}
