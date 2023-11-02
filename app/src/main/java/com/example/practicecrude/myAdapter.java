package com.example.practicecrude;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> productID;
    private ArrayList<String> productName;
    private ArrayList<String> productQuantity;
    private ArrayList<String> productPrice;
    DBHelper db;

    public myAdapter(Context context, ArrayList<String> productID, ArrayList<String> productName, ArrayList<String> productQuantity, ArrayList<String> productPrice) {
        this.context = context;
        this.productID = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        db = new DBHelper(context);
    }


    @NonNull
    @Override
    public myAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.productentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter.MyViewHolder holder, final int position) {
        holder.product_id.setText(productID.get(position));
        holder.product_name.setText(productName.get(position));
        holder.product_quantity.setText(productQuantity.get(position));
        holder.product_price.setText(productPrice.get(position));

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the item's ID to delete
                int idToDelete = Integer.parseInt(productID.get(holder.getAdapterPosition()));
                // Call the deleteData method in the DBHelper
                db.deleteData(idToDelete);
                // Remove the item from your ArrayList
                productID.remove(holder.getAdapterPosition());
                productName.remove(holder.getAdapterPosition());
                productQuantity.remove(holder.getAdapterPosition());
                productPrice.remove(holder.getAdapterPosition());
                // Notify the adapter that data has changed
                notifyDataSetChanged();
                Toast.makeText(context, "Product Successfully Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return productID.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_id, product_name, product_quantity, product_price;
        Button deleteButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id = itemView.findViewById(R.id.prodid);
            product_name = itemView.findViewById(R.id.prodName);
            product_quantity = itemView.findViewById(R.id.prodQuantity);
            product_price = itemView.findViewById(R.id.prodPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

}
