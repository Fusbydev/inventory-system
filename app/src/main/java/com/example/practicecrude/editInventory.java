package com.example.practicecrude;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class editInventory extends AppCompatActivity {

    RecyclerView RecyclerView;
    ArrayList<String> id, name, quantity, price;
    DBHelper DBh;
    myAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);

        DBh = new DBHelper(this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        quantity = new ArrayList<>();
        price = new ArrayList<>();

        RecyclerView = findViewById(R.id.recycleView);
        adapter = new myAdapter(this, id, name, quantity, price);
        RecyclerView.setAdapter(adapter);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
