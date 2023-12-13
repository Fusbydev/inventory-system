package com.example.practicecrude;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class transactionAdapter extends RecyclerView.Adapter<transactionAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> productID;
    private ArrayList<String> productName;
    private ArrayList<String> productQuantity;
    private ArrayList<String> productPrice;
    private ArrayList<String> investment;
    DBHelper db;

    public transactionAdapter(Context context, ArrayList<String> productID, ArrayList<String> productName, ArrayList<String> productQuantity, ArrayList<String> productPrice, ArrayList<String> investment) {
        this.context = context;
        this.productID = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.investment = investment;
        db = new DBHelper(context);
    }

    @NonNull
    @Override
    public transactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.transaction_product, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.product_id.setText(productID.get(position));
        holder.product_name.setText(productName.get(position));
        holder.product_quantity.setText(productQuantity.get(position));
        holder.product_price.setText(productPrice.get(position));

        holder.value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newQuan = holder.value.getText().toString();
                    int newquan1 = Integer.valueOf(newQuan);
                    int id = Integer.parseInt(productID.get(holder.getAdapterPosition()));

                    // Update the database with the new quantity value
                    Boolean success = db.updateQuantity(newquan1, id);

                    if (success) {
                        // Insert the transaction into the Transaction table
                        boolean transactionSuccess = db.insertTransaction(id,
                                productName.get(position),
                                Integer.parseInt(productPrice.get(position)),
                                newquan1);

                        if (transactionSuccess) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to insert transaction", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Failed to update quantity", Toast.LENGTH_SHORT).show();
                    }
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
        EditText value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id = itemView.findViewById(R.id.prodid);
            product_name = itemView.findViewById(R.id.prodName);
            product_quantity = itemView.findViewById(R.id.prodQuantity);
            product_price = itemView.findViewById(R.id.prodPrice);
            value = itemView.findViewById(R.id.value);
        }
    }
}
