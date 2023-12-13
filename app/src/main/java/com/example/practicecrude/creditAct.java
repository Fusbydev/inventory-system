package com.example.practicecrude;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class creditAct extends AppCompatActivity {

    TableLayout tbL;
    DBHelper DBHelper;
    Cursor cursor;
    Button editInventory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        editInventory = findViewById(R.id.editInventory);

        editInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(creditAct.this, editCredit.class));
            }
        });

        tbL = findViewById(R.id.dynamicTable);
        DBHelper = new DBHelper(this);
        populateData();
    }
    public void populateData() {
        cursor = DBHelper.getLoanData();
        TableRow headerRow = new TableRow(this);
        headerRow.setBackgroundColor(Color.GRAY); // Header row background color
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            TextView headerCell = new TextView(this);
            headerCell.setText(cursor.getColumnName(i));
            headerCell.setTextSize(8);
            headerCell.setPadding(0, 5, 0, 5);
            headerCell.setGravity(Gravity.CENTER);
            headerCell.setTextColor(Color.WHITE); // Header text color
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(45, 20, 45, 20); // Left and right margins
            headerCell.setLayoutParams(layoutParams);
            headerRow.addView(headerCell);
        }
        tbL.addView(headerRow);

        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                TextView cell = new TextView(this);
                cell.setText(cursor.getString(i));
                cell.setPadding(5, 5, 5, 5);
                cell.setGravity(Gravity.CENTER);
                cell.setTextColor(Color.BLACK);
                cell.setAllCaps(true);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(45, 20, 45, 20); // Left and right margins
                cell.setLayoutParams(layoutParams);
                row.addView(cell);
            }
            tbL.addView(row);
        }

        tbL.setGravity(Gravity.CENTER);

        cursor.close();
        DBHelper.close();
    }
}