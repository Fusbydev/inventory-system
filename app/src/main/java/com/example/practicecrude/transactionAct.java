package com.example.practicecrude;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class transactionAct extends AppCompatActivity {

    RecyclerView RecyclerView;
    ArrayList<String> id, name, quantity, price;
    DBHelper DBh;
    transactionAdapter adapter;
    public Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        DBh = new DBHelper(this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        quantity = new ArrayList<>();
        price = new ArrayList<>();
        RecyclerView = findViewById(R.id.recycleView1);
        adapter = new transactionAdapter(this, id, name, quantity, price);
        RecyclerView.setAdapter(adapter);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newQuan = String.valueOf(adapter.value.getText());
                int newquan1 = Integer.valueOf(newQuan);
                int id1 = adapter.id;

                Boolean success = DBh.updateQuantity(newquan1, id1);
                if (success) {
                    Toast.makeText(transactionAct.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(transactionAct.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        displayData();
    }
    private void displayData() {
        Cursor cursor = DBh.getAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data exists", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                price.add(cursor.getString(2));
                quantity.add(cursor.getString(3));
            }
        }
    }

}