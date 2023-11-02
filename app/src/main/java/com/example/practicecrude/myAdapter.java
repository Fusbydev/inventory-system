package com.example.practicecrude;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> productID;
    private ArrayList<String> productName;
    private ArrayList<String> productQuantity;
    private ArrayList<String> productPrice;
    DBHelper db;
    BottomSheetDialog dialog;
    int idToEdit; // Add a member variable to store the ID to edit
    EditText pnameEdit, pnameQuantity, pnamePrice; // Declare EditText fields

    public myAdapter(Context context, ArrayList<String> productID, ArrayList<String> productName, ArrayList<String> productQuantity, ArrayList<String> productPrice) {
        this.context = context;
        this.productID = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        db = new DBHelper(context);
        dialog = new BottomSheetDialog(context);
        createDialog(); // Initialize createDialog here without a parameter
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
                int idToDelete = Integer.parseInt(productID.get(holder.getAdapterPosition()));
                db.deleteData(idToDelete);
                productID.remove(holder.getAdapterPosition());
                productName.remove(holder.getAdapterPosition());
                productQuantity.remove(holder.getAdapterPosition());
                productPrice.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                Toast.makeText(context, "Product Successfully Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idToEdit = Integer.parseInt(productID.get(holder.getAdapterPosition()));
                Cursor cursor = db.getDataById(idToEdit);

                if (cursor != null && cursor.moveToFirst()) {
                    String name = cursor.getString(1);
                    int quantity = cursor.getInt(3);
                    float price = cursor.getFloat(2);

                    // Update the EditText fields in the dialog
                    pnameEdit.setText(name);
                    pnameQuantity.setText(String.valueOf(quantity));
                    pnamePrice.setText(String.valueOf(price));
                }
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_id, product_name, product_quantity, product_price;
        Button deleteButton, editButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id = itemView.findViewById(R.id.prodid);
            product_name = itemView.findViewById(R.id.prodName);
            product_quantity = itemView.findViewById(R.id.prodQuantity);
            product_price = itemView.findViewById(R.id.prodPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editBtn1);
        }
    }

    private void createDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.editdialog, null, false);
        Button editData = view.findViewById(R.id.editBtn);
        pnameEdit = view.findViewById(R.id.editName);
        pnameQuantity = view.findViewById(R.id.editQuantity);
        pnamePrice = view.findViewById(R.id.editPrice);

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = String.valueOf(pnameEdit.getText());
                int quan = Integer.parseInt(String.valueOf(pnameQuantity.getText()));
                float price = Float.parseFloat(String.valueOf(pnamePrice.getText()));

                boolean success = db.updateData(idToEdit, name, quan, price);

                if (success) {
                    Toast.makeText(context, "Edited Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Edit Unsuccessful", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss(); // Close the dialog when the edit is done
            }
        });

        dialog.setContentView(view);
    }
}
