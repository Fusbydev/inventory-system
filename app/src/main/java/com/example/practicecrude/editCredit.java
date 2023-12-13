package com.example.practicecrude;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class editCredit extends AppCompatActivity {

    RecyclerView RecyclerView;
    ArrayList<String> id, name;
    ArrayList<String> credit;
    DBHelper DBh;
    creditAdapter cadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credit);

        DBh = new DBHelper(this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        credit = new ArrayList<>();
        RecyclerView = findViewById(R.id.recycleView);
        cadapter = new creditAdapter(this, id, name, credit);
        RecyclerView.setAdapter(cadapter);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();


    }

    private void displayData() {
        Cursor cursor = DBh.getLoanData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data exists", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                credit.add(cursor.getString(2));
            }
        }
    }
}
