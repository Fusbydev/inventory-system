    package com.example.practicecrude;

    import androidx.appcompat.app.AppCompatActivity;

    import android.annotation.SuppressLint;
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

    public class inventoryActivity extends AppCompatActivity {
        FloatingActionButton addButton;
        TableLayout tbL;
        DBHelper DBHelper;
        Cursor cursor;
        Button editInventory;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_inventory);
            editInventory = findViewById(R.id.editInventory);

            editInventory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(inventoryActivity.this, editInventory.class));
                }
            });

            addButton = findViewById(R.id.floatingActionButton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(inventoryActivity.this, addProduct.class));
                }
            });

            tbL = findViewById(R.id.dynamicTable);
            DBHelper = new DBHelper(this);
            populateData();
        }
        public void populateData() {
            cursor = DBHelper.getAllData();
            TableRow headerRow = new TableRow(this);
            headerRow.setBackgroundColor(Color.GRAY); // Header row background color
            for (int i = 0; i < cursor.getColumnCount()-2; i++) {
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
                    for (int i = 0; i < cursor.getColumnCount()-2; i++) {
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