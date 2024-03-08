package com.example.onlinegroceryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.R;
import com.example.onlinegroceryapp.model.OrdersModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<OrdersModel> ordersModels;
    Context context;

    public OrderAdapter(List<OrdersModel> ordersModels, Context context) {
        this.ordersModels = ordersModels;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_order_styles,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Picasso.get().load(ordersModels.get(position).getImageUrl()).into(holder.orderImage);
        holder.orderNamestyle.setText(ordersModels.get(position).getProductName());
        holder.orderPrice.setText(ordersModels.get(position).getPrice());
        holder.orderQuantity.setText(ordersModels.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return ordersModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView orderImage;
        TextView orderNamestyle,orderQuantity,orderPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImage = itemView.findViewById(R.id.orderImage);
            orderNamestyle = itemView.findViewById(R.id.orderNamestyle);
            orderQuantity = itemView.findViewById(R.id.orderQuantity);
            orderPrice = itemView.findViewById(R.id.orderPrice);

        }
    }
}
