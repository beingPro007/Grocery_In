package com.example.onlinegroceryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.annotation.GlideModule;
import com.example.onlinegroceryapp.Cart;
import com.example.onlinegroceryapp.ProductDetails;
import com.example.onlinegroceryapp.R;
import com.example.onlinegroceryapp.model.RecentlyViewed;
import com.squareup.picasso.Picasso;

import java.util.List;
@GlideModule
public class RecentlyViewedAdapter extends RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedViewHolder> {
    Context context;
    List<RecentlyViewed> recentlyViewedList;

    public RecentlyViewedAdapter(Context context, List<RecentlyViewed> recentlyViewedList) {
        this.context = context;
        this.recentlyViewedList = recentlyViewedList;
    }

    @NonNull
    @Override
    public RecentlyViewedAdapter.RecentlyViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recently_viewed_items,parent,false);
        return new RecentlyViewedViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull RecentlyViewedAdapter.RecentlyViewedViewHolder holder, final int position) {
        holder.name.setText(recentlyViewedList.get(position).getName());
        holder.description.setText(recentlyViewedList.get(position).getDescription());
        holder.price.setText(recentlyViewedList.get(position).getPrice());
        holder.unit.setText(recentlyViewedList.get(position).getUnit());
        Log.d("AdapterDebug", "Item imageUrl: " + recentlyViewedList.get(position).getImageurl());
        Picasso.get().load(recentlyViewedList.get(position).getImageurl()).placeholder(R.drawable.johnny_automatic_broccoli).into(holder.bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                Intent intent1 = new Intent(context, Cart.class);
                intent1.putExtra("name",recentlyViewedList.get(position).getName());
                intent.putExtra("id",recentlyViewedList.get(position).getProdId());
                intent.putExtra("email",recentlyViewedList.get(position).getEmail());
                intent.putExtra("name",recentlyViewedList.get(position).getName());
                intent.putExtra("price",recentlyViewedList.get(position).getPrice());
                intent.putExtra("description",recentlyViewedList.get(position).getDescription());
                intent.putExtra("qty",recentlyViewedList.get(position).getQuantity());
                intent.putExtra("unit",recentlyViewedList.get(position).getUnit());
                intent.putExtra("image",recentlyViewedList.get(position).getImageurl());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return recentlyViewedList.size();
    }
    public static class RecentlyViewedViewHolder extends RecyclerView.ViewHolder{
        TextView name,description,price,quantity,unit;
        ImageView bg;
        public RecentlyViewedViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            description =  itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.cartProdprice);
            quantity = itemView.findViewById(R.id.cartProdQty);
            unit = itemView.findViewById(R.id.unit);
            bg = itemView.findViewById(R.id.product_image);
        }
    }
}