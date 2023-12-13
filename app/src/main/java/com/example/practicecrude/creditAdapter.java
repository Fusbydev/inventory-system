package com.example.practicecrude;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
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

public class creditAdapter extends RecyclerView.Adapter<creditAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> customerID;
    private ArrayList<String> customerName;
    private ArrayList<String> customerLoan;
    DBHelper db;
    BottomSheetDialog dialog;
    int idToEdit; // Add a member variable to store the ID to edit
    EditText customernamee, customerLoane; // Declare EditText fields

    public creditAdapter(Context context, ArrayList<String> customerID, ArrayList<String> customerName, ArrayList<String> customerLoan) {
        this.context = context;
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerLoan = customerLoan;
        db = new DBHelper(context);
        dialog = new BottomSheetDialog(context);
        createDialog(); // Initialize createDialog here without a parameter
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.creditloanview, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.product_id.setText(customerID.get(position));
        holder.product_name.setText(customerName.get(position));
        holder.product_quantity.setText(customerLoan.get(position));

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idToDelete = Integer.parseInt(customerID.get(holder.getAdapterPosition()));
                deleteDialog(holder, idToDelete);

            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idToEdit = Integer.parseInt(customerID.get(holder.getAdapterPosition()));
                Cursor cursor = db.getCustomer(idToEdit);

                if (cursor != null && cursor.moveToFirst()) {
                    String name = cursor.getString(1);
                    int quantity = cursor.getInt(2);

                    // Update the EditText fields in the dialog
                    customernamee.setText(name);
                    customerLoane.setText(String.valueOf(quantity));
                }
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_id, product_name, product_quantity;
        Button deleteButton, editButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id = itemView.findViewById(R.id.textId);
            product_name = itemView.findViewById(R.id.textName);
            product_quantity = itemView.findViewById(R.id.textcredit);
            editButton = itemView.findViewById(R.id.editFuckingButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    private void createDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.loandialog, null, false);
        Button editData = view.findViewById(R.id.editBtn);
        customernamee = view.findViewById(R.id.ceditname);
        customerLoane = view.findViewById(R.id.ceditloan);

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(customernamee.getText());
                int loan = Integer.parseInt(String.valueOf(customerLoane.getText()));
                Log.d("EditDialog", "editDialogs method called");

                AlertDialog.Builder confirmEdit = new AlertDialog.Builder(context);
                confirmEdit.setTitle("Enter Password to Confirm Edit");
                final EditText userIn = new EditText(context);
                confirmEdit.setView(userIn);

                confirmEdit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pass = String.valueOf(userIn.getText());

                        if (pass.matches("password")) { 
                            boolean success = db.updateCredit(idToEdit, name, loan);

                            if (success) {
                                Toast.makeText(context, "Edited Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Edit Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss(); // Close the dialog when the edit is done
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                confirmEdit.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle negative button click if needed
                    }
                });

                confirmEdit.show();
            }
        });

        dialog.setContentView(view);
    }


    void deleteDialog(final MyViewHolder holder, int idDelete) {
        AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(context);
        deleteBuilder.setTitle("Delete");
        deleteBuilder.setMessage("Do you want to delete this customer?");

        deleteBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
            db.deleteLoan(idDelete);
            customerID.remove(holder.getAdapterPosition());
            customerName.remove(holder.getAdapterPosition());
            customerLoan.remove(holder.getAdapterPosition());
            notifyDataSetChanged();
            Toast.makeText(context, "Customer Successfully Deleted", Toast.LENGTH_SHORT).show();
        });
        deleteBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        deleteBuilder.show();
    }

}
