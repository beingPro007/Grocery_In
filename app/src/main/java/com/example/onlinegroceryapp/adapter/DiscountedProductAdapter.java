package com.example.onlinegroceryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.R;
import com.example.onlinegroceryapp.model.Discountedproducts;

import java.util.List;

public class DiscountedProductAdapter extends RecyclerView.Adapter<DiscountedProductAdapter.DiscountedProductViewHolder> {
    Context context ;
    List<Discountedproducts> discountedproductsList;

    public DiscountedProductAdapter(Context context, List<Discountedproducts> datalist) {
        this.context = context;
        this.discountedproductsList = datalist;
    }

    @NonNull
    @Override
    public DiscountedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discounted_row_items,parent,false);
        return new DiscountedProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountedProductViewHolder holder, int position) {
        holder.discountedimageview.setImageResource(discountedproductsList.get(position).getImageurl());
    }

    @Override
    public int getItemCount() {
        return discountedproductsList.size();
    }

    public static class DiscountedProductViewHolder extends RecyclerView.ViewHolder{
        ImageView discountedimageview;
        public DiscountedProductViewHolder(@NonNull View itemView) {
            super(itemView);
            discountedimageview = itemView.findViewById(R.id.recentlyViewedImage);
        }
    }
}
