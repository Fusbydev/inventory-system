package com.example.practicecrude;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class transactionAdapter extends RecyclerView.Adapter<transactionAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> productID;
    private ArrayList<String> productName;
    private ArrayList<String> productQuantity;
    private ArrayList<String> productPrice;
    DBHelper db;
    int prodQuant = 0;

    public transactionAdapter(Context context, ArrayList<String> productID, ArrayList<String> productName, ArrayList<String> productQuantity, ArrayList<String> productPrice) {
        this.context = context;
        this.productID = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        db = new DBHelper(context);
    }

    @NonNull
    @Override
    public transactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.transaction_product, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull transactionAdapter.MyViewHolder holder, int position) {
        holder.product_id.setText(productID.get(position));
        holder.product_name.setText(productName.get(position));
        holder.product_quantity.setText(productQuantity.get(position));
        holder.product_price.setText(productPrice.get(position));


        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //may error pa na iincrement yung value ng ibang product here mwa
                prodQuant++;
                holder.value.setText(String.valueOf(prodQuant));
                if(prodQuant >= 0) {
                    holder.decrement.setEnabled(true);
                } else {
                    holder.decrement.setEnabled(false);
                }
            }
        });
        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prodQuant--;
                holder.value.setText(String.valueOf(prodQuant));
                if(prodQuant <= 0) {
                    holder.decrement.setEnabled(false);
                } else {
                    holder.decrement.setEnabled(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_id, product_name, product_quantity, product_price;
        Button increment, decrement;
        EditText value;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_id = itemView.findViewById(R.id.prodid);
            product_name = itemView.findViewById(R.id.prodName);
            product_quantity = itemView.findViewById(R.id.prodQuantity);
            product_price = itemView.findViewById(R.id.prodPrice);
            increment = itemView.findViewById(R.id.increment);
            decrement = itemView.findViewById(R.id.decrement);
            value = itemView.findViewById(R.id.value);
        }
    }
}