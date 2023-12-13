package com.example.practicecrude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addCreditLoan extends AppCompatActivity {
    Button addButton;
    EditText name, Cloan;
    DBHelper DBHelper;
    inventoryActivity inventoryAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_loan);

        DBHelper = new DBHelper(this);

        addButton = findViewById(R.id.AddButton);
        Cloan = findViewById(R.id.creditloan);
        inventoryAct = new inventoryActivity();

        name = findViewById(R.id.customerName);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String cName = name.getText().toString();
                    int customerLoan = Integer.parseInt(Cloan.getText().toString());
                    addProduct(String.valueOf(cName), customerLoan);
                    name.setText("");
                    Cloan.setText("");
                } catch(Exception e) {
                    Toast.makeText(addCreditLoan.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void addProduct(String name, int loan) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CUSTOMER_NAME, name);
        values.put(DBHelper.CUSTOMER_LOAN, loan);

        long newRowId = db.insert(DBHelper.CREDIT, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Loan added successfully" + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add Loan", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }
}