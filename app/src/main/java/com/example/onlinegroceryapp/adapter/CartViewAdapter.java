package com.example.onlinegroceryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegroceryapp.R;
import com.example.onlinegroceryapp.model.Orders;
import com.example.onlinegroceryapp.model.UserManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.ViewHolder> {
    Context context;
    List<Orders> cartViewList;

    public CartViewAdapter(Context context, List<Orders> cartViewList) {
        this.context = context;
        this.cartViewList = cartViewList;
    }


    @NonNull
    @Override
    public CartViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.ViewHolder holder, int position) {
        Picasso.get().load(cartViewList.get(position).getImageUrl()).into(holder.cartImage);
        holder.cartproductname.setText(cartViewList.get(position).getProductName());
        holder.cartproductqty.setText(cartViewList.get(position).getQuantity());
        holder.cartproductprice.setText(cartViewList.get(position).getPrice());

        String userId = UserManager.getUserEmail();
        String prodName = cartViewList.get(position).getProductName();

        int total = Integer.parseInt(cartViewList.get(position).getQuantity().replaceAll("[^0-9]", "")) * Integer.parseInt(cartViewList.get(position).getPrice().replaceAll("[^0-9]", ""));

        UserManager userManager = new UserManager();
        userManager.setTotal(total);
        String ans = String.valueOf(total);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility");
        databaseReference.child(prodName).child("Totals").setValue(Integer.parseInt(ans));

        DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility");

        userCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int grandTotal = 0;

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    int quantity = Integer.parseInt(productSnapshot.child("quantity").getValue(String.class));
                    int pricePerKg = Integer.parseInt(productSnapshot.child("price").getValue(String.class).replaceAll("[^0-9]", ""));
                    int total = quantity * pricePerKg;
                    grandTotal += total;
                }
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
                userRef.child("Grand Total").setValue(String.valueOf(grandTotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Error occurred while fetching data.", Toast.LENGTH_SHORT).show();
            }
        });


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility").child(prodName);
                int quan = Integer.parseInt(cartViewList.get(position).getQuantity());
                quan++;
                holder.cartproductqty.setText(String.valueOf(quan));
                    databaseReference.child("quantity").setValue(String.valueOf(quan));
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility").child(prodName);
                int quan = Integer.parseInt(cartViewList.get(position).getQuantity());
                if (quan > 1){
                    quan--;
                    holder.cartproductqty.setText(String.valueOf(quan));
                    databaseReference.child("quantity").setValue(String.valueOf(quan));
                }
                else if (quan == 1){
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Cart Facility").child(prodName);
                    databaseReference1.removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(context, prodName+" removed from your cart", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartViewList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cartproductname,cartproductqty,cartproductprice;
        ImageView cartImage,plus,minus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.cartImage);
            cartproductname = itemView.findViewById(R.id.cartProdName);
            cartproductprice = itemView.findViewById(R.id.cartProdprice);
            cartproductqty = itemView.findViewById(R.id.cartProdQty);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);

        }
    }
}
