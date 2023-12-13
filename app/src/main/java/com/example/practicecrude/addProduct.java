package com.example.practicecrude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kotlin.collections.IndexedValue;

public class addProduct extends AppCompatActivity {
    Button addButton;
    EditText name, quantity, price, investmentq;
    DBHelper DBHelper;
    inventoryActivity inventoryAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        DBHelper = new DBHelper(this);

        addButton = findViewById(R.id.AddButton);
        name = findViewById(R.id.productName);
        price = findViewById(R.id.Price);
        investmentq = findViewById(R.id.investment);

        inventoryAct = new inventoryActivity();

        quantity = findViewById(R.id.quantity);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String pName = name.getText().toString();
                    int quantityP = Integer.parseInt(quantity.getText().toString());
                    float priceP = Integer.parseInt(price.getText().toString());
                    float investmente = Integer.parseInt(investmentq.getText().toString());
                    addProduct(pName, quantityP, priceP, investmente);
                    name.setText("");
                    price.setText("");
                    quantity.setText("");
                    investmentq.setText("");
                } catch(Exception e) {
                    Toast.makeText(addProduct.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void addProduct(String name, int quantity, float price, float inv) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.PRODUCT_NAME, name);
        values.put(DBHelper.PRODUCT_QUANTITY, quantity);
        values.put(DBHelper.PRODUCT_PRICE, price);
        values.put(DBHelper.PRODUCT_INVESTMENT, inv);
        values.put(DBHelper.INITIAL_QUANTITY, quantity);

        long newRowId = db.insert(DBHelper.PRODUCT_INVENTORY, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }
}